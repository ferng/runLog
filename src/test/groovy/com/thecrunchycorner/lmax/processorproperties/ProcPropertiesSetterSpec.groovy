package com.thecrunchycorner.lmax.processorproperties

import com.thecrunchycorner.lmax.buffer.BufferWriter
import com.thecrunchycorner.lmax.testHelpers.IdGenerator
import spock.lang.Specification

import java.util.function.UnaryOperator

class ProcPropertiesSetterSpec extends Specification {

    def test() {
        given:
        def writer = Mock(BufferWriter.class)
        def process = Mock(UnaryOperator)
        def id = IdGenerator.id

        when:
        def props = new ProcPropertiesBuilder()
                .setId(id)
                .setProcId(22)
                .setStage(1)
                .setExternal(true)
                .setWriter(writer)
                .setInitialHead(12)
                .setProcess(process)
                .build()

        props.setHead(15)
        props.setPos(32)


        then:
        props.getId() == id
        props.getProcId() == 22
        props.getStage() == 1
        props.isExternal()
        props.getReader() == null
        props.getWriter() == writer
        props.getHead() == 15
        props.getProcess() == process
        props.getPos() == 32

    }

}
