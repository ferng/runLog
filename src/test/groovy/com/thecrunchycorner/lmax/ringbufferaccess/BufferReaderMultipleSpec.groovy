package com.thecrunchycorner.lmax.ringbufferaccess

import com.thecrunchycorner.lmax.msgstore.RingBufferStore
import com.thecrunchycorner.lmax.workflow.ProcessorWorkflow
import com.thecrunchycorner.lmax.workflow.ProcessorId

import com.thecrunchycorner.lmax.services.SystemProperties

import spock.lang.Specification

class BufferReaderMultipleSpec extends Specification {

    def 'test'() {
        given:
        def bufferSize = Integer.parseInt(SystemProperties.get("threshold.buffer.minimum.size"))
        def buffer = new RingBufferStore(bufferSize)
        def inputProcHead = bufferSize
        def busProcHead = 10
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

        def writer = new BufferWriter(busProcProps)

        def inputProcProps = new ProcPropertiesBuilder()
                .setBuffer(buffer)
                .setProcessor(trailProc)
                .setLeadProc(leadProc)
                .setInitialHead(inputProcHead)
                .createProcProperties()

        def reader = new BufferReader(inputProcProps)

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