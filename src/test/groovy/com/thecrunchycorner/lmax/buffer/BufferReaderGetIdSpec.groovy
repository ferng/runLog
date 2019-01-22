package com.thecrunchycorner.lmax.buffer

import spock.lang.Specification

class BufferReaderGetIdSpec extends Specification {

    def test() {
        given:
        def buffer = new RingBuffer(1, 32)
        def reader = new BufferReader(buffer)

        when:
        def data = reader.getBufferId()

        then:
        data == 1


    }
}
