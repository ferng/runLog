package com.thecrunchycorner.lmax.workflow

import spock.lang.Specification

class ProcessorWorkflowInitNullPropertiesSpec extends Specification {

    def 'test'() {
        when:
        ProcessorWorkflow.init(null)

        then:
        NullPointerException ex1 = thrown()
        ex1.message == "Processor properties cannot be null"
    }

}
