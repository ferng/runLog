package com.thecrunchycorner.runlog.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SystemProperties {
    private static Logger logger = LogManager.getLogger(SystemProperties.class);
    private static boolean propertiesLoaded = false;
    private static String propsFile = "runLog.properties";

    private static Properties systemProperties;

    // Not using volatile as once it's up it never changes but can change while app is initializing
    private static ConcurrentHashMap<String, String> propMap = new ConcurrentHashMap<String, String>();

    private SystemProperties() {
    }

    public static String get(String key) {
        getSystemProperties();
        return propMap.get(key);
    }

    private synchronized static void getSystemProperties() {

        if (propertiesLoaded == true) {
            return;
        }

        systemProperties = new Properties();

        logger.info("Loading properties file");
        logger.info("Loading properties file");
        logger.info("Loading properties file");
        logger.info("Loading properties file");
        logger.info("Loading properties file");

        propMap.put("data.store.path", "fernRunLog");

        FileInputStream stream = null;
        try {
            File f = new File(propsFile);
            if (f.exists()) {
                stream = new FileInputStream(propsFile);
                systemProperties.load(stream);
            }

            // Load existing properties and warn about any missing ones
            Set<String> propNames = propMap.keySet();
            String readPropVal = null;
            for (String propName : propNames) {
                readPropVal = systemProperties.getProperty(propName);
                if (readPropVal == null) {
                    logger.warn("Property [" + propName + "] not found in " + propsFile + "file.  Using ["
                            + propMap.get(propName) + "] as a default.");
                } else {
                    propMap.put(propName, readPropVal);
                }
            }
            propertiesLoaded = true;

        } catch (IOException ex) {
            logger.error("Can't load the properties file: " + propsFile);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (Exception ex1) {
                    logger.debug(ex1);
                }
            }
        }
    }

    // Used by clients and code to set/change a property
    public static void setProperty(String key, String value) {
        propMap.put(key, value);
    }

}