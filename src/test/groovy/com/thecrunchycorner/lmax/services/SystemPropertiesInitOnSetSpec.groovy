package com.thecrunchycorner.lmax.services

import spock.lang.Specification

class SystemPropertiesInitOnSetSpec extends Specification {

    def 'test'() {
        given:
        def key = "threshold.buffer.minimum.size"
        def value = "16"
        def loadedKey = "unit.test.value.systemdefault"
        def loadedValue = "Pre-loaded test data"

        when:
        SystemProperties.set(key, value)

        then:
        SystemProperties.get(loadedKey) == Optional.of(loadedValue)
    }

}
