package com.thecrunchycorner.lmax.buffer

import spock.lang.Specification

class BufferWriterNullDataSpec extends Specification {

    def test() {
        given:
        def buffer = new RingBuffer(1, 32)
        def writer = new BufferWriter(buffer)

        when:
        writer.write(-32, null)

        then:
        NullPointerException ex1 = thrown()
        ex1.message == "Cannot write null to the buffer"

    }
}
