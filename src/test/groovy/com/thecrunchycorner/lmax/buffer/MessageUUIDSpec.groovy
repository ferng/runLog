package com.thecrunchycorner.lmax.buffer

import spock.lang.Specification

import java.util.regex.Pattern

class MessageUUIDSpec extends Specification {

    def 'test'() {
        given:
        def message1 = new Message("onions")
        def message2 = new Message("cheese")
        def pattern = Pattern.compile(/^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$/)

        when:
        def payload1 = message1.payload
        def payload2 = message2.payload
        def matcher = pattern.matcher(message1.id.toString())
        def uidOk = matcher.matches()

        then:
        payload1 == "onions"
        payload1 != payload2
        uidOk
    }
}
