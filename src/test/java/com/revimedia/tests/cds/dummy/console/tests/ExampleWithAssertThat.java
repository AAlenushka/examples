package com.revimedia.tests.cds.dummy.console.tests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.revimedia.testing.cds.auto.staticdata.StaticDataAutoMFS;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Demonstrates how Hamcrest matchers can be used with assertThat()
 * using TestNG.
 *
 * @author Joe Walnes
 */

public class ExampleWithAssertThat {

    @Test
    public void usingAssertThatPass() {
        assertThat("xx", is("xx"));
        assertThat("yy", is(not("xx")));
        assertThat("i like cheese", containsString("cheese"));
    }

    @Test
    public void usingAssertThatFail() {
        assertThat("xx", is("xx"));
        assertThat("yy", is(not("xx")));
        assertThat("i like che_ese", containsString("cheese"));
    }

    @Test(enabled = false)
    public void usingAssertThatSkip() {
        assertThat("xx", is("xx"));
        assertThat("yy", is(not("xx")));
        assertThat("i like che_ese", containsString("cheese"));
    }

    @Test
    public void testHamcrestAssert() throws Exception {
        StaticDataAutoMFS staticDataAutoMFS = new StaticDataAutoMFS();
        StaticDataAutoMFS copy = staticDataAutoMFS.clone();
        copy.setMake("qqqqq");
        assertThat(staticDataAutoMFS, is(equalTo(copy)));
    }

    @Test
    public void testTestNGAssert() throws Exception {
        StaticDataAutoMFS staticDataAutoMFS = new StaticDataAutoMFS();
        StaticDataAutoMFS copy = staticDataAutoMFS.clone();
        copy.setMake("qqqqq");
        Assert.assertEquals(staticDataAutoMFS, copy);
    }

}
