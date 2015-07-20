package com.thecrunchycorner.runlog.msgstore

import com.thecrunchycorner.runlog.ringbufferaccess.Message
import com.thecrunchycorner.runlog.ringbufferaccess.enums.MsgType

import spock.lang.Specification

class ConcurrentLinkedQueueStoreAddNullExceptionSpec extends Specification{

    def 'test'() {
        given:
        def random = new Random()
        def store = new LinkedBlockingQueueStore()
        def newValue = random.nextInt()
        def msg = new Message(MsgType.QUEUE_PAYLOAD, newValue)

        when:
        store.add(null)

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == "Argument cannot be null"
    }



}
