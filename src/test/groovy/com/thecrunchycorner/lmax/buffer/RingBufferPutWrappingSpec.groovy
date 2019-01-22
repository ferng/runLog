package com.thecrunchycorner.lmax.buffer

import com.thecrunchycorner.lmax.services.SystemProperties
import spock.lang.Specification

class RingBufferPutWrappingSpec extends Specification {

    def 'test'() {
        given:
        def thresholdSize = SystemProperties.getThresholdBufferSize()
        def buffer = new RingBuffer<>(1, thresholdSize)
        def testInt1 = -1


        when:
        for (int i = 0; i < thresholdSize; i++) {
            buffer.set(i, i)
        }
        buffer.set(thresholdSize, testInt1)


        then:
        buffer.get(thresholdSize) == testInt1
        buffer.get(0) == testInt1
    }

}
