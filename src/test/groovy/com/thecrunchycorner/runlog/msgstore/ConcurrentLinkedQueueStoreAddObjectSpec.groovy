package com.thecrunchycorner.runlog.msgstore

import com.thecrunchycorner.runlog.ringbufferaccess.Message
import com.thecrunchycorner.runlog.ringbufferaccess.enums.MsgType

import spock.lang.Specification

class ConcurrentLinkedQueueStoreAddObjectSpec extends Specification{

    def 'test'() {
        given:
        def random = new Random()
        def store = new LinkedBlockingQueueStore()
        def newValue = random.nextInt()
        def msg = new Message(MsgType.QUEUE_PAYLOAD, newValue)

        when:
        def returnVal = store.add(msg)

        then:
        returnVal == true
    }



}
