package com.thecrunchycorner.runlog.services;

import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class SystemPropertiesFactory {
    private SystemPropertiesFactory() {};

    private static class LazyHolder {
        private static final SystemProperties sysProps = new SystemProperties();
    }

    public static SystemProperties loadSystemProperties() {
        return LazyHolder.sysProps;
    }
}
