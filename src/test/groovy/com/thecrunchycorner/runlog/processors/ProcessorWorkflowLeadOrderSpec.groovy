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
        ProcessorID.IN_Q_RECEIVER | ProcessorID.IN_BUSINESS_PROCESSOR
        ProcessorID.IN_BUSINESS_PROCESSOR | ProcessorID.IN_UNMARSHALER
        ProcessorID.IN_UNMARSHALER | ProcessorID.IN_Q_RECEIVER
        ProcessorID.OUT_BUSINESS_PROCESSOR | ProcessorID.OUT_Q_SENDER
        ProcessorID.OUT_Q_SENDER| ProcessorID.OUT_MARSHALER
        ProcessorID.OUT_MARSHALER | ProcessorID.OUT_BUSINESS_PROCESSOR
    }


}