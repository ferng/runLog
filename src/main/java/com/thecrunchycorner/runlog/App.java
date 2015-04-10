package com.thecrunchycorner.runlog;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class App {
    private static Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) {

        try {
            //don't forget to inherit(cloneStack()) to children to pass the diagnostic context
            logger.info("this gets called with important info");
            logger.info("something else happened, here's more info");
            logger.info("and finally here, everything is ok, with this info");

            String k = null;
//            k.length();

            logger.info("--COMPLETE");


            logger.info("this gets called too");

            logger.info("and finally");

            System.out.println("Hello World!");
        } catch (Exception e) {
            logger.error(e.toString(), e);
        } finally {
        }


    }
}
