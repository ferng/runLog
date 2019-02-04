package com.thecrunchycorner.lmax.processors

import com.thecrunchycorner.lmax.buffer.BufferReader
import com.thecrunchycorner.lmax.buffer.BufferWriter
import com.thecrunchycorner.lmax.buffer.Message
import com.thecrunchycorner.lmax.buffer.OpStatus
import com.thecrunchycorner.lmax.processorproperties.ProcProperties
import spock.lang.Specification

import java.util.function.UnaryOperator

class ProcessorProcessPendingMessageWithWriteSpec extends Specification {

    def 'test'() {
        given:
        def propPrimary = Mock(ProcProperties.class)
        def propSecondary = Mock(ProcProperties.class)
        def reader = Mock(BufferReader.class)
        def writer = Mock(BufferWriter.class)
        def process = Mock(UnaryOperator.class)
        def proc = new Processor(propPrimary, propSecondary)
        def msg1 = new Message(22)
        def msg2 = new Message(122)

        when:
        propPrimary.getId() >> 10
        propPrimary.getPriority() >> 0
        propPrimary.getPos() >> 0
        reader.read(0) >> msg1
        propPrimary.getReader() >> reader
        propSecondary.getWriter() >> writer
        process.apply(msg1) >> msg2
        propPrimary.getProcess() >> process
        def outcome = proc.processPending()

        then:
        OpStatus.WRITE_SUCCESS == outcome
        1 * propPrimary.movePos()
        1 * writer.write(0, msg2)
        1 * propSecondary.movePos()
    }
}
