package com.thecrunchycorner.runlog.ringbufferaccess

import com.thecrunchycorner.runlog.ringbufferaccess.enums.ProcessorType

import spock.lang.Specification

class PosControllerIncrPosNullProcExceptionSpec extends Specification {

    def 'test'() {
        given:
        def PosController ctrl = PosControllerFactory.getController()
        ctrl.setPos ProcessorType.BUSINESS_PROCESSOR, 0

        when:
        ctrl.incrPos(null)

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == "Arguments cannot be null"
    }

}
