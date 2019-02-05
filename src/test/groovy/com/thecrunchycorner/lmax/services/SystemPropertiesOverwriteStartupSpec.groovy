package com.thecrunchycorner.lmax.services

import spock.lang.Specification

class SystemPropertiesOverwriteStartupSpec extends Specification {

    def 'setup'() {
        SystemProperties.refreshProperties()
    }

    def 'test'() {
        given:
        SystemProperties.set("threshold.buffer.minimum.size", "27")
        SystemProperties.set("system.name", "57")

        when:
        def loadedValue = SystemProperties.get(key)

        then:
        loadedValue == expectedValue

        where:
        key                              | expectedValue
        "threshold.buffer.minimum.size"  | Optional.of("27")
        "system.name" | Optional.of("57")
    }

}
