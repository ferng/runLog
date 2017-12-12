package com.thecrunchycorner.lmax.workflow

import spock.lang.Specification

class ProcessorPrioritiesSpec extends Specification {

    def 'test'() {
        ProcessorPriorities procId = ProcessorPriorities.BUSINESS_PROCESSOR

        when:
        def priority = procId.getPriority()

        then:
        priority == 1

    }


}