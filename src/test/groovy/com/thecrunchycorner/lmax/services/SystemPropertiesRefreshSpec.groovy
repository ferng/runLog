package com.thecrunchycorner.lmax.services

import spock.lang.Specification

class SystemPropertiesRefreshSpec extends Specification {

    def 'test'() {
        given:
        SystemProperties.refreshProperties()
        SystemProperties.set("threshold.buffer.minimum.size", "hello there")
        SystemProperties.set("unit.test.value.system.default", "78")
        SystemProperties.refreshProperties()

        when:
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
