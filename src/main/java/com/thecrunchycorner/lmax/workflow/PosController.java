package com.thecrunchycorner.lmax.workflow;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Readers can only read up to the position in the buffer where a write has placed a message
 * specifically for them. Writers cannot write to positions which have not yet been read from.
 * PosController is shared by all processors to keep track of where they can process up to.
 */
public class PosController {
    private static final Logger LOGGER = LogManager.getLogger(PosController.class);

    private Map<ProcessorId, Integer> posMap = new ConcurrentHashMap<>(ProcessorId.values().length);

    /**
     * Set the next position index in buffer we are working on, this will be the position where the
     * next read or write will take place. Used following bulk update operations.
     *
     * @param pos
     * @throws IllegalArgumentException If any parameter is null
     */
    public final void setPos(ProcessorId procType, Integer pos) {
        if (procType == null || pos == null) {
            LOGGER.error("Arguments cannot be null: proctype[{}] pos[{}]", procType, pos);
            throw new IllegalArgumentException("Arguments cannot be null");
        } else {
            posMap.put(procType, pos);
        }
    }


    /**
     * Moves the buffer position index along one.
     * @throws IllegalArgumentException If any parameter is null
     */
    public final void incrPos(ProcessorId procType) {
        if (procType == null) {
            LOGGER.error("Arguments cannot be null: proctype");
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
            LOGGER.error("Arguments cannot be null: proctype");
            throw new IllegalArgumentException("Argument cannot be null");
        } else {
            return posMap.get(procType);
        }
    }


}
