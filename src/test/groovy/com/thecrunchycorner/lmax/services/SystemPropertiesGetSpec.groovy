package com.thecrunchycorner.lmax.services

import spock.lang.Specification

class SystemPropertiesGetSpec extends Specification {

    def 'test'() {
        when:
        SystemProperties.refreshProperties()
        def loadedValue = SystemProperties.get(key)

        then:
        loadedValue == expectedValue

        where:
        key                              | expectedValue
        "threshold.buffer.minimum.size"  | Optional.of("16")
        "unit.test.value.system.default" | Optional.of("Pre-loaded test data")
        "unit.test.value.undefined"      | Optional.empty()
    }

}
