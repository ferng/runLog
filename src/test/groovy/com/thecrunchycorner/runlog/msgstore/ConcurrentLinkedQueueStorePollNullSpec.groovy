package com.thecrunchycorner.runlog.msgstore

import com.thecrunchycorner.runlog.ringbufferaccess.Message
import com.thecrunchycorner.runlog.ringbufferaccess.enums.MsgType

import spock.lang.Specification

class ConcurrentLinkedQueueStorePollNullSpec extends Specification{

    def 'test'() {
        given:
        def random = new Random()
        def store = new LinkedBlockingQueueStore()
        def oldValue = random.nextInt()
        def newValue = random.nextInt()
        def oldMsg = new Message(MsgType.QUEUE_PAYLOAD, oldValue)
        def newMsg = new Message(MsgType.QUEUE_PAYLOAD, newValue)

        when:
        store.add(oldMsg)
        store.add(newMsg)
        def returnVal = store.take()
        returnVal = store.take()
        returnVal = store.take()

        then:
        returnVal == null
    }



}
