package com.thecrunchycorner.lmax.ringbufferaccess

import com.thecrunchycorner.lmax.msgstore.Message
import spock.lang.Specification

class MessageUUIDSetOnCreateSpec extends Specification {

    def 'test'() {
        given:
        def random = new Random()
        def msg = new Message(random.nextInt())
        def oldId = msg.id

        when:
        msg = new Message(random.nextInt())

        then:
        msg.getId() != oldId
    }


}
