package com.thecrunchycorner.runlog.ringbufferaccess

import com.thecrunchycorner.runlog.msgstore.RingBufferStore
import com.thecrunchycorner.runlog.ringbufferaccess.enums.ProcessorID
import com.thecrunchycorner.runlog.ringbufferprocessor.ProcPropertiesBuilder
import com.thecrunchycorner.runlog.services.SystemProperties

import spock.lang.Specification

class ReaderEmptyBufferSpec extends Specification {

    def 'test'() {
        given:
        def bufferSize = Integer.parseInt(SystemProperties.get("threshold.buffer.minimum.size"))
        def buffer = new RingBufferStore(bufferSize)
        def inputProcHead = 0
        def busProcHead = 10

        def PosController proc = PosControllerFactory.getController()
        proc.setPos(ProcessorID.BUSINESS_PROCESSOR, 0)

        def busProcProps = new ProcPropertiesBuilder()
                .setBuffer(buffer)
                .setProcessor(ProcessorID.BUSINESS_PROCESSOR)
                .setLeadProc(ProcessorID.INPUT_QUEUE_PROCESSOR)
                .setInitialHead(busProcHead)
                .createProcProperties()

        def writer = new Writer(busProcProps)

        def inputProcProps = new ProcPropertiesBuilder()
                .setBuffer(buffer)
                .setProcessor(ProcessorID.INPUT_QUEUE_PROCESSOR)
                .setLeadProc(ProcessorID.BUSINESS_PROCESSOR)
                .setInitialHead(inputProcHead)
                .createProcProperties()

        def reader = new Reader(inputProcProps)

        Object testObj1 = new Integer(3)


        when:
        writer.write(testObj1)
        reader.read()


        then:
        reader.read() == null
    }

}
