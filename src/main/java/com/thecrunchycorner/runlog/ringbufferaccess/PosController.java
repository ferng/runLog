package com.thecrunchycorner.runlog.ringbufferaccess;

import com.thecrunchycorner.runlog.ringbufferaccess.enums.ProcessorType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;


public class PosController {
    private static Logger logger = LogManager.getLogger(PosController.class);

    private HashMap<ProcessorType, Integer> posMap = new HashMap<ProcessorType, Integer>(ProcessorType.values().length);

    /**
     * @throws IllegalArgumentException - If any parameter is null
     */
    public void setPos(ProcessorType procType, Integer pos) {
        if (procType==null || pos==null) {
            logger.error("Arguments cannot be null: proctype[{}] pos[{}]", procType, pos);
            throw new IllegalArgumentException("Arguments cannot be null");
        }
        posMap.put(procType, pos);
    }

    /**
     * @throws IllegalArgumentException - If any parameter is null
     */
    public Integer getPos(ProcessorType procType) {
        if (procType==null) {
            logger.error("Arguments cannot be null: proctype[{}]", procType);
            throw new IllegalArgumentException("Arguments cannot be null");
        }
        return posMap.get(procType);
    }




}
