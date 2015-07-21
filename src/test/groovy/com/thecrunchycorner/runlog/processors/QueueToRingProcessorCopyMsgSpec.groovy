package com.thecrunchycorner.runlog.processors
import com.thecrunchycorner.runlog.msgstore.LinkedBlockingQueueStore
import com.thecrunchycorner.runlog.msgstore.RingBufferStore
import com.thecrunchycorner.runlog.ringbufferaccess.Message
import com.thecrunchycorner.runlog.ringbufferaccess.PosControllerFactory
import com.thecrunchycorner.runlog.ringbufferaccess.enums.MsgType
import com.thecrunchycorner.runlog.ringbufferaccess.enums.ProcessorType
import com.thecrunchycorner.runlog.services.SystemProperties

import spock.lang.Specification

class QueueToRingProcessorCopyMsgSpec extends Specification {

    def 'test'() {
        given:
        def random = new Random()
        def newValue = random.nextInt()
        def newMsg = new Message(MsgType.QUEUE_PAYLOAD, newValue)

        def posCtrlr = PosControllerFactory.getController()
        posCtrlr.setPos(ProcessorType.BUSINESS_PROCESSOR, 10);

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