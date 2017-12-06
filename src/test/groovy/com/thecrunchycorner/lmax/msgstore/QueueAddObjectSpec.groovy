package com.thecrunchycorner.lmax.msgstore

import spock.lang.Specification

class QueueAddObjectSpec extends Specification{

    def 'test'() {
        given:
        def random = new Random()
        def store = new Queue()
        def newValue = random.nextInt()
        def msg = new Message(newValue)

        when:
        def returnVal = store.add(msg)

        then:
        returnVal
    }

}
