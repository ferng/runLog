package com.thecrunchycorner.lmax.msgstore

import com.thecrunchycorner.lmax.ringbufferaccess.Message

import spock.lang.Specification

class ConcurrentLinkedQueueStoreAddObjectSpec extends Specification{

    def 'test'() {
        given:
        def random = new Random()
        def store = new LinkedBlockingQueueStore()
        def newValue = random.nextInt()
        def msg = new Message(newValue)

        when:
        def returnVal = store.add(msg)

        then:
        returnVal == true
    }



}
