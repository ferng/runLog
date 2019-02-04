package com.thecrunchycorner.lmax.buffer

import spock.lang.Specification

class OpStatusSpec extends Specification {

    def 'test'() {
        given:
        OpStatus WRITE_SUCCESS = OpStatus.WRITE_SUCCESS
        OpStatus HEADER_REACHED = OpStatus.HEADER_REACHED
        OpStatus ERROR = OpStatus.ERROR
    }

}
