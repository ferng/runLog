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
    private static Properties properties;

    private static String propsFileName = "lmax.properties";

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
     * Convenience method to get the system default buffer size.
     *
     * @return the default buffer's size
     * @throws NumberFormatException the property wasn't a numeric value, the buffer size can
     * only be a number
     * @throws MissingResourceException the code is broken a default should always be available as
     * one is declared in the properties file and one is declared directly in this class
     */
    public static int getThresholdBufferSize() throws NumberFormatException,
            MissingResourceException {
        return getPropertyAsInt("threshold.buffer.minimum.size");
    }


    /**
     * Retrieve and convert to an int the property value using the property identifier given.
     *
     * @param id the name of the property to retrieve
     * @return optional with an integer representation of whatever value is held by the property if
     * present or an empty Optional id it doesn't exist or the value is not an int
     * @throws NumberFormatException the property wasn't a numeric value, the buffer size can
     * only be a number
     * @throws MissingResourceException the code is broken a default should always be available as
     * one is declared in the properties file and one is declared directly in this class
     */
    public static OptionalInt getAsOptInt(final String id) throws NumberFormatException,
            MissingResourceException {
        return OptionalInt.of(getPropertyAsInt(id));
    }


    private static int getPropertyAsInt(final String id) throws NumberFormatException,
            MissingResourceException {
        final String prop = properties.getProperty(id);

        if (prop == null) {
            LOGGER.error("Undefined property: {}", id);
            throw new MissingResourceException("System property missing: " + id,
                    SystemProperties.class.getName(), "");
        }

        int response;
        try {
            response = Integer.parseInt(prop);
        } catch (NumberFormatException ex) {
            LOGGER.error("Property {} is not Integer as expected", id);
            throw new NumberFormatException("System property is not a number: " + id);
        }
        return response;
    }


    /**
     * Set the property identified by the given identifier to a value.
     *
     * @param id the name (or id) of the property
     * @param value the value you want to set the property to
     */
    public static void set(final String id, final String value) {
        properties.setProperty(id, value);
    }


    /**
     * Remove the property identified by the given identifier if present, nothing happens otherwise.
     *
     * @param id the name (or id) of the property
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


    /**
     * Refresh all properties those provided by an alternative file name, any properties added
     * programmatically will be removed.
     *
     * @param newPropsFileName the filename of the file holding the properties to be loaded
     */
    static void refreshProperties(String newPropsFileName) {
        propsFileName = newPropsFileName;
        loadSystemProperties();
    }


    private static void loadSystemProperties() {
        setDefaults();

        final Optional<InputStreamReader> optionalStream = getPropsStream();

        if (optionalStream.isPresent()) {
            LOGGER.info("Loading properties file: {}", propsFileName);
            try (final InputStreamReader stream = optionalStream.get()) {
                properties.load(stream);
            } catch (IOException ex) {
                LOGGER.error("Properties could not be loaded from : {}", propsFileName);
            }
        } else {
            LOGGER.warn("Properties file {} not found, using system defaults: ",
                    propsFileName);
        }
    }


    private static Optional<InputStreamReader> getPropsStream() {
        final URL fileUrl = Thread.currentThread().getContextClassLoader()
                .getResource(propsFileName);

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


    private static void setDefaults() {
        // used for unit testing only
        properties = new Properties();
        properties.setProperty("system.name", "fern's lmax");

        //these are minimum threshold values, by all means go above, but never below
        properties.setProperty("threshold.buffer.minimum.size", "8");
    }

}
