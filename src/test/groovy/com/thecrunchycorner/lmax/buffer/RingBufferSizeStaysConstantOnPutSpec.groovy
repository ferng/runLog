package com.thecrunchycorner.lmax.buffer

import com.thecrunchycorner.lmax.services.SystemProperties
import spock.lang.Specification

class RingBufferSizeStaysConstantOnPutSpec extends Specification {

    def 'test'() {
        given:
        def thresholdSize = SystemProperties.getThresholdBufferSize()
        def buffer = new RingBuffer<>(1, thresholdSize)


        when:
        for (int i = 0; i < thresholdSize; i++) {
            buffer.set(i, i)
        }
        buffer.set(thresholdSize +1 , 1)


        then:
        buffer.size() == thresholdSize
    }

}
