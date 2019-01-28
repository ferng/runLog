package com.thecrunchycorner.lmax.processorproperties

import com.thecrunchycorner.lmax.buffer.BufferReader
import com.thecrunchycorner.lmax.testHelpers.IdGenerator
import spock.lang.Specification

import java.util.function.UnaryOperator

class ProcPropertiesBuilderMissingProcessOnReaderSpec extends Specification {
    def reader = Mock(BufferReader.class)
    def process = Mock(UnaryOperator)

    def test() {
        when:
        def props = new ProcProperties.Builder()
                .setId(IdGenerator.id)
                .setProcId(IdGenerator.id)
                .setPriority(1)
                .setReader(reader)
                .setInitialHead(32)
                .build()

        then:
        IllegalStateException ex1 = thrown()
        ex1.message == "Missing property in primary (reader): process"
    }

}
