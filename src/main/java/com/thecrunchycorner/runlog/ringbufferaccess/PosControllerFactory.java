package com.thecrunchycorner.runlog.ringbufferaccess;

import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class PosControllerFactory {
    private PosControllerFactory() {};

    private static class LazyHolder {
        private static final PosController posController = new PosController();
    }

    public static PosController getInstance() {
        return LazyHolder.posController;
    }
}
