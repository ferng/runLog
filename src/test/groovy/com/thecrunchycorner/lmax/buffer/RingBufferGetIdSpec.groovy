package com.thecrunchycorner.lmax.buffer

import spock.lang.Specification

class RingBufferGetIdSpec extends Specification {

    def test() {
        given:
        def buffer = new RingBuffer(1, 32)

        when:
        def data = buffer.getId()

        then:
        data == 1


    }
}
