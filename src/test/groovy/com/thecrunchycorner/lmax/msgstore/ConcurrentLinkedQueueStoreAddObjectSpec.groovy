package com.thecrunchycorner.lmax.msgstore

import spock.lang.Specification

class ConcurrentLinkedQueueStoreAddObjectSpec extends Specification{

    def 'test'() {
        given:
        def random = new Random()
        def store = new QueueStore()
        def newValue = random.nextInt()
        def msg = new Message(newValue)

        when:
        def returnVal = store.add(msg)

        then:
        returnVal
    }



}
