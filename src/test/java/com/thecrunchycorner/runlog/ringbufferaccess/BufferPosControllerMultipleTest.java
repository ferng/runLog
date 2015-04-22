package com.thecrunchycorner.runlog.ringbufferaccess;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.thecrunchycorner.runlog.ringbufferaccess.types.ProcessorType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BufferPosControllerMultipleTest {
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
        int inputIndex = 146;
        int unMarshallerIndex = 84;
        int businessProcIndex = 72;

        posController.updatePos(ProcessorType.INPUT_PROCESSOR, inputIndex);
        assertThat(posController.getPos(ProcessorType.INPUT_PROCESSOR), is(inputIndex));

        posController.updatePos(ProcessorType.UNMARSHELLER, unMarshallerIndex);
        assertThat(posController.getPos(ProcessorType.UNMARSHELLER), is(unMarshallerIndex));

        posController.updatePos(ProcessorType.BUSINESS_PROCESSOR, businessProcIndex);
        assertThat(posController.getPos(ProcessorType.BUSINESS_PROCESSOR), is(businessProcIndex));


    }

}