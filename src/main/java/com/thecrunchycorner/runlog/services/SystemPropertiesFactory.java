package com.thecrunchycorner.runlog.services;

import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class SystemPropertiesFactory {
    private static class FactoryHolder {
        public static SystemProperties sysProps = new SystemProperties();
    }

    public static SystemProperties loadSystemProperties() {
        return FactoryHolder.sysProps;
    }
}
