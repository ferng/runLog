package com.thecrunchycorner.runlog.ringbufferaccess;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.thecrunchycorner.runlog.ringbufferaccess.enums.ProcessorType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PosControllerMultipleTest {
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
        int inputIndex = 146;
        int unMarshallerIndex = 84;
        int businessProcIndex = 72;

        posController.setPos(ProcessorType.INPUT_PROCESSOR, inputIndex);
        assertThat(posController.getPos(ProcessorType.INPUT_PROCESSOR), is(inputIndex));

        posController.setPos(ProcessorType.UNMARSHELLER, unMarshallerIndex);
        assertThat(posController.getPos(ProcessorType.UNMARSHELLER), is(unMarshallerIndex));

        posController.setPos(ProcessorType.BUSINESS_PROCESSOR, businessProcIndex);
        assertThat(posController.getPos(ProcessorType.BUSINESS_PROCESSOR), is(businessProcIndex));


    }

}