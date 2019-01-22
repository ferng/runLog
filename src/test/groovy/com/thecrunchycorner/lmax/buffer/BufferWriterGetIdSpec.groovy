package com.thecrunchycorner.lmax.buffer

import spock.lang.Specification

class BufferWriterGetIdSpec extends Specification {

    def test() {
        given:
        def buffer = new RingBuffer(1, 32)
        def writer = new BufferWriter(buffer)

        when:
        def data = writer.getBufferId()

        then:
        data == 1


    }
}
