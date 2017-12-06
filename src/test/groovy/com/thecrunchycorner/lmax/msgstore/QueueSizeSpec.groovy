package com.thecrunchycorner.lmax.msgstore

import spock.lang.Specification

class QueueSizeSpec extends Specification{

    def 'test'() {
        given:
        def store = new Queue()
        def msg = "hello"

        when:
        store.add(msg)
        store.add(msg)
        store.add(msg)

        then:
        store.size() == 3
    }

}
