package com.thecrunchycorner.lmax.processorproperties

import com.thecrunchycorner.lmax.buffer.BufferReader
import com.thecrunchycorner.lmax.testHelpers.IdGenerator
import spock.lang.Specification

import java.util.function.UnaryOperator

class ProcPropertiesBuilderNegativeStageSpec extends Specification {
    def reader = Mock(BufferReader.class)
    def process = Mock(UnaryOperator)
    def id = IdGenerator.id

    def test() {
        when:
        def props = new ProcPropertiesBuilder()
                .setId(id)
                .setStage(-1)
                .setReader(reader)
                .setInitialHead(12)
                .setProcess(process)
                .build()

        then:
        IllegalArgumentException ex1 = thrown()
        ex1.message == "Stage cannot be negative. id: " + id
    }
}
