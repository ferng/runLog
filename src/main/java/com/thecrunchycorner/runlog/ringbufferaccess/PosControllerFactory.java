package com.thecrunchycorner.runlog.ringbufferaccess;

import net.jcip.annotations.ThreadSafe;

/**
 * Used to instantiate PosController. Behaviour is undefined if PosController is instantiated directly.
 */
@ThreadSafe
public final class PosControllerFactory {

    private PosControllerFactory() {
    }

    public static PosController getController() {
        return LazyHolder.posController;
    }

    private static class LazyHolder {
        private static final PosController posController = new PosController();
    }

}
