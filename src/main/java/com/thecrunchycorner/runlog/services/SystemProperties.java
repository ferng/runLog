package com.thecrunchycorner.runlog.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/** SystemProperties should not be instantiated directly, this should be done through SystemPropertiesFactory instead **/
public class SystemProperties {
    private static Logger logger = LogManager.getLogger(SystemProperties.class);

    private static ConcurrentHashMap<String, String> propMap = new ConcurrentHashMap<String, String>();
    private static boolean propertiesLoaded = false;
    private static String propsFileName = "runLog.properties";
    private static Properties systemProperties;


    public SystemProperties() {
        loadSystemProperties();
    }


    public static String get(String key) {
        if (propMap.get(key) == null) {
            logger.error("Undefined property: {}", key);
            return "Undefined property";
        } else {
            return propMap.get(key);
        }
    }


    public static void setProperty(String key, String value) {
        propMap.put(key, value);
    }


    public static void remove(String key) {
        propMap.remove(key);
    }


    private synchronized static void loadSystemProperties() {
        if (propertiesLoaded == true) {
            return;
        }

        systemProperties = new Properties();
        prepPop();

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

            Set<String> propNames = new HashSet<String>() ;//= propMap.keySet();

            for (Object sysPropName : systemProperties.keySet()) {
                propNames.add((String) sysPropName);
            }

            for (Object propName : propMap.keySet()) {
                propNames.add((String) propName);
            }

            String readPropVal = null;
            for (String propName : propNames) {
                readPropVal = systemProperties.getProperty(propName);
                if (readPropVal == null) {
                    logger.warn("Property [{}] not found in {} file.  Using [{}] as a default.",  propName, propsFileName, propMap.get(propName)  );
                } else {
                    propMap.put(propName, readPropVal);
                }
            }
            propertiesLoaded = true;
        } catch (IOException ex) {
            logger.error("Can't load the properties file: {}", propsFileName);
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



    //pre-populate any vital data in case we don't find properties files or property
    private static void prepPop() {
        //used for unit tests
        propMap.put("unit.test.value.systemdefault", "Pre-loaded test data");

        //data that can be overwritten by properties file
        propMap.put("data.store.path", "fernRunLog");

        //these are minimum threshold values, by all means go above, but never below
        propMap.put("threshold.buffer.minimum.size", "32");
    }


}