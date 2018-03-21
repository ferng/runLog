package com.thecrunchycorner.lmax.services

import spock.lang.Specification

class SystemPropertiesRemoveSpec extends Specification {

    def 'test'() {
        given:
        SystemProperties.refreshProperties()
        SystemProperties.set("finger.size", "78")
        SystemProperties.set("threshold.buffer.minimum.size", "hello there")
        SystemProperties.remove(key)

        when:
        def loadedValue = SystemProperties.get(key)

        then:
        loadedValue == expectedValue

        where:
        key                             | expectedValue
        "finger.size"                   | Optional.empty()
        "threshold.buffer.minimum.size" | Optional.empty()
        "unit.test.value.undefined"     | Optional.empty()
    }

}
