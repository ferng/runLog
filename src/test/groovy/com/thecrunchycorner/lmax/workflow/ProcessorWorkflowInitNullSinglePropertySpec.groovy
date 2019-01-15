package com.thecrunchycorner.lmax.workflow


import com.thecrunchycorner.lmax.processorproperties.ProcProperties
import spock.lang.Specification

class ProcessorWorkflowInitNullSinglePropertySpec extends Specification {

    def 'test'() {
        given:
        def props = new ArrayList()
        def prop = Mock(ProcProperties.class)
        props.add(prop)
        props.add(null)

        when:
        ProcessorWorkflow.init(props)

        then:
        NullPointerException ex1 = thrown()
        ex1.message == "Processor properties cannot be null"
    }

}
