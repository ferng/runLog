package com.thecrunchycorner.lmax.buffer

import com.thecrunchycorner.lmax.services.SystemProperties
import spock.lang.Specification

class RingBufferIllegalPutSpec extends Specification {

    def 'test'() {
        given:
        def thresholdSize = SystemProperties.getThresholdBufferSize()
        def buffer = new RingBuffer<>(1, thresholdSize)


        when:
        buffer.set(-1, 1)

        then:
        IllegalArgumentException ex1 = thrown()
        ex1.message == "Position cannot be negative"


        when:
        buffer.set(1, null)

        then:
        NullPointerException ex2 = thrown()
        ex2.message == "Cannot write null to the buffer"
    }

}
