package com.thecrunchycorner.lmax.services

import spock.lang.Specification

class SystemPropertiesGetIntSpec extends Specification {

    def 'test'() {
        when:
        SystemProperties.refreshProperties()
        def loadedValue = SystemProperties.getAsInt(key)

        then:
        loadedValue == expectedValue

        where:
        key                              | expectedValue
        "threshold.buffer.minimum.size"  | OptionalInt.of(16)
        "unit.test.value.system.default" | OptionalInt.empty()
        "unit.test.value.undefined"      | OptionalInt.empty()
    }

}
