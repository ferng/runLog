package com.thecrunchycorner.lmax.processors
import com.thecrunchycorner.lmax.msgstore.LinkedBlockingQueueStore
import com.thecrunchycorner.lmax.msgstore.RingBufferStore
import com.thecrunchycorner.lmax.ringbufferaccess.Message
import com.thecrunchycorner.lmax.ringbufferaccess.PosControllerFactory
import com.thecrunchycorner.lmax.ringbufferaccess.enums.ProcessorID
import com.thecrunchycorner.lmax.services.SystemProperties

import spock.lang.Specification

class QueueToRingProcessorCopyMsgSpec extends Specification {

    def 'test'() {
        given:
        def random = new Random()
        def newValue = random.nextInt()
        def newMsg = new Message(newValue)

        def posCtrlr = PosControllerFactory.getController()
        posCtrlr.setPos(ProcessorID.IN_BUSINESS_PROCESSOR, 10);

        def bufferSize = Integer.parseInt(SystemProperties.get("threshold.buffer.minimum.size"))
        def ringStore = new RingBufferStore(bufferSize)
        def qStore = new LinkedBlockingQueueStore()

        def proc = new QueueToRingProcessor(qStore, ringStore)

        when:
        qStore.add(newMsg)


        proc.getAndProcessMsg()

        then:
        ((Message) ringStore.get(0)).getPayload() == newMsg.getPayload()
    }



}