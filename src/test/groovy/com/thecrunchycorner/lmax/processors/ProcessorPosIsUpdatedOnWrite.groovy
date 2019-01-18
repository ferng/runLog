package com.thecrunchycorner.lmax.processors

import com.thecrunchycorner.lmax.buffer.BufferWriter
import com.thecrunchycorner.lmax.buffer.Message
import com.thecrunchycorner.lmax.buffer.OpStatus
import com.thecrunchycorner.lmax.processorproperties.ProcProperties
import spock.lang.Specification

class ProcessorPosIsUpdatedOnWrite extends Specification {

    def 'test'() {
        given:
        def props = new ArrayList()
        def prop = Mock(ProcProperties.class)
        props.add(prop)
        def writer = Mock(BufferWriter.class)
        def proc = new Processor(prop)
        def msg = new Message(22)

        when:
        prop.getId() >> 10
        prop.getPriority() >> 0
        prop.getPos() >> 0
        writer.write(0, msg)
        prop.getWriter() >> writer
        def outcome = proc.writeMessage(msg)

        then:
        OpStatus.WRITE_SUCCESS == outcome
        1 * prop.movePos()
    }
}
