package com.thecrunchycorner.lmax.processorproperties

import com.thecrunchycorner.lmax.buffer.BufferReader
import com.thecrunchycorner.lmax.testHelpers.IdGenerator
import spock.lang.Specification

import java.util.function.UnaryOperator

class ProcPropertiesBuilderMissingPrioritySpec extends Specification {
    def reader = Mock(BufferReader.class)
    def process = Mock(UnaryOperator)

    def test() {
        when:
        def props = new ProcProperties.Builder()
                .setId(IdGenerator.id)
                .setReader(reader)
                .setInitialHead(32)
                .setProcess(process)
                .build()

        then:
        IllegalStateException ex1 = thrown()
        ex1.message == "Missing property: priority"
    }

}
