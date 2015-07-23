package com.thecrunchycorner.runlog.processors

import com.thecrunchycorner.runlog.ringbufferaccess.enums.ProcessorID

import spock.lang.Specification

class ProcessorWorkflowLeadOrderSpec extends Specification {

    def 'test'() {
        ProcessorID qryTrailProc
        ProcessorID returnedLeadProc

        given:
        qryTrailProc = trailProc

        when:
        returnedLeadProc = ProcessorWorkflow.getLeadProc(qryTrailProc)

        then:
        returnedLeadProc == leadProc

        where:
        trailProc                         | leadProc
        ProcessorID.INPUT_QUEUE_PROCESSOR | ProcessorID.BUSINESS_PROCESSOR
    }


}