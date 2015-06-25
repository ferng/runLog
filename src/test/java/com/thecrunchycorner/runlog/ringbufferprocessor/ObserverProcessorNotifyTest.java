package com.thecrunchycorner.runlog.ringbufferprocessor;
import static org.junit.Assert.assertEquals;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Observable;

public class ObserverProcessorNotifyTest {
    ObserverProcessor processor = new ObserverProcessor();
    Notifier notifier = new Notifier();
    int newValue = 73;

    @Before
    public void setup() {
        notifier.addObserver(processor);
    }

    @After
    public void tearDOWN() {

    }

    @Test
    public void Test() {
        notifier.setValTochange(newValue);
        notifier.notifyObservers();
        assertEquals(processor.getValue(), newValue);
    }


    public class Notifier extends Observable {
        int valTochange = 22;

        void setValTochange(int valTochange) {
            this.valTochange = valTochange;
        }

        @Override
        public void notifyObservers() {
            setChanged();
            notifyObservers(valTochange);
        }
    }

}
