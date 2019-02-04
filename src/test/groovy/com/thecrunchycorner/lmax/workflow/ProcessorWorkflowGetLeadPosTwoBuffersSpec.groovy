package com.thecrunchycorner.lmax.workflow


import com.thecrunchycorner.lmax.processorproperties.ProcProperties
import spock.lang.Specification

class ProcessorWorkflowGetLeadPosTwoBuffersSpec extends Specification {

    def 'test'() {
        given:
        def props = new ArrayList()
        def prop0 = Mock(ProcProperties.class)
        def prop1 = Mock(ProcProperties.class)
        def prop2 = Mock(ProcProperties.class)
        def prop3 = Mock(ProcProperties.class)
        def prop4 = Mock(ProcProperties.class)
        def prop5 = Mock(ProcProperties.class)
        props.add(prop0)
        props.add(prop1)
        props.add(prop2)
        props.add(prop3)
        props.add(prop4)
        props.add(prop5)

        when:
        prop0.getId() >> 52
        prop0.getPriority() >> 60
        prop0.getPos() >> 100
        prop0.getBufferId() >> 1
        prop1.getId() >> 53
        prop1.getPriority() >> 70
        prop1.getPos() >> 101
        prop1.getBufferId() >> 1

        prop2.getId() >> 201
        prop2.getPriority() >> 80
        prop2.getPos() >> 350
        prop2.getBufferId() >> 2
        prop3.getId() >> 202
        prop3.getPriority() >> 90
        prop3.getPos() >> 351
        prop3.getBufferId() >> 2
        prop4.getId() >> 203
        prop4.getPriority() >> 90
        prop4.getPos() >> 352
        prop4.getBufferId() >> 2
        prop5.getId() >> 204
        prop5.getPriority() >> 100
        prop5.getPos() >> 353
        prop5.getBufferId() >> 2

        ProcessorWorkflow.init(props)

        then:
        101 == ProcessorWorkflow.getLeadPos(1, 60)
        100 == ProcessorWorkflow.getLeadPos(1, 70)

        353 == ProcessorWorkflow.getLeadPos(2, 80)
        350 == ProcessorWorkflow.getLeadPos(2, 90)
        351 == ProcessorWorkflow.getLeadPos(2, 100)
    }
}
