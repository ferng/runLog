package com.thecrunchycorner.lmax.msgstore

import spock.lang.Specification

class QueueTakeNewValueSpec extends Specification{

    def 'test'() {
        given:
        def random = new Random()
        def store = new Queue()
        def oldValue = random.nextInt()
        def oldMsg = new Message(oldValue)
        def newValue = random.nextInt()
        def newMsg = new Message(newValue)

        when:
        store.add(oldMsg)
        store.add(newMsg)
        store.take()
        def returnVal = store.take()

        then:
        returnVal == newMsg
    }

}
