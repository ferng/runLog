package com.thecrunchycorner.lmax.ringbufferaccess

import com.thecrunchycorner.lmax.ringbufferaccess.enums.ProcessorId

import spock.lang.Specification

class PosControllerIncrPosNullProcExceptionSpec extends Specification {

    def 'test'() {
        given:
        def PosController ctrl = PosControllerFactory.getController()
        ctrl.setPos ProcessorId.IN_BUSINESS_PROCESSOR, 0

        when:
        ctrl.incrPos(null)

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == "Arguments cannot be null"
    }

}
