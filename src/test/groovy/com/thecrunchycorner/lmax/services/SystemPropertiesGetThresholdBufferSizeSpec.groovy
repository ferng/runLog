package com.thecrunchycorner.lmax.services

import spock.lang.Specification

class SystemPropertiesGetThresholdBufferSizeSpec extends Specification {

    def 'test'() {
        when:
        SystemProperties.refreshProperties()
        def size = SystemProperties.getThresholdBufferSize()

        then:
        size == 16
    }

}
