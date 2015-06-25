package com.thecrunchycorner.runlog.ringbufferaccess

import com.thecrunchycorner.runlog.ringbufferaccess.enums.ProcessorType

import spock.lang.Specification

class PosControllerSetPosNullPosExceptionSpec extends Specification {

    def 'test'() {
        given:
        def PosController proc = PosControllerFactory.getController()

        when:
        proc.setPos ProcessorType.BUSINESS_PROCESSOR, null

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == "Arguments cannot be null"
    }

}
