package com.thecrunchycorner.lmax.processors


import com.thecrunchycorner.lmax.processorproperties.ProcProperties
import com.thecrunchycorner.lmax.workflow.ProcessorWorkflow
import spock.lang.Specification

class ProcessorProcessLoopInterruptedBeforeProcessing extends Specification {

    def 'test'() {
        given:
        def props = new ArrayList()
        def prop0 = Mock(ProcProperties.class)
        def prop1 = Mock(ProcProperties.class)
        props.add(prop0)
        props.add(prop1)
        def proc = new Processor(prop0)

        when:
        prop0.getId() >> 10
        prop0.getPriority() >> 0
        prop0.getPos() >> 100
        prop0.getHead() >> 100
        prop1.getId() >> 11
        prop1.getPriority() >> 1
        prop1.getPos() >> {100; proc.shutdown(); 100}
        prop1.getHead() >> 201
        ProcessorWorkflow.init(props)
        def processor = proc.processLoop
        def outcome = processor.get()


        then:
        ProcessorStatus.SHUTDOWN == outcome
    }
}
