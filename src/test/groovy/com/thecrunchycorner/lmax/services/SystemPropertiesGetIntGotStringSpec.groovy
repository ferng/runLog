package com.thecrunchycorner.lmax.services

import spock.lang.Specification

class SystemPropertiesGetIntGotStringSpec extends Specification {

    def 'setup'() {
        SystemProperties.refreshProperties()
    }

    def 'test'() {
        when:
        SystemProperties.getAsOptInt(key)

        then:
        expectedException = thrown(expectedException)
        expectedException.message == expectedExceptionText

        where:
        key                              | expectedException        | expectedExceptionText

        "system.name" | NumberFormatException    |"System property is not a " +
                "number: system.name"

        "unit.test.value.undefined"      | MissingResourceException | "System property missing: unit.test.value.undefined"
    }

}
