package com.thecrunchycorner.runlog.ringbufferprocessor

import com.thecrunchycorner.runlog.msgstore.LinkedBlockingQueueStore
import com.thecrunchycorner.runlog.msgstore.RingBufferStore
import com.thecrunchycorner.runlog.ringbufferaccess.Message
import com.thecrunchycorner.runlog.ringbufferaccess.enums.MsgType
import com.thecrunchycorner.runlog.services.SystemProperties

import spock.lang.Specification

import java.util.concurrent.LinkedBlockingQueue

class ProcessorGetMsgObjectSpec extends Specification{

    def 'test'() {
        given:
        //Mocks are broken for classes (what???) using the real class instead
        def qStore = new LinkedBlockingQueueStore()
        def ringStore = new RingBufferStore(SystemProperties.get("threshold.buffer.minimum.size"))

        



        def random = new Random()
        def processor = new ObserverProcessor()
        def notifier = new Notifier()
        def newValue = random.nextInt()
        def oldValue = random.nextInt()
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
