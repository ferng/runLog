package com.thecrunchycorner.lmax.msgstore

import spock.lang.Specification

class QueueStoreSize extends Specification{

    def 'test'() {
        given:
        def store = new QueueStore()
        def msg = "hello"

        when:
        store.add(msg)
        store.add(msg)
        store.add(msg)

        then:
        store.size() == 3
    }

}
