package com.thecrunchycorner.lmax.msgstore;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.thecrunchycorner.lmax.services.SystemProperties;
import java.util.OptionalInt;
import org.junit.Assert;
import org.junit.Test;

public class RingBufferMinimumSizeTest {
    @Test
    public void test() {
        final RingBufferStore<Integer> buffer = new RingBufferStore<>(4);
        final OptionalInt value = SystemProperties.getAsInt("threshold.buffer.minimum.size");
        if (value.isPresent()) {
            assertThat(buffer.size(), is(value.getAsInt()));
        } else {
            Assert.fail();
        }
    }
}
