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
            buffer.set(i, new Message(i))
        }
        buffer.set(thresholdSize + 1, new Message(1))


        then:
        buffer.size() == thresholdSize
    }

}
