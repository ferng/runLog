package com.thecrunchycorner.lmax.workflow


import com.thecrunchycorner.lmax.processorproperties.ProcProperties
import com.thecrunchycorner.lmax.processors.ProcessorStatus
import spock.lang.Specification

class ProcessorWorkflowStartShutdownSpec extends Specification {

    def 'test'() {
        given:
        def props = new ArrayList()
        def prop0 = Mock(ProcProperties.class)
        def prop1 = Mock(ProcProperties.class)
        def prop2 = Mock(ProcProperties.class)
        def prop3 = Mock(ProcProperties.class)
        def prop4 = Mock(ProcProperties.class)
        props.add(prop0)
        props.add(prop1)
        props.add(prop2)
        props.add(prop3)
        props.add(prop4)

        when:
        prop0.getId() >> 10
        prop0.getPriority() >> 0
        prop0.getPos() >> 100
        prop1.getId() >> 11
        prop1.getPriority() >> 1
        prop1.getPos() >> 101
        prop2.getId() >> 12
        prop2.getPriority() >> 2
        prop2.getPos() >> 102
        prop3.getId() >> 13
        prop3.getPriority() >> 3
        prop3.getPos() >> 103
        prop4.getId() >> 14
        prop4.getPriority() >> 4
        prop4.getPos() >> 104
        ProcessorWorkflow.init(props)
        ProcessorWorkflow.start()
        ProcessorWorkflow.shutdown()

        then:
        def statuses = ProcessorWorkflow.getProcStatus()
        statuses.each{k, v -> ProcessorStatus.SHUTDOWN == v}
        statuses.size() == props.size()
    }
}
