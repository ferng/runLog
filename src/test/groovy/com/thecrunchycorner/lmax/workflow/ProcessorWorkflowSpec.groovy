package com.thecrunchycorner.lmax.workflow

import spock.lang.Specification

class ProcessorWorkflowSpec extends Specification {

    def 'test'() {
        def leadPos

        when:
        leadPos = ProcessorWorkflow.getLeadPos(ProcessorId.IN_UNMARSHALL)

        then:
        leadPos == 5
    }


}