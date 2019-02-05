package com.thecrunchycorner.lmax.processorproperties

import com.thecrunchycorner.lmax.buffer.BufferWriter
import com.thecrunchycorner.lmax.testHelpers.IdGenerator
import spock.lang.Specification

import java.util.function.UnaryOperator

class ProcPropertiesBuilderMissingProcIdSpec extends Specification {
    def writer = Mock(BufferWriter.class)
    def process = Mock(UnaryOperator)
    def id = IdGenerator.id

    def test() {
        when:
        def props = new ProcPropertiesBuilder()
                .setId(id)
                .setStage(1)
                .setWriter(writer)
                .setInitialHead(32)
                .setProcess(process)
                .build()

        then:
        IllegalStateException ex1 = thrown()
        ex1.message == "Missing property: procId. id: " + id
    }

}
