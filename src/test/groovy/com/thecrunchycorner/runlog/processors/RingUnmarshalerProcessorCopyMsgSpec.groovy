package com.thecrunchycorner.lmax.processors

import com.thecrunchycorner.lmax.msgstore.RingBufferStore
import com.thecrunchycorner.lmax.ringbufferaccess.Message
import com.thecrunchycorner.lmax.ringbufferaccess.PosControllerFactory
import com.thecrunchycorner.lmax.ringbufferaccess.enums.ProcessorID
import com.thecrunchycorner.lmax.services.SystemProperties

import spock.lang.Specification

class RingUnmarshalerProcessorCopyMsgSpec extends Specification {

    def 'test'() {
        given:
        def random = new Random()
        def newValue = random.nextInt()
        def newMsg = new Message(newValue)

        def leadProcID = ProcessorID.IN_Q_RECEIVER
        def posCtrlr = PosControllerFactory.getController()
        posCtrlr.setPos(leadProcID, 0);

        def bufferSize = Integer.parseInt(SystemProperties.get("threshold.buffer.minimum.size"))
        def ringStore = new RingBufferStore(bufferSize)

        def proc = new RingUnmarshalerProcessor(ringStore)

        when:
        ringStore.set(0, newMsg)
        posCtrlr.incrPos(leadProcID)

        proc.getAndProcessMsg()

        then:
        ((Message) ringStore.get(0)).getPayload() == newMsg.getPayload()
    }



}