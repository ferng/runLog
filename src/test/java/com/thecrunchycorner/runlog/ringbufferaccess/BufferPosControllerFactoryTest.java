package com.thecrunchycorner.runlog.ringbufferaccess;


import static org.hamcrest.core.Is.isA;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BufferPosControllerFactoryTest {
    private BufferPosController posController;


    @Before
    public void setup() {
        posController = BufferPosControllerFactory.getInstance();
    }


    @After
    public void tearDOWN() {

    }


    @Test
    public void Test() {
        assertThat(posController, isA(BufferPosController.class));
    }

}