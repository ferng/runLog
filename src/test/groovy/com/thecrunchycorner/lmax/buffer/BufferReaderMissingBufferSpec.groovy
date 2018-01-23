package com.thecrunchycorner.lmax.buffer

import spock.lang.Specification

class BufferReaderMissingBufferSpec extends Specification {

    def test() {
        when:
        def reader = new BufferReader(null)

        then:
        NullPointerException ex1 = thrown()
        ex1.message == "Missing buffer"
    }

}
