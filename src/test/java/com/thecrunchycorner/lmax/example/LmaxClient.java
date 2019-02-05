package com.thecrunchycorner.lmax.example;

import com.thecrunchycorner.lmax.workflow.ProcessorWorkflow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Client to configure and start up LMAX, here you would add other stuff like maybe a server to
 * provide a rest endpoint for shutting down, or some other magic. whatever you like.
 */
public class LmaxClient {
    private static Logger LOGGER = LogManager.getLogger(LmaxClient.class);


    /**
     * entry point
     *
     * @param args we don't do anything with this, but you could!!
     */
    public static void main(String[] args) {
        LOGGER.info("Configuring processors");
        ClientProcessorConfig.init();
        LOGGER.info("Start processing");
        ProcessorWorkflow.start();
    }
}
