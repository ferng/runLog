package com.thecrunchycorner.lmax.processorproperties

import com.thecrunchycorner.lmax.buffer.BufferWriter
import com.thecrunchycorner.lmax.testHelpers.IdGenerator
import spock.lang.Specification

import java.util.function.UnaryOperator

class ProcPropertiesBuilderMissingProcessOnWriterSpec extends Specification {
    def writer = Mock(BufferWriter.class)
    def process = Mock(UnaryOperator)

    def test() {
        when:
        def props = new ProcPropertiesBuilder()
                .setId(IdGenerator.id)
                .setProcId(IdGenerator.id)
                .setPriority(1)
                .setWriter(writer)
                .setInitialHead(32)
                .build()

        then:
        noExceptionThrown()
    }

}
