package com.thecrunchycorner.runlog.ringbufferaccess;

import com.thecrunchycorner.runlog.ringbufferaccess.types.ProcessorType;

import java.util.HashMap;


public class BufferPosController {
    private HashMap<ProcessorType, Integer> posMap = new HashMap<ProcessorType, Integer>(ProcessorType.values().length);

    public void updatePos(ProcessorType procType, Integer pos) {
        posMap.put(procType, pos);
    }

    public Integer getPos(ProcessorType procTYpe) {
        return posMap.get(procTYpe);
    }




}
