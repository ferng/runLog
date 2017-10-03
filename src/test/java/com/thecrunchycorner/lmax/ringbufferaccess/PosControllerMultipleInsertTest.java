package com.thecrunchycorner.lmax.ringbufferaccess;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.thecrunchycorner.lmax.ringbufferaccess.enums.ProcessorId;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PosControllerMultipleInsertTest {
    private PosController posController;


    @Before
    public void setup() {
        posController = PosControllerFactory.getController();
    }


    @After
    public void tearDOWN() {

    }


    @Test
    public void Test() {
        int inputIndex = 146;
        int unMarshallerIndex = 84;
        int businessProcIndex = 72;

        posController.setPos(ProcessorId.IN_Q_RECEIVE, inputIndex);
        posController.setPos(ProcessorId.IN_UNMARSHALL, unMarshallerIndex);
//        posController.setPos(ProcessorId.IN_BUSINESS_PROCESSOR, businessProcIndex);
        assertThat(posController.getPos(ProcessorId.IN_Q_RECEIVE), is(inputIndex));


    }

}