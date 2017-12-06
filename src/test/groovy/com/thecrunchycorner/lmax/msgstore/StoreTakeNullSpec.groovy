package com.thecrunchycorner.lmax.msgstore

import spock.lang.Specification

class StoreTakeNullSpec extends Specification{

    def 'test'() {
        given:
        def random = new Random()
        def store = new Queue()
        def oldValue = random.nextInt()
        def newValue = random.nextInt()
        def oldMsg = new Message(oldValue)
        def newMsg = new Message(newValue)

        when:
        store.add(oldMsg)
        store.add(newMsg)
        store.take()
        store.take()
        def returnVal = store.take()

        then:
        returnVal == null
    }

}
