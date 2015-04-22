package com.thecrunchycorner.runlog.ringbuffer;

import java.util.HashMap;



public class BufferPointers {
    public enum ProcessorType {INPUT_PROCESSOR, UNMARSHALLER, BUSINESS_PROCESSOR}

    private HashMap<Integer, Integer> posMap = new HashMap<Integer, Integer>(6);

    public void put(Integer key, Integer value) {
        posMap.put(key, value);
    }

    public Integer get(Integer key) {
        return posMap.get(key);
    }

}
