package com.ja.smarkdown.preprocessing;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import javax.servlet.ServletContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProcessingContextTest {

	@Mock
	private ServletContext servletContext;

	@Test
	public void testCreateNoDir() {
		final ProcessingContext actual = ProcessingContext.create("index.html",
				servletContext);
		assertThat(actual.getPath(), is("index.html"));
		assertThat(actual.getDirectory(), is(""));
	}

	@Test
	public void testCreateDir() {
		final ProcessingContext actual = ProcessingContext.create(
				"foo/index.html", servletContext);
		assertThat(actual.getPath(), is("foo/index.html"));
		assertThat(actual.getDirectory(), is("/foo"));
	}

	@Test
	public void testCreateDeepDir() {
		final ProcessingContext actual = ProcessingContext.create(
				"foo/bar/index.html", servletContext);
		assertThat(actual.getPath(), is("foo/bar/index.html"));
		assertThat(actual.getDirectory(), is("/foo/bar"));
	}
}
