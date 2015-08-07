package com.thecrunchycorner.runlog.ringbufferaccess

import com.thecrunchycorner.runlog.ringbufferaccess.enums.ProcessorID

import spock.lang.Specification

class PosControllerIncrPosNullProcExceptionSpec extends Specification {

    def 'test'() {
        given:
        def PosController ctrl = PosControllerFactory.getController()
        ctrl.setPos ProcessorID.IN_BUSINESS_PROCESSOR, 0

        when:
        ctrl.incrPos(null)

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == "Arguments cannot be null"
    }

}
