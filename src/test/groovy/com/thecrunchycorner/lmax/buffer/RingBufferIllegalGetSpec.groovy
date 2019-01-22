package com.thecrunchycorner.lmax.buffer

import com.thecrunchycorner.lmax.services.SystemProperties
import spock.lang.Specification

class RingBufferIllegalGetSpec extends Specification {

    def 'test'() {
        given:
        def thresholdSize = SystemProperties.getThresholdBufferSize()
        def buffer = new RingBuffer<>(1, thresholdSize)


        when:
        buffer.get(-1)

        then:
        IllegalArgumentException ex1 = thrown()
        ex1.message == "Position cannot be negative"
    }

}
