package com.thecrunchycorner.lmax.processors

import com.thecrunchycorner.lmax.buffer.BufferWriter
import com.thecrunchycorner.lmax.buffer.Message
import com.thecrunchycorner.lmax.buffer.OpStatus
import com.thecrunchycorner.lmax.processorproperties.ProcProperties
import spock.lang.Specification

class ProcessorPosIsUpdatedOnWriteSpec extends Specification {

    def 'test'() {
        given:
        def propPrimary = Mock(ProcProperties.class)
        def propSecondary = Mock(ProcProperties.class)
        def writer = Mock(BufferWriter.class)
        def proc = new Processor(propPrimary, propSecondary)
        def msg = new Message(22)

        when:
        propSecondary.getId() >> 10
        propSecondary.getStage() >> 0
        propSecondary.getPos() >> 0
        writer.write(0, msg)
        propSecondary.getWriter() >> writer
        def outcome = proc.writeMessage(msg)

        then:
        OpStatus.WRITE_SUCCESS == outcome
        1 * propSecondary.movePos()
    }
}
