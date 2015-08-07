package com.thecrunchycorner.runlog.processors

import com.thecrunchycorner.runlog.msgstore.RingBufferStore
import com.thecrunchycorner.runlog.ringbufferaccess.Message
import com.thecrunchycorner.runlog.ringbufferaccess.PosControllerFactory
import com.thecrunchycorner.runlog.ringbufferaccess.enums.MsgType
import com.thecrunchycorner.runlog.ringbufferaccess.enums.ProcessorID
import com.thecrunchycorner.runlog.services.SystemProperties

import spock.lang.Specification

class RingMarshalerProcessorCopyMsgSpec extends Specification {

    def 'test'() {
        given:
        def random = new Random()
        def newValue = random.nextInt()
        def newMsg = new Message(MsgType.PAYLOAD, newValue)

        def leadProcID = ProcessorID.OUT_BUSINESS_PROCESSOR
        def posCtrlr = PosControllerFactory.getController()
        posCtrlr.setPos(leadProcID, 0);

        def bufferSize = Integer.parseInt(SystemProperties.get("threshold.buffer.minimum.size"))
        def ringStore = new RingBufferStore(bufferSize)

        def proc = new RingMarshalerProcessor(ringStore)

        when:
        ringStore.set(0, newMsg)
        posCtrlr.incrPos(leadProcID)

        proc.getAndProcessMsg()

        then:
        ((Message) ringStore.get(0)).getPayload() == newMsg.getPayload()
    }



}