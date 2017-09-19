package com.thecrunchycorner.lmax.ringbufferaccess

import com.thecrunchycorner.lmax.ringbufferaccess.enums.ProcessorID

import spock.lang.Specification

class PosControllerSetPosNullPosExceptionSpec extends Specification {

    def 'test'() {
        given:
        def PosController proc = PosControllerFactory.getController()

        when:
        proc.setPos ProcessorID.IN_BUSINESS_PROCESSOR, null

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == "Arguments cannot be null"
    }

}
