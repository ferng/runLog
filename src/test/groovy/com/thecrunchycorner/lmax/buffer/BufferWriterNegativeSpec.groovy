package com.thecrunchycorner.lmax.buffer

import spock.lang.Specification

class BufferWriterNegativeSpec extends Specification {

    def test() {
        given:
        def buffer = new RingBuffer(1, 32)
        def writer = new BufferWriter(buffer)

        when:
        writer.write(-32, "data")

        then:
        IllegalArgumentException ex1 = thrown()
        ex1.message == "Position cannot be negative"

    }
}
