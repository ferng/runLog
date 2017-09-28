package com.thecrunchycorner.lmax.msgstore

import com.thecrunchycorner.lmax.ringbufferaccess.Message

import spock.lang.Specification

class ConcurrentLinkedQueueStoreTakeNullSpec extends Specification{

    def 'test'() {
        given:
        def random = new Random()
        def store = new LinkedBlockingQueueStore()
        def oldValue = random.nextInt()
        def newValue = random.nextInt()
        def oldMsg = new Message(oldValue)
        def newMsg = new Message(newValue)

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
