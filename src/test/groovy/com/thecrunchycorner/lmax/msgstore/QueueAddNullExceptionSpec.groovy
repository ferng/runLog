package com.thecrunchycorner.lmax.msgstore

import spock.lang.Specification

class QueueAddNullExceptionSpec extends Specification{

    def 'test'() {
        given:
        def store = new Queue()

        when:
        store.add(null)

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == "Argument cannot be null"
    }

}
