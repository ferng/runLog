package com.thecrunchycorner.lmax.processorproperties

import com.thecrunchycorner.lmax.testHelpers.IdGenerator
import spock.lang.Specification

import java.util.function.UnaryOperator

class ProcPropertiesBuilderMissingReaderWriterSpec extends Specification {
    def process = Mock(UnaryOperator)
    def id = IdGenerator.id

    def test() {
        when:
        def props = new ProcPropertiesBuilder()
                .setId(id)
                .setProcId(IdGenerator.id)
                .setStage(1)
                .setInitialHead(32)
                .setProcess(process)
                .build()

        then:
        IllegalStateException ex1 = thrown()
        ex1.message == "Invalid configuration: reader or writer must be configured. id: " + id
    }

}
