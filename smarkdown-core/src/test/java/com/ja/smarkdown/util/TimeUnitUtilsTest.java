package com.ja.smarkdown.util;

import static com.ja.smarkdown.util.TimeUnitUtils.parseToMillis;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class TimeUnitUtilsTest {

	@Test
	public void testParseValid() {
		assertThat(parseToMillis("10 MILLISECONDS"), is(10L));
		assertThat(parseToMillis("1 seconds"), is(1000L));
		assertThat(parseToMillis("1 second"), is(1000L));
		assertThat(parseToMillis("1 minutes"), is(60000L));
		assertThat(parseToMillis("1 minute"), is(60000L));
		assertThat(parseToMillis("0"), is(0L));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testParseInvalidNumber() throws Exception {
		parseToMillis("a minute");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testParseInvalidUnit() throws Exception {
		parseToMillis("1 foo");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testParseNull() throws Exception {
		parseToMillis(null);
	}
}
