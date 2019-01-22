package com.thecrunchycorner.lmax.processorproperties

import com.thecrunchycorner.lmax.buffer.BufferReader
import com.thecrunchycorner.lmax.testHelpers.IdGenerator
import spock.lang.Specification

import java.util.function.UnaryOperator

class ProcPropertiesGetIdFromReaderSpec extends Specification {

    def test() {
        given:
        def reader = Mock(BufferReader.class)
        def process = Mock(UnaryOperator)
        def id = IdGenerator.id

        when:
        reader.getBufferId() >> 76
        def props = new ProcProperties.Builder()
                .setId(id)
                .setPriority(1)
                .setReader(reader)
                .setInitialHead(12)
                .setProcess(process)
                .build()

        then:
        76 == props.getBufferId()

    }

}
