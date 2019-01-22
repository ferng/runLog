package com.thecrunchycorner.lmax.buffer

import spock.lang.Specification

class BufferWriterReaderSpec extends Specification {

    def test() {
        given:
        def buffer = new RingBuffer(1, 32)
        def reader = new BufferReader(buffer)
        def writer = new BufferWriter(buffer)
        writer.write(1, "hello")

        when:
        def data = reader.read(1)

        then:
        data == "hello"


    }
}
