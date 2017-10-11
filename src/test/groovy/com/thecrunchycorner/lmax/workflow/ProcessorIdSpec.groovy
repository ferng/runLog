package com.thecrunchycorner.lmax.workflow

import spock.lang.Specification

class ProcessorIdSpec extends Specification {

    def 'test'() {
        ProcessorId procId = ProcessorId.BUSINESS_PROCESSOR

        when:
        def priority = ProcessorId.getPriority(procId);
        def proc = ProcessorId.values()[2]

        then:
        priority == 3

    }


}