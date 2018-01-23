package com.thecrunchycorner.lmax.buffer

import spock.lang.Specification

class MessageGettersSpec extends Specification {

    def 'test'() {
        given:
        def message1 = new Message("onions")
        def message2 = new Message("cheese")


        when:
        def payload1 = message1.payload
        def payload2 = message2.payload

        then:
        payload1 != payload2
    }

}
