package com.thecrunchycorner.lmax.services

import spock.lang.Specification

class SystemPropertiesSetSpec extends Specification {

    def 'setup'() {
        SystemProperties.refreshProperties()
    }

    def 'test'() {
        given:
        SystemProperties.set("finger.size", "38")

        when:
        def loadedValue = SystemProperties.get(key)

        then:
        loadedValue == expectedValue

        where:
        key                              | expectedValue
        "finger.size"                    | Optional.of("38")
        "threshold.buffer.minimum.size"  | Optional.of("16")
        "system.name" | Optional.of("fern's lmax")
        "unit.test.value.undefined"      | Optional.empty()
    }

}
