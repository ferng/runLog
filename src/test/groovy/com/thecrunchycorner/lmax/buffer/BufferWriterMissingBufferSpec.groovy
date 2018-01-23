package com.thecrunchycorner.lmax.buffer

import spock.lang.Specification

class BufferWriterMissingBufferSpec extends Specification {

    def test() {
        when:
        def writer = new BufferWriter(null)

        then:
        NullPointerException ex1 = thrown()
        ex1.message == "Missing buffer"
    }

}
