package com.thecrunchycorner.lmax.services

import spock.lang.Specification

class SystemPropertiesGetSpec extends Specification {

    def 'setup'() {
        SystemProperties.refreshProperties()
    }

    def 'test'() {
        when:
        def loadedValue = SystemProperties.get(key)

        then:
        loadedValue == expectedValue

        where:
        key                              | expectedValue
        "threshold.buffer.minimum.size"  | Optional.of("16")
        "system.name" | Optional.of("fern's lmax")
        "unit.test.value.undefined"      | Optional.empty()
    }

}
