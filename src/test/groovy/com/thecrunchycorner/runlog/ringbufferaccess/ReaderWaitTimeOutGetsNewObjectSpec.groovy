package com.thecrunchycorner.lmax.ringbufferaccess

import com.thecrunchycorner.lmax.msgstore.RingBufferStore
import com.thecrunchycorner.lmax.processors.ProcessorWorkflow
import com.thecrunchycorner.lmax.ringbufferaccess.enums.ProcessorId
import com.thecrunchycorner.lmax.ringbufferprocessor.ProcPropertiesBuilder
import com.thecrunchycorner.lmax.services.SystemProperties

import spock.lang.Specification

class ReaderWaitTimeOutGetsNewObjectSpec extends Specification {

    def 'test'() {
        given:
        def bufferSize = Integer.parseInt(SystemProperties.get("threshold.buffer.minimum.size"))
        def buffer = new RingBufferStore(bufferSize)
        def busProcHead = 10
        def inputProcHead = 0

        def ProcessorId trailProc = ProcessorId.IN_BUSINESS_PROCESSOR
        def ProcessorId leadProc = ProcessorWorkflow.getLeadProc(trailProc)

        def PosController proc = PosControllerFactory.getController()
        proc.setPos(leadProc, 0)

        def busProcProps = new ProcPropertiesBuilder()
                .setBuffer(buffer)
                .setProcessor(leadProc)
                .setLeadProc(trailProc)
                .setInitialHead(busProcHead)
                .createProcProperties()

        def writer = new Writer(busProcProps)

        def inputProcProps = new ProcPropertiesBuilder()
                .setBuffer(buffer)
                .setProcessor(trailProc)
                .setLeadProc(leadProc)
                .setInitialHead(inputProcHead)
                .createProcProperties()

        def reader = new Reader(inputProcProps)

        Object testObj1 = new Integer(3)
        Object testObj2 = new Integer(4)

        Thread writerThread = new Thread() {
            public void run() {
                try {
                    Thread.sleep(50)
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
        reader.read(100) == testObj2
    }

}
