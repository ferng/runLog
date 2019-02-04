package com.thecrunchycorner.lmax.processorproperties

import com.thecrunchycorner.lmax.buffer.BufferWriter
import com.thecrunchycorner.lmax.testHelpers.IdGenerator
import spock.lang.Specification

import java.util.function.UnaryOperator

class ProcPropertiesBuilderSpec extends Specification {

    def test() {
        given:
        def writer = Mock(BufferWriter.class)
        def process = Mock(UnaryOperator)
        def id = IdGenerator.id

        when:
        def props = new ProcPropertiesBuilder()
                .setId(id)
                .setProcId(id)
                .setPriority(1)
                .setExternal(false)
                .setWriter(writer)
                .setInitialHead(12)
                .setProcess(process)
                .build()

        then:
        props.getId() == id
        props.getPriority() == 1
        !props.isExternal()
        props.getWriter() == writer
        props.getHead() == 12
        props.getProcess() == process
        props.getPos() == 0

    }

}
