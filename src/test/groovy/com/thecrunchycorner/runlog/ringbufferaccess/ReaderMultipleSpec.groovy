package com.thecrunchycorner.runlog.ringbufferaccess

import com.thecrunchycorner.runlog.msgstore.RingBufferStore
import com.thecrunchycorner.runlog.processors.ProcessorWorkflow
import com.thecrunchycorner.runlog.ringbufferaccess.enums.ProcessorID
import com.thecrunchycorner.runlog.ringbufferprocessor.ProcPropertiesBuilder
import com.thecrunchycorner.runlog.services.SystemProperties

import spock.lang.Specification

class ReaderMultipleSpec extends Specification {

    def 'test'() {
        given:
        def bufferSize = Integer.parseInt(SystemProperties.get("threshold.buffer.minimum.size"))
        def buffer = new RingBufferStore(bufferSize)
        def inputProcHead = bufferSize
        def busProcHead = 10
        def ProcessorID trailProc = ProcessorID.BUSINESS_PROCESSOR
        def ProcessorID leadProc = ProcessorWorkflow.getLeadProc(trailProc)

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


        when:
        writer.write(testObj1)
        writer.write(testObj2)
        reader.read()


        then:
        reader.read() == testObj2
    }

}
