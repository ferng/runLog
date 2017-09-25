package com.thecrunchycorner.lmax.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * System-wide properties this is normally something like: database connectivity, file locations/names, threshold
 * values.
 */
public final class SystemProperties {
    private static final Logger logger = LogManager.getLogger(SystemProperties.class);
    private static final String propsFileName = "lmax.properties";
    private static Properties systemProperties;

    private SystemProperties() {
    }

    static {
        loadSystemProperties();
    }


    /**
     * Retrieve property value using the property identifier given.
     */
    public static String get(String id) {
        String prop = systemProperties.getProperty(id);
        if (prop == null) {
            logger.error("Undefined property: {}", id);
            return "Undefined property";
        } else {
            return prop;
        }
    }


    /**
     * Set the property identified by the given identifier to a value.
     */
    public static void set(String id, String value) {
        systemProperties.setProperty(id, value);
    }


    /**
     * Remove the property identified by the given identifier if present, nothing happens otherwise.
     */
    static void remove(String id) {
        systemProperties.remove(id);
    }


    /**
     * Refresh all properties to original values. Properties will be reset to their initial values, any properties
     * added programmatically will be removed.
     */
    static void refreshProperties() {
        loadSystemProperties();
    }


    private static void loadSystemProperties() {
        setDefaults();
        Optional<FileInputStream> stream = getPropsStream();
        try {
            if (stream.isPresent()) {
                logger.info("Loading properties file: {}", propsFileName);
                systemProperties.load(stream.get());
            } else {
                logger.warn("Properties file {} not found, using system defaults: ", propsFileName);
            }
        } catch (IOException ex) {
            logger.error("Properties could not be loaded from : {}", propsFileName);
        } finally {
            if (stream.isPresent()) {
                try {
                    stream.get().close();
                } catch (Exception ex1) {
                    logger.debug("Can't close file {}, could have been closed already or was never opened",
                            propsFileName);
                }
            }
        }
    }


    private static Optional<FileInputStream> getPropsStream() {
        URL fileUrl = SystemProperties.class.getClassLoader().getResource(propsFileName);
        if (fileUrl == null) {
            return Optional.empty();
        }

        FileInputStream stream;
        try {
            stream = new FileInputStream(fileUrl.getFile());
        } catch (FileNotFoundException ex) {
            return Optional.empty();
        }
        return Optional.of(stream);
    }


    //clean up and pre-populate any vital data in case we don't find properties files or property
    private static void setDefaults() {
        Properties defaultProps = new Properties();

        // used for unit testing only
        defaultProps.setProperty("unit.test.value.systemdefault", "Pre-loaded test data");

        //these are minimum threshold values, by all means go above, but never below
        defaultProps.setProperty("threshold.buffer.minimum.size", "32");

        systemProperties = new Properties(defaultProps);
    }

}
