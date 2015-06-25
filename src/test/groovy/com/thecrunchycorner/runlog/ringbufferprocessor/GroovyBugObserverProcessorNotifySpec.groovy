package com.thecrunchycorner.runlog.ringbufferprocessor

import spock.lang.Specification

class GroovyBugObserverProcessorNotifySpec extends Specification{

    def 'test'() {
        given:
        def processor = new ObserverProcessor()
        def notifier = new Notifierd()
        def newValue = 73

        notifier.addObserver(processor)

        when:
        notifier.setValToChange(newValue)
        notifier.notifyObservers()

        then:
        processor.getValue() == newValue
    }



    public class Notifierd extends Observable {
        def valToChange = 22

        void setValToChange(int valToChange) {
            this.valToChange = valToChange
        }

        @Override
        void notifyObservers() {
            setChanged()
            notifyObservers(valToChange)
        }
    }

}
