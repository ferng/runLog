package com.thecrunchycorner.lmax.buffer

import com.thecrunchycorner.lmax.services.SystemProperties
import spock.lang.Specification

class RingBufferMinSizeSpec extends Specification {

    def 'test'() {
        given:
        def thresholdSize = SystemProperties.getThresholdBufferSize()


        when:
        def buffer1 = new RingBuffer<>(4)

        then:
        buffer1.size() == thresholdSize


        when:
        def buffer2 = new RingBuffer<>(8)

        then:
        buffer2.size() == thresholdSize


        when:
        def buffer3 = new RingBuffer<>(99)

        then:
        buffer3.size() == 99
    }

}

