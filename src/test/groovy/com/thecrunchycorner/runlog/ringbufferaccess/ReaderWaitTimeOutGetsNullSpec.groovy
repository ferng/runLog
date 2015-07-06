package com.thecrunchycorner.runlog.ringbufferaccess

import com.thecrunchycorner.runlog.msgstore.RingBufferStore
import com.thecrunchycorner.runlog.ringbufferaccess.enums.ProcessorType
import com.thecrunchycorner.runlog.ringbufferprocessor.ProcPropertiesBuilder
import com.thecrunchycorner.runlog.services.SystemProperties

import spock.lang.Specification

class ReaderWaitTimeOutGetsNullSpec extends Specification {

    def 'test'() {
        given:
        def bufferSize = Integer.parseInt(SystemProperties.get("threshold.buffer.minimum.size"))
        def buffer = new RingBufferStore(bufferSize)
        def busProcHead = 10
        def inputProcHead = 0

        
        def PosController proc = PosControllerFactory.getController()
        proc.setPos(ProcessorType.BUSINESS_PROCESSOR, 0)

        def busProcProps = new ProcPropertiesBuilder()
                .setBuffer(buffer)
                .setProcessor(ProcessorType.BUSINESS_PROCESSOR)
                .setLeadProc(ProcessorType.INPUT_PROCESSOR)
                .setInitialHead(busProcHead)
                .createProcProperties()

        def writer = new Writer(busProcProps)

        def inputProcProps = new ProcPropertiesBuilder()
                .setBuffer(buffer)
                .setProcessor(ProcessorType.INPUT_PROCESSOR)
                .setLeadProc(ProcessorType.BUSINESS_PROCESSOR)
                .setInitialHead(inputProcHead)
                .createProcProperties()

        def reader = new Reader(inputProcProps)

        Object testObj1 = new Integer(3)


        when:
        writer.write(testObj1)
        reader.read()


        then:
        reader.read(100) == null
    }

}
