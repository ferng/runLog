package com.thecrunchycorner.lmax.services

import spock.lang.Specification

class SystemPropertiesGetThresholdBufferSizeSpec extends Specification {

    def 'setup'() {
        SystemProperties.refreshProperties()
    }

    def 'test'() {
        when:
        def size = SystemProperties.getThresholdBufferSize()

        then:
        size == 16
    }

}
