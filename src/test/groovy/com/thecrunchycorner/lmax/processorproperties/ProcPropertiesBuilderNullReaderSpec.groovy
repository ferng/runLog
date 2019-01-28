package com.thecrunchycorner.lmax.processorproperties

import com.thecrunchycorner.lmax.testHelpers.IdGenerator
import spock.lang.Specification

import java.util.function.UnaryOperator

class ProcPropertiesBuilderNullReaderSpec extends Specification {
    def process = Mock(UnaryOperator)
    def id = IdGenerator.id

    def test() {
        when:
        def props = new ProcPropertiesBuilder()
                .setId(id)
                .setPriority(1)
                .setReader(null)
                .setInitialHead(12)
                .setProcess(process)
                .build()

        then:
        NullPointerException ex1 = thrown()
        ex1.message == "Buffer reader cannot be null. id: " + id
    }
}
