package com.thecrunchycorner.lmax.processors

import com.thecrunchycorner.lmax.buffer.BufferReader
import com.thecrunchycorner.lmax.buffer.Message
import com.thecrunchycorner.lmax.buffer.OpStatus
import com.thecrunchycorner.lmax.processorproperties.ProcProperties
import spock.lang.Specification

import java.util.function.UnaryOperator

class ProcessorProcessPendingMessageNoWriteSpec extends Specification {

    def 'test'() {
        given:
        def props = new ArrayList()
        def prop = Mock(ProcProperties.class)
        props.add(prop)
        def reader = Mock(BufferReader.class)
        def process = Mock(UnaryOperator.class)
        def proc = new Processor(prop)
        def msg1 = new Message(22)
        def msg2 = new Message(122)

        when:
        prop.getId() >> 10
        prop.getPriority() >> 0
        prop.getPos() >> 0
        reader.read(0) >> msg1
        prop.getReader() >> reader
        process.apply(msg1) >> msg2
        prop.getProcess() >> process
        def outcome = proc.processPending()

        then:
        OpStatus.NO_WRITE_OP == outcome
        1 * prop.movePos()
    }
}
