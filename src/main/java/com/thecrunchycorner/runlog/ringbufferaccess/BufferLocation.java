package com.thecrunchycorner.runlog.ringbufferaccess;

import com.thecrunchycorner.runlog.ringbufferaccess.types.ProcessorType;

public class BufferLocation {
    private BufferPosController posController =  BufferPosControllerFactory.getInstance();

    private ProcessorType myLeadProc;

    public void setBufferPos(ProcessorType procType, int pos) {
        posController.updatePos(procType, pos);
    }

    public int getNewHead() {
        return posController.getPos(myLeadProc);
    }
    

}
