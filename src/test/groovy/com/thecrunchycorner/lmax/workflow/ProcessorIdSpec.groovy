package com.thecrunchycorner.lmax.workflow

import spock.lang.Specification

class ProcessorIdSpec extends Specification {

    def 'test'() {
        ProcessorId procId = ProcessorId.BUSINESS_PROCESSOR

        when:
        def priority = procId.getPriority()

        then:
        priority == 1

    }


}