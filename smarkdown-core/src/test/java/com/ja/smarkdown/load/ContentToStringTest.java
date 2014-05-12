package com.ja.smarkdown.load;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ContentToStringTest {

	@Test
	public void testToString() {
		final String result = ContentToString.of("foo bar").toString();
		assertThat(result, is("foo bar"));
	}

	@Test
	public void testToStringAbbreviation() {
		final String result = ContentToString.of(
				"123456789012345678901234567890123456789012345678901234567890")
				.toString();
		assertThat(result,
				is("12345678901234567890123456789012345678901234567..."));
	}

	@Test
	public void testToStringNull() {
		final String result = ContentToString.of(null).toString();
		assertThat(result, is(nullValue()));
	}
}
