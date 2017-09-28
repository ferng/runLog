package com.thecrunchycorner.lmax.msgstore

import com.thecrunchycorner.lmax.ringbufferaccess.Message

import spock.lang.Specification

class ConcurrentLinkedQueueStoreAddNullExceptionSpec extends Specification{

    def 'test'() {
        given:
        def random = new Random()
        def store = new LinkedBlockingQueueStore()
        def newValue = random.nextInt()
        def msg = new Message(newValue)

        when:
        store.add(null)

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == "Argument cannot be null"
    }



}
