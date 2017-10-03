package com.thecrunchycorner.lmax.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
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
     * Retrieve property value using the property identifier given.
     */
    public static String get(final String id) {
        final String prop = properties.getProperty(id);
        if (prop == null) {
            LOGGER.error("Undefined property: {}", id);
            return "Undefined property";
        } else {
            return prop;
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
            try (final InputStreamReader stream = optionalStream.get()){
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


    //clean up and pre-populate any vital data in case we don't find properties files or property
    private static void setDefaults() {
        final Properties defaultProps = new Properties();

        // used for unit testing only
        defaultProps.setProperty("unit.test.value.systemdefault", "Pre-loaded test data");

        //these are minimum threshold values, by all means go above, but never below
        defaultProps.setProperty("threshold.buffer.minimum.size", "8");

        properties = new Properties(defaultProps);
    }

}
