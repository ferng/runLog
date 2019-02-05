package com.thecrunchycorner.lmax.processorproperties

import com.thecrunchycorner.lmax.buffer.BufferWriter
import spock.lang.Specification

import java.util.function.UnaryOperator

class ProcPropertiesBuilderMissingIdSpec extends Specification {
    def writer = Mock(BufferWriter.class)
    def process = Mock(UnaryOperator)

    def test() {
        when:
        def props = new ProcPropertiesBuilder()
                .setStage(1)
                .setWriter(writer)
                .setInitialHead(32)
                .setProcess(process)
                .build()

        then:
        IllegalStateException ex1 = thrown()
        ex1.message == "Missing property: ID"
    }

}
