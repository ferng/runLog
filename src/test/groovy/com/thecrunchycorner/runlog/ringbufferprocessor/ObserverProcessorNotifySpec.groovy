package com.thecrunchycorner.runlog.ringbufferprocessor

import com.thecrunchycorner.runlog.ringbufferaccess.Message
import com.thecrunchycorner.runlog.ringbufferaccess.enums.MsgType

import spock.lang.Specification

class ObserverProcessorNotifySpec extends Specification{

    def 'test'() {
        given:
        def random = new Random()
        def processor = new ObserverProcessor()
        def notifier = new Notifier()
        def oldValue = random.nextInt()
        def newValue = random.nextInt()
        def msg = new Message(MsgType.QUEUE_PAYLOAD, oldValue)

        when:
        notifier.addObserver(processor)
        notifier.setVal(msg, newValue)
        notifier.notifyObservers()

        then:
        processor.getMsg().getPayload() == newValue
    }


    public static class Notifier extends Observable {
        def msg

        void setVal(Message msg, int val) {
            this.msg = msg
            msg.setPayload(val)
        }

        @Override
        void notifyObservers() {
            setChanged()
            notifyObservers(msg)
        }
    }

}
