package com.thecrunchycorner.lmax.processorproperties

import com.thecrunchycorner.lmax.buffer.BufferReader
import com.thecrunchycorner.lmax.testHelpers.IdGenerator
import spock.lang.Specification

import java.util.function.UnaryOperator

class ProcPropertiesBuilderMissingWriterSpec extends Specification {
    def reader = Mock(BufferReader.class)
    def process = Mock(UnaryOperator)
    def id = IdGenerator.id

    def test() {
        when:
        def props = new ProcPropertiesBuilder()
                .setId(id)
                .setProcId(IdGenerator.id)
                .setPriority(1)
                .setReader(reader)
                .setInitialHead(12)
                .setProcess(process)
                .build()

        then:
        props.getId() == id
        props.getPriority() == 1
        props.getReader() == reader
        props.getWriter() == null
        props.getHead() == 12
        props.getProcess() == process
        props.getPos() == 0

    }
}
