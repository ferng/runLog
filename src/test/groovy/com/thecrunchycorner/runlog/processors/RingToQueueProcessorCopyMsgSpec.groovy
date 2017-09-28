package com.thecrunchycorner.lmax.processors

import com.thecrunchycorner.lmax.msgstore.LinkedBlockingQueueStore
import com.thecrunchycorner.lmax.msgstore.RingBufferStore
import com.thecrunchycorner.lmax.ringbufferaccess.Message
import com.thecrunchycorner.lmax.ringbufferaccess.PosControllerFactory
import com.thecrunchycorner.lmax.ringbufferaccess.enums.ProcessorId
import com.thecrunchycorner.lmax.services.SystemProperties

import spock.lang.Specification

class RingToQueueProcessorCopyMsgSpec extends Specification {

    def 'test'() {
        given:
        def random = new Random()
        def newValue = random.nextInt()
        def newMsg = new Message(newValue)

        def leadProcID = ProcessorId.OUT_MARSHALER
        def posCtrlr = PosControllerFactory.getController()
        posCtrlr.setPos(leadProcID, 0);

        def bufferSize = Integer.parseInt(SystemProperties.get("threshold.buffer.minimum.size"))
        def ringStore = new RingBufferStore(bufferSize)
        def qStore = new LinkedBlockingQueueStore<Message>()

        def proc = new RingToQueueProcessor(ringStore, qStore)

        when:
        ringStore.set(0, newMsg)
        posCtrlr.incrPos(leadProcID)

        proc.getAndProcessMsg()

        then:
        qStore.take().getPayload() == newMsg.payload
    }



}