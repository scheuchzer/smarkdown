package com.ja.smarkdown.preprocessing;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ProcessingContextTest {

	@Test
	public void testCreateNoDir() {
		final ProcessingContext actual = ProcessingContext.create("index.html");
		assertThat(actual.getPath(), is("index.html"));
		assertThat(actual.getDirectory(), is(""));
	}

	@Test
	public void testCreateDir() {
		final ProcessingContext actual = ProcessingContext
				.create("foo/index.html");
		assertThat(actual.getPath(), is("foo/index.html"));
		assertThat(actual.getDirectory(), is("foo"));
	}
}
