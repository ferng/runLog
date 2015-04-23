package com.thecrunchycorner.runlog.ringbufferaccess;


import static org.hamcrest.core.Is.isA;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PosControllerFactoryTest {
    private PosController posController;


    @Before
    public void setup() {
        posController = PosControllerFactory.getInstance();
    }


    @After
    public void tearDOWN() {

    }


    @Test
    public void Test() {
        assertThat(posController, isA(PosController.class));
    }

}