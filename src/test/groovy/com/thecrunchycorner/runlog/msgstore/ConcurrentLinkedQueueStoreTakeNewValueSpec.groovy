package com.thecrunchycorner.lmax.msgstore

import com.thecrunchycorner.lmax.ringbufferaccess.Message
import com.thecrunchycorner.lmax.ringbufferaccess.enums.MsgType

import spock.lang.Specification

class ConcurrentLinkedQueueStoreTakeNewValueSpec extends Specification{

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

        then:
        returnVal == newMsg
    }



}
