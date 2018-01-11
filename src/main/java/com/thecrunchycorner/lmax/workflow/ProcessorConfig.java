package com.thecrunchycorner.lmax.workflow;

import com.thecrunchycorner.lmax.processorproperties.ProcProperties;
import java.util.List;

public class ProcessorConfig {
    private static List<ProcProperties> properties;

    public static List<ProcProperties> getProperties() {
        return properties;
    }

    public static void setProperties(List<ProcProperties> properties) {
        ProcessorConfig.properties = properties;
    }
}
