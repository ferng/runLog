package com.thecrunchycorner.lmax.buffer

import spock.lang.Specification

class BufferReaderNegativeSpec extends Specification {

    def test() {
        given:
        def buffer = new RingBuffer(1, 32)
        def reader = new BufferReader(buffer)

        when:
        reader.read(-32)

        then:
        IllegalArgumentException ex1 = thrown()
        ex1.message == "Position cannot be negative"

    }
}
