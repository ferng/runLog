package com.thecrunchycorner.runlog.ringbufferaccess

import spock.lang.Specification

class PosControllerSetPosNullProcExceptionSpec extends Specification {

    def 'test'() {
        given:
        def PosController proc = PosControllerFactory.getController()

        when:
        proc.setPos null, 0

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == "Arguments cannot be null"
    }

}