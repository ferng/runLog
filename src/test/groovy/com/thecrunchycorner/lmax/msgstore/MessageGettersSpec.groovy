package com.thecrunchycorner.lmax.msgstore

import spock.lang.Specification

import java.util.regex.Pattern

class MessageGettersSpec extends Specification {

    def 'test'() {
        given:
        def message = new Message("onions")

        when:
        def payload = message.payload
        def pattern = Pattern.compile(/^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$/)
        def matcher = pattern.matcher(message.id.toString())
        def uidOk = matcher.matches()

        then:
        payload == "onions"
        uidOk
    }

}
