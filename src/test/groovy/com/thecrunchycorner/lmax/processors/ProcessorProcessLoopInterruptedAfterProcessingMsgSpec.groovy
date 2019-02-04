package com.thecrunchycorner.lmax.processors

import com.thecrunchycorner.lmax.buffer.Message
import com.thecrunchycorner.lmax.handlers.Reader
import com.thecrunchycorner.lmax.processorproperties.ProcProperties
import com.thecrunchycorner.lmax.workflow.ProcessorWorkflow
import spock.lang.Specification

import java.util.function.UnaryOperator

class ProcessorProcessLoopInterruptedAfterProcessingMsgSpec extends Specification {

    def 'test'() {
        given:
        def props = new ArrayList()
        def prop0 = Mock(ProcProperties.class)
        def prop1 = Mock(ProcProperties.class)
        props.add(prop0)
        props.add(prop1)
        def proc = new Processor(prop0)
        def reader = Mock(Reader.class)
        def readerMsg = Mock(Message.class)
        def writerMsg = Mock(Message.class)
        def process = Mock(UnaryOperator.class)


        when:
        prop0.getId() >> 10
        prop0.getPriority() >> 0
        prop0.getPos() >> 100
        prop0.getHead() >> 100
        reader.read(100) >> readerMsg
        prop0.getReader() >> reader
        writerMsg.getPayload() >> "Hello"
        process.apply(readerMsg) >> writerMsg
        prop0.getProcess() >> process
        prop1.getId() >> 11
        prop1.getPriority() >> 1
        prop1.getPos() >> { 101; proc.shutdown(); 101 }
        prop1.getHead() >> 201
        ProcessorWorkflow.init(props)
        def processor = proc.processLoop
        def outcome = processor.get()


        then:
        ProcessorStatus.SHUTDOWN == outcome
        1 * prop0.getPriority()
        1 * prop0.getHead()
        1 * prop0.setHead(101)
        1 * prop0.getPos()
        1 * prop0.movePos()

    }
}
