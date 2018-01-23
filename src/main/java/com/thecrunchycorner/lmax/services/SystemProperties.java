package com.thecrunchycorner.lmax.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * System-wide properties this is normally something like: database connectivity, file
 * locations/names, threshold values.
 */
public final class SystemProperties {
    private static final Logger LOGGER = LogManager.getLogger(SystemProperties.class);
    private static final String PROPS_FILE_NAME = "lmax.properties";
    private static Properties properties;

    private SystemProperties() {
    }

    static {
        loadSystemProperties();
    }


    /**
     * Retrieve the property value using the property identifier given.
     *
     * @param id the name of the property to retrieve
     * @return optional with a string representation of whatever value is held by the property if
     * present or an empty Optional id it doesn't exist
     */
    public static Optional<String> get(final String id) {
        final String prop = properties.getProperty(id);
        if (prop == null) {
            LOGGER.error("Undefined property: {}", id);
            return Optional.empty();
        } else {
            return Optional.of(prop);
        }
    }


    /**
     * Retrieve and convert to an int the property value using the property identifier given.
     *
     * @param id the name of the property to retrieve
     * @return optional with an integer representation of whatever value is held by the property if
     * present or an empty Optional id it doesn't exist or the value is not an int
     */
    public static OptionalInt getAsInt(final String id) {
        try {
            return OptionalInt.of(getPropertyAsInt(id));
        } catch (NumberFormatException | MissingResourceException ex) {
            return OptionalInt.empty();
        }
    }


    /**
     * Convenience method to get the system default buffer size.
     *
     * @return the default buffersize
     * @throws NumberFormatException the property wasn't a numeric value, the buffer size can
     * only be a number
     * @throws MissingResourceException the code is broken a default should always be available as
     * one is declared in the properties file and one is declared directly in this class
     */
    public static int getThresholdBufferSize() throws NumberFormatException {
        return getPropertyAsInt("threshold.buffer.minimum.size");
    }


    /**
     * Convenience method to retrieve the given property having converted it to an int if possible.
     *
     * @param id what system property do you want
     * @return the value of that property as an int
     * @throws NumberFormatException it wasn't an int
     * @throws MissingResourceException it wasn't there to begin with
     */
    private static int getPropertyAsInt(final String id) throws NumberFormatException {
        final String prop = properties.getProperty(id);

        if (prop == null) {
            LOGGER.error("Undefined property: {}", id);
            throw new MissingResourceException("System property missing: " + id,
                    SystemProperties.class.getName(), "");
        }
        try {
            return Integer.parseInt(prop);
        } catch (NumberFormatException ex) {
            LOGGER.error("Property {} is not Integer as expected", id);
            throw new NumberFormatException("System property is not a number: " + id);
        }
    }


    /**
     * Set the property identified by the given identifier to a value.
     */
    public static void set(final String id, final String value) {
        properties.setProperty(id, value);
    }


    /**
     * Remove the property identified by the given identifier if present, nothing happens otherwise.
     */
    static void remove(final String id) {
        properties.remove(id);
    }


    /**
     * Refresh all properties to original values. Properties will be reset to their initial
     * values, any properties added programmatically will be removed.
     */
    static void refreshProperties() {
        loadSystemProperties();
    }


    private static void loadSystemProperties() {
        setDefaults();

        final Optional<InputStreamReader> optionalStream = getPropsStream();

        if (optionalStream.isPresent()) {
            LOGGER.info("Loading properties file: {}", PROPS_FILE_NAME);
            try (final InputStreamReader stream = optionalStream.get()) {
                properties.load(stream);
            } catch (IOException ex) {
                LOGGER.error("Properties could not be loaded from : {}", PROPS_FILE_NAME);
            }
        } else {
            LOGGER.warn("Properties file {} not found, using system defaults: ",
                    PROPS_FILE_NAME);
        }
    }


    private static Optional<InputStreamReader> getPropsStream() {
        final URL fileUrl = Thread.currentThread().getContextClassLoader()
                .getResource(PROPS_FILE_NAME);

        if (fileUrl == null) {
            return Optional.empty();
        }

        InputStreamReader stream;
        try {
            final FileInputStream file = new FileInputStream(fileUrl.getFile());
            stream = new InputStreamReader(file, StandardCharsets.UTF_8);
            return Optional.of(stream);
        } catch (FileNotFoundException ex) {
            return Optional.empty();
        }
    }


    //clean up and pre-populate any vital data in case we don't find properties files or property.
    //using defaults in property constructor doesn't work so we're setting them here
    private static void setDefaults() {
        // used for unit testing only
        properties = new Properties();
        properties.setProperty("unit.test.value.system.default", "Pre-loaded test data");

        //these are minimum threshold values, by all means go above, but never below
        properties.setProperty("threshold.buffer.minimum.size", "8");
    }

}
