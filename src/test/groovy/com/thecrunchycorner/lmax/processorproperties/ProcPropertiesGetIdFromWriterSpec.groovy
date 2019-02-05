package com.thecrunchycorner.lmax.processorproperties

import com.thecrunchycorner.lmax.buffer.BufferWriter
import com.thecrunchycorner.lmax.testHelpers.IdGenerator
import spock.lang.Specification

import java.util.function.UnaryOperator

class ProcPropertiesGetIdFromWriterSpec extends Specification {

    def test() {
        given:
        def writer = Mock(BufferWriter.class)
        def process = Mock(UnaryOperator)
        def id = IdGenerator.id

        when:
        writer.getBufferId() >> 25
        def props = new ProcPropertiesBuilder()
                .setId(id)
                .setProcId(id)
                .setStage(1)
                .setWriter(writer)
                .setInitialHead(12)
                .setProcess(process)
                .build()

        then:
        25 == props.getBufferId()

    }

}
