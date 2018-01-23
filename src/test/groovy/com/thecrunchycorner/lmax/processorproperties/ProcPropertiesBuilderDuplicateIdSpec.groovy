package com.thecrunchycorner.lmax.processorproperties

import com.thecrunchycorner.lmax.buffer.BufferReader
import com.thecrunchycorner.lmax.buffer.BufferWriter
import com.thecrunchycorner.lmax.testHelpers.IdGenerator
import spock.lang.Specification

import java.util.function.UnaryOperator

class ProcPropertiesBuilderDuplicateIdSpec extends Specification {
    def reader = Mock(BufferReader.class)
    def writer = Mock(BufferWriter.class)
    def process = Mock(UnaryOperator)

    def test() {
        when:
        def id = IdGenerator.id
        def props = new ProcProperties.Builder()
                .setId(id)
                .setPriority(1)
                .setReader(reader)
                .setWriter(writer)
                .setInitialHead(12)
                .setProcess(process)
                .build()

        def props2 = new ProcProperties.Builder()
                .setId(id)
                .setPriority(2)
                .setReader(reader)
                .setWriter(writer)
                .setInitialHead(15)
                .setProcess(process)
                .build()

        then:
        IllegalArgumentException ex1 = thrown()
        ex1.message == "ID already assigned to processor"
    }
}
