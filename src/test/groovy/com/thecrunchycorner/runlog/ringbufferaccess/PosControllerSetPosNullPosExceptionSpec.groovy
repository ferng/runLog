package com.thecrunchycorner.lmax.ringbufferaccess

import com.thecrunchycorner.lmax.ringbufferaccess.enums.ProcessorId
import spock.lang.Specification

class PosControllerSetPosNullPosExceptionSpec extends Specification {

    def 'test'() {
        given:
        def PosController proc = PosControllerFactory.getController()

        when:
        proc.setPos ProcessorId.IN_BUSINESS_PROCESSOR, null

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == "Arguments cannot be null"
    }

}
