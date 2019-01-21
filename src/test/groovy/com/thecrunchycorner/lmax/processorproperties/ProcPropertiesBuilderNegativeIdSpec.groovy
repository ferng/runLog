package com.thecrunchycorner.lmax.processorproperties

import com.thecrunchycorner.lmax.buffer.BufferReader
import spock.lang.Specification

import java.util.function.UnaryOperator

class ProcPropertiesBuilderNegativeIdSpec extends Specification {
    def reader = Mock(BufferReader.class)
    def process = Mock(UnaryOperator)

    def test() {
        when:
        def props = new ProcProperties.Builder()
                .setId(-1)
                .setPriority(1)
                .setReader(reader)
                .setInitialHead(12)
                .setProcess(process)
                .build()

        then:
        IllegalArgumentException ex1 = thrown()
        ex1.message == "ID cannot be negative"
    }
}
