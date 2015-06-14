package com.thecrunchycorner.runlog.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class SystemPropertiesGetTest {

    //used for parameterized tests
    @Parameterized.Parameter(value = 0)
    public String keyToGet;

    @Parameterized.Parameter(value = 1)
    public String expectedValue;


    //parameters for each test {testData,expectedResult}
    @Parameterized.Parameters(name = "{index}: testGetUriParametersParameterized({0})={1}")
    public static Collection<Object[]> queryStrings() {
        return Arrays.asList(new Object[][]{
                {"unit.test.value.systemdefault", "Pre-loaded test data"},
                {"unit.test.value.fromfile", "Test data from properties file"},
                {"unit.test.value.undefined", "Undefined property"},
        });
    }


    @Before
    public void setUp() throws Exception {
        SystemProperties.refreshProperties();
        SystemProperties.setProperty("unit.test.value.systemdefault", "Pre-loaded test data");
    }


    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void test() {
        assertThat(SystemProperties.get(keyToGet), is(expectedValue));
    }
}