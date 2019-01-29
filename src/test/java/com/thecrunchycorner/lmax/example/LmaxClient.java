package com.thecrunchycorner.lmax.example;

import com.thecrunchycorner.lmax.workflow.ProcessorWorkflow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LmaxClient {
    private static Logger LOGGER = LogManager.getLogger(LmaxClient.class);

    public static void main(String[] args) {
        LOGGER.info("Configuring processors");
        ClientProcessorConfig.init();
        LOGGER.info("Start processing");
        ProcessorWorkflow.start();
        do {
//            LOGGER.info(ProcessorWorkflow.getProcStatus().values());
        } while (true);
    }
}
