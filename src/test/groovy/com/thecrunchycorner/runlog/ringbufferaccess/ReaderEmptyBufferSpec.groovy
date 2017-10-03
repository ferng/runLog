package com.thecrunchycorner.lmax.ringbufferaccess

import com.thecrunchycorner.lmax.msgstore.RingBufferStore
import com.thecrunchycorner.lmax.workflow.ProcessorWorkflow
import com.thecrunchycorner.lmax.ringbufferaccess.enums.ProcessorId

import com.thecrunchycorner.lmax.services.SystemProperties

import spock.lang.Specification

class ReaderEmptyBufferSpec extends Specification {

    def 'test'() {
        given:
        def bufferSize = Integer.parseInt(SystemProperties.get("threshold.buffer.minimum.size"))
        def buffer = new RingBufferStore(bufferSize)
        def inputProcHead = 0
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

        def writer = new Writer(busProcProps)

        def inputProcProps = new ProcPropertiesBuilder()
                .setBuffer(buffer)
                .setProcessor(trailProc)
                .setLeadProc(leadProc)
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
