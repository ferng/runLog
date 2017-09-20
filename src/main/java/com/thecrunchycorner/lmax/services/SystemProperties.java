package com.thecrunchycorner.lmax.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * System-wide properties this is normally something like: database
 * connectivity (if not using an app server), file locations/names, maximum/minimum values.
 */
public final class SystemProperties {
    private static Logger logger = LogManager.getLogger(SystemProperties.class);

    private static ConcurrentHashMap<String, String> propMap = new ConcurrentHashMap<>();
    private static String propsFileName = "runLog.properties";
    private static Properties systemProperties;

    private SystemProperties() {
    }


    /**
     * Retrieve property value using the property identifier given
     */
    public static synchronized String get(String id) {
        if (! propertiesInitialized()) {
            loadSystemProperties();
        }

        if (propMap.get(id) == null) {
            logger.error("Undefined property: {}", id);
            return "Undefined property";
        } else {
            return propMap.get(id);
        }
    }


    /**
     * Set the property identified by the given identifier to a value
     */
    public static void setProperty(String id, String value) {
        if (! propertiesInitialized()) {
            loadSystemProperties();
        }
        propMap.put(id, value);
    }


    /**
     * Remove the property identified by the given identifier
     */
    public static void remove(String id) {
        propMap.remove(id);
    }


    /**
     * Reload all properties from the properties file.
     * <p>
     * Only properties defined in the file wil be reset/refreshed.
     * Any properties set programmatically and *not* specified in the
     * properties file will retain their current values
     */
    public static void refreshProperties() {
        loadSystemProperties();
    }


    private static void loadSystemProperties() {
        if (! propertiesInitialized()) {
            systemProperties = new Properties();
            prepPop();
        }
        attemptLoadPropsFile();

        populateSystemProperties(getStartupPropNames());
    }


    private static void attemptLoadPropsFile() {
        FileInputStream stream = null;
        try {
            File propsFile = new File(SystemProperties.class.getClassLoader().getResource(propsFileName).getFile());
            if (propsFile.exists()) {
                logger.info("Loading properties file");
                stream = new FileInputStream(propsFile);
                systemProperties.load(stream);
            } else {
                logger.warn("Properties file not found: {}", propsFile.getAbsolutePath());
            }
        } catch (IOException ex) {
            logger.error("Can't load the properties file: {}", propsFileName);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (Exception ex1) {
                    logger.debug("Can't close file, could have been closed already or was never opened");
                }
            }
        }
    }


    private static Set<String> getStartupPropNames() {
        Set<String> propNames = new HashSet<>();

        for (Object sysPropName : systemProperties.keySet()) {
            propNames.add((String) sysPropName);
        }

        for (Object propName : propMap.keySet()) {
            propNames.add((String) propName);
        }
        return propNames;
    }


    private static void populateSystemProperties(Set<String> propNames) {
        String readPropVal;
        for (String propName : propNames) {
            readPropVal = systemProperties.getProperty(propName);
            if (readPropVal == null) {
                logger.warn("Property [{}] not found in {} file.  Using [{}] as a default.", propName, propsFileName, propMap.get(propName));
            } else {
                propMap.put(propName, readPropVal);
            }
        }
    }


    private static boolean propertiesInitialized() {
        return propMap.size() != 0;
    }


    //pre-populate any vital data in case we don't find properties files or property
    private static void prepPop() {
        //used for unit tests
        propMap.put("unit.test.value.systemdefault", "Pre-loaded test data");

        //neo4j database location
        propMap.put("data.store.path", "fernRunLog");

        //these are minimum threshold values, by all means go above, but never below
        propMap.put("threshold.buffer.minimum.size", "32");
    }


}