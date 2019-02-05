package com.thecrunchycorner.lmax.processorproperties

import com.thecrunchycorner.lmax.testHelpers.IdGenerator
import spock.lang.Specification

import java.util.function.UnaryOperator

class ProcPropertiesBuilderNullWriterSpec extends Specification {
    def process = Mock(UnaryOperator)
    def id = IdGenerator.id

    def test() {
        when:
        def props = new ProcPropertiesBuilder()
                .setId(id)
                .setStage(1)
                .setWriter(null)
                .setInitialHead(12)
                .setProcess(process)
                .build()

        then:
        NullPointerException ex1 = thrown()
        ex1.message == "Buffer writer cannot be null. id: " + id
    }

}
