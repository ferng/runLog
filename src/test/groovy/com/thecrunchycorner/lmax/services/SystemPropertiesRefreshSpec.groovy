package com.thecrunchycorner.lmax.services

import spock.lang.Specification

class SystemPropertiesRefreshSpec extends Specification {

    def 'setup'() {
        SystemProperties.refreshProperties()
    }

    def 'test'() {
        given:
        SystemProperties.set("threshold.buffer.minimum.size", "hello there")
        SystemProperties.set("system.name", "78")
        SystemProperties.refreshProperties()

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
