package com.thecrunchycorner.runlog.ringbufferprocessor;

import java.util.Observable;
import java.util.Observer;

public class ObserverProcessor implements Observer {
    private int value = 20;

    public int getValue() {
        return value;
    }

    public void update(Observable o, Object arg) {
        value = (Integer)arg;
    }

}
