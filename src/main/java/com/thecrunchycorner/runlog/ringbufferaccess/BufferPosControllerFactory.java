package com.thecrunchycorner.runlog.ringbufferaccess;

public class BufferPosControllerFactory {
    private BufferPosControllerFactory() {};

    private static class LazyHolder {
        private static final BufferPosController posController = new BufferPosController();
    }

    public static BufferPosController getInstance() {
        return LazyHolder.posController;
    }
}
