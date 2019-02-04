package com.thecrunchycorner.lmax.services

import spock.lang.Specification

class SystemPropertiesRefreshWithNewFileSpec extends Specification {

    def 'setup'() {
        SystemProperties.refreshProperties()
    }

    def 'test'() {
        given:
        SystemProperties.set("threshold.buffer.minimum.size", "hello there")
        SystemProperties.set("unit.test.value.system.default", "78")
        SystemProperties.refreshProperties("onions")

        when:
        def loadedValue = SystemProperties.get(key)

        then:
        loadedValue == expectedValue

        where:
        key                              | expectedValue
        "threshold.buffer.minimum.size"  | Optional.of("8")
        "unit.test.value.system.default" | Optional.of("Pre-loaded test data")
        "unit.test.value.undefined"      | Optional.empty()
    }

    def 'cleanup'() {
        SystemProperties.refreshProperties("lmax.properties")
    }
}
