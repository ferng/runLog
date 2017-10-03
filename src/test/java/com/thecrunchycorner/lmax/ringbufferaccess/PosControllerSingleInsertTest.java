package com.thecrunchycorner.lmax.ringbufferaccess;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.thecrunchycorner.lmax.workflow.ProcessorId;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PosControllerSingleInsertTest {
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
        int inputIndex = 45;

        posController.setPos(ProcessorId.IN_Q_RECEIVE, inputIndex);

        assertThat(posController.getPos(ProcessorId.IN_Q_RECEIVE), is(inputIndex));
    }

}