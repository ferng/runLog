package com.thecrunchycorner.lmax.workflow

import spock.lang.Specification

class ProcessorWorkflowLeadOrderSpec extends Specification {

    def 'test'() {
        ProcessorPriorities qryTrailProc
        ProcessorPriorities returnedLeadProc

        given:
        qryTrailProc = trailProc

        when:
        returnedLeadProc = ProcessorWorkflow.getLeadProc(qryTrailProc)

        then:
        returnedLeadProc == leadProc

        where:
        trailProc                                  | leadProc
        ProcessorPriorities.IN_Q_RECEIVER          | ProcessorPriorities.IN_BUSINESS_PROCESSOR
        ProcessorPriorities.IN_BUSINESS_PROCESSOR  | ProcessorPriorities.IN_UNMARSHALER
        ProcessorPriorities.IN_UNMARSHALER         | ProcessorPriorities.IN_Q_RECEIVER
        ProcessorPriorities.OUT_BUSINESS_PROCESSOR | ProcessorPriorities.OUT_Q_SENDER
        ProcessorPriorities.OUT_Q_SENDER           | ProcessorPriorities.OUT_MARSHALER
        ProcessorPriorities.OUT_MARSHALER          | ProcessorPriorities.OUT_BUSINESS_PROCESSOR
    }


}