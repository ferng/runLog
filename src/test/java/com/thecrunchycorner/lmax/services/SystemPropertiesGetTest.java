package com.thecrunchycorner.lmax.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.OptionalInt;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class SystemPropertiesGetTest {

    //used for parameterized tests
    @Parameterized.Parameter()
    public String keyToGet;

    @Parameterized.Parameter(value = 1)
    public OptionalInt expectedValue;


    //parameters for each test {testData,expectedResult}
    @Parameterized.Parameters(name = "{index}: testGetUriParametersParameterized({0})={1}")
    public static Collection<Object[]> queryStrings() {
        return Arrays.asList(new Object[][] {
                {"threshold.buffer.minimum.size", OptionalInt.of(16)},
                {"unit.test.value.undefined", OptionalInt.empty()},
        });
    }


    @Before
    public void setUp() throws Exception {
        SystemProperties.refreshProperties();
    }


    @Test
    public void test() {
        assertThat(SystemProperties.getAsInt(keyToGet), is(expectedValue));
    }
}