package com.thecrunchycorner.lmax.processorproperties

import com.thecrunchycorner.lmax.buffer.BufferReader
import com.thecrunchycorner.lmax.buffer.BufferWriter
import com.thecrunchycorner.lmax.testHelpers.IdGenerator
import spock.lang.Specification

import java.util.function.UnaryOperator

class ProcPropertiesBuilderBothReaderWriterSpec extends Specification {
    def reader = Mock(BufferReader.class)
    def writer = Mock(BufferWriter.class)
    def process = Mock(UnaryOperator)
    def id = IdGenerator.id

    def test() {
        when:
        def props = new ProcPropertiesBuilder()
                .setId(id)
                .setProcId(IdGenerator.id)
                .setStage(1)
                .setInitialHead(32)
                .setReader(reader)
                .setWriter(writer)
                .setProcess(process)
                .build()

        then:
        IllegalStateException ex1 = thrown()
        ex1.message == "Invalid configuration: reader or writer must be configured not both. id: " + id
    }

}
