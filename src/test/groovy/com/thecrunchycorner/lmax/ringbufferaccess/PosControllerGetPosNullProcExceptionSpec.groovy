package com.thecrunchycorner.lmax.ringbufferaccess

import com.thecrunchycorner.lmax.workflow.ProcessorId

import spock.lang.Specification

class PosControllerGetPosNullProcExceptionSpec extends Specification {

    def 'test'() {
        given:
        def PosController ctrl = PosControllerFactory.getController()
        ctrl.setPos ProcessorId.IN_BUSINESS_PROCESSOR, 0

        when:
        ctrl.getPos(null)

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == "Arguments cannot be null"
    }

}