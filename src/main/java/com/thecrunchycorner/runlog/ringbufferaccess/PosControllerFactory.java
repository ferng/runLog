package com.thecrunchycorner.runlog.ringbufferaccess;

import net.jcip.annotations.ThreadSafe;

/**
 * Used to instantiate PosController. Behaviour is undefined if PosController is instantiated directly.
 */
@ThreadSafe
public class PosControllerFactory {
    private PosControllerFactory() {};

    private static class LazyHolder {
        private static final PosController posController = new PosController();
    }

    public static PosController getController() {
        return LazyHolder.posController;
    }
}
