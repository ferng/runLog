package com.thecrunchycorner.lmax.ringbufferaccess

import com.thecrunchycorner.lmax.ringbufferaccess.enums.MsgType

import spock.lang.Specification

class MessageUUIDSetOnCreateSpec extends Specification {

    def 'test'() {
        given:
        def random = new Random()
        def msg = new Message(MsgType.QUEUE_PAYLOAD, random.nextInt())
        def oldId = msg.id

        when:
        msg = new Message(MsgType.QUEUE_PAYLOAD, random.nextInt())

        then:
        msg.getId() != oldId
    }


}
