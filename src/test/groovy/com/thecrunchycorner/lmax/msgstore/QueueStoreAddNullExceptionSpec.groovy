package com.thecrunchycorner.lmax.msgstore

import spock.lang.Specification

class QueueStoreAddNullExceptionSpec extends Specification{

    def 'test'() {
        given:
        def store = new QueueStore()

        when:
        store.add(null)

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == "Argument cannot be null"
    }

}
