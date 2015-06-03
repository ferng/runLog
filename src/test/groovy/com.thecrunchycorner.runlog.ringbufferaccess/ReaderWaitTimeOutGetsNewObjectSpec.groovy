package com.thecrunchycorner.runlog.ringbufferaccess

import com.thecrunchycorner.runlog.ringbuffer.RingBuffer
import com.thecrunchycorner.runlog.ringbufferaccess.enums.ProcessorType
import com.thecrunchycorner.runlog.ringbufferprocessor.ProcPropertiesBuilder
import com.thecrunchycorner.runlog.services.SystemProperties

import spock.lang.Specification

class ReaderWaitTimeOutGetsNewObjectSpec extends Specification {

    def 'test'() {
        given:
        def bufferSize = Integer.parseInt(SystemProperties.get("threshold.buffer.minimum.size"))
        def buffer = new RingBuffer(bufferSize)
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
        Object testObj2 = new Integer(4)

        Thread writerThread = new Thread() {
            public void run() {
                try {
                    Thread.sleep(500)
                    writer.write(testObj2)
                } catch (InterruptedException ex) {
                    println(ex)
                }
            }
        }

        when:
        writer.write(testObj1)
        writerThread.start()
        reader.read()


        then:
        reader.read(1000) == testObj2
    }

}
