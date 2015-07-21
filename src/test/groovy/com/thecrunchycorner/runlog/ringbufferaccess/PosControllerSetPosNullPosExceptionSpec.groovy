package com.thecrunchycorner.runlog.ringbufferaccess

import com.thecrunchycorner.runlog.ringbufferaccess.enums.ProcessorID

import spock.lang.Specification

class PosControllerSetPosNullPosExceptionSpec extends Specification {

    def 'test'() {
        given:
        def PosController proc = PosControllerFactory.getController()

        when:
        proc.setPos ProcessorID.BUSINESS_PROCESSOR, null

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == "Arguments cannot be null"
    }

}
