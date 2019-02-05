package com.thecrunchycorner.lmax.processors

import com.thecrunchycorner.lmax.buffer.BufferReader
import com.thecrunchycorner.lmax.buffer.Message
import com.thecrunchycorner.lmax.processorproperties.ProcProperties
import spock.lang.Specification

class ProcessorPosIsUpdatedOnReadSpec extends Specification {

    def 'test'() {
        given:
        def prop = Mock(ProcProperties.class)
        def reader = Mock(BufferReader.class)
        def proc = new Processor(prop)
        def msg = new Message(22)

        when:
        prop.getId() >> 10
        prop.getStage() >> 0
        prop.getPos() >> 0
        reader.read(0) >> msg
        prop.getReader() >> reader
        def outcome = proc.readMessage()

        then:
        msg == outcome
        1 * prop.movePos()
    }
}
