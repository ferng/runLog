package com.thecrunchycorner.lmax.workflow

import spock.lang.Specification

class ProcessorWorkflowLeadOrderSpec extends Specification {

    def 'test'() {
        ProcessorId qryTrailProc
        ProcessorId returnedLeadProc

        given:
        qryTrailProc = trailProc

        when:
        returnedLeadProc = ProcessorWorkflow.getLeadProc(qryTrailProc)

        then:
        returnedLeadProc == leadProc

        where:
        trailProc                          | leadProc
        ProcessorId.IN_Q_RECEIVER          | ProcessorId.IN_BUSINESS_PROCESSOR
        ProcessorId.IN_BUSINESS_PROCESSOR  | ProcessorId.IN_UNMARSHALER
        ProcessorId.IN_UNMARSHALER         | ProcessorId.IN_Q_RECEIVER
        ProcessorId.OUT_BUSINESS_PROCESSOR | ProcessorId.OUT_Q_SENDER
        ProcessorId.OUT_Q_SENDER           | ProcessorId.OUT_MARSHALER
        ProcessorId.OUT_MARSHALER          | ProcessorId.OUT_BUSINESS_PROCESSOR
    }


}