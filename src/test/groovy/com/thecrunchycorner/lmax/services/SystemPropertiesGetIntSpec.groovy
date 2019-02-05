package com.thecrunchycorner.lmax.services

import spock.lang.Specification

class SystemPropertiesGetIntSpec extends Specification {

    def 'setup'() {
        SystemProperties.refreshProperties()
    }

    def 'test'() {
        when:
        def loadedValue = SystemProperties.getAsOptInt(key)

        then:
        loadedValue == expectedValue

        where:
        key                              | expectedValue
        "threshold.buffer.minimum.size"  | OptionalInt.of(16)
    }

}
