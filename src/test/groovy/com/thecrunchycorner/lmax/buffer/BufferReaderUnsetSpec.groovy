package com.thecrunchycorner.lmax.buffer

import spock.lang.Specification

class BufferReaderUnsetSpec extends Specification {

    def test() {
        given:
        def buffer = new RingBuffer(32);
        def reader = new BufferReader(buffer)

        when:
        def data = reader.read(2)

        then:
        data == null


    }
}
