package com.thecrunchycorner.lmax.processorproperties

import com.thecrunchycorner.lmax.buffer.BufferReader
import com.thecrunchycorner.lmax.buffer.BufferWriter
import com.thecrunchycorner.lmax.testHelpers.IdGenerator
import spock.lang.Specification

import java.util.function.UnaryOperator

class ProcPropertiesSetterSpec extends Specification {

    def test() {
        given:
        def reader = Mock(BufferReader.class)
        def writer = Mock(BufferWriter.class)
        def process = Mock(UnaryOperator)
        def id = IdGenerator.id

        when:
        def props = new ProcProperties.Builder()
                .setId(id)
                .setPriority(1)
                .setReader(reader)
                .setWriter(writer)
                .setInitialHead(12)
                .setProcess(process)
                .build()

        props.setHead(15)
        props.setPos(32)


        then:
        props.getId() == id
        props.getPriority() == 1
        props.getReader() == reader
        props.getWriter() == writer
        props.getHead() == 15
        props.getProcess() == process
        props.getPos() == 32

    }

}
