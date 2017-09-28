package com.thecrunchycorner.lmax.ringbufferaccess;

import com.thecrunchycorner.lmax.ringbufferaccess.enums.ProcessorId;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Readers can only read up to the position in the buffer where a write
 * has placed a message specifically for them. Same goes for writers.
 * PosController is shared by all processors to keep track of where they
 * can process up to.
 *
 * Behaviour is undefined if PosController is instantiated directly,
 * instead use PosControllerFactory.getController()
 */
public class PosController {
    private static Logger logger = LogManager.getLogger(PosController.class);

    private Map<ProcessorId, Integer> posMap = new HashMap<>(ProcessorId.values().length);

    /**
     * @throws IllegalArgumentException If any parameter is null
     */
    public final void setPos(ProcessorId procType, Integer pos) {
        if (procType == null || pos == null) {
            logger.error("Arguments cannot be null: proctype[{}] pos[{}]", procType, pos);
            throw new IllegalArgumentException("Arguments cannot be null");
        } else {
            posMap.put(procType, pos);
        }
    }


    /**
     * @throws IllegalArgumentException If any parameter is null
     */
    public final void incrPos(ProcessorId procType) {
        if (procType == null) {
            logger.error("Arguments cannot be null: proctype");
            throw new IllegalArgumentException("Arguments cannot be null");
        } else {
            int pos = posMap.get(procType);
            posMap.put(procType, pos + 1);
        }
    }


    /**
     * @throws IllegalArgumentException If any parameter is null
     */
    public final Integer getPos(ProcessorId procType) {
        if (procType == null) {
            logger.error("Arguments cannot be null: proctype");
            throw new IllegalArgumentException("Arguments cannot be null");
        } else {
            return posMap.get(procType);
        }
    }


}
