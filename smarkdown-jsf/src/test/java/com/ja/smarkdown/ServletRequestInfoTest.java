package com.ja.smarkdown;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import javax.servlet.ServletContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ServletRequestInfoTest {

	@Mock
	private ServletContext servletContext;

	@Test
	public void testCreateNoDir() {
		final ServletRequestInfo actual = ServletRequestInfo.create("index.html",
				servletContext);
		assertThat(actual.getPath(), is("index.html"));
		assertThat(actual.getDirectory(), is(""));
	}

	@Test
	public void testCreateDir() {
		final ServletRequestInfo actual = ServletRequestInfo.create(
				"foo/index.html", servletContext);
		assertThat(actual.getPath(), is("foo/index.html"));
		assertThat(actual.getDirectory(), is("/foo"));
	}

	@Test
	public void testCreateDeepDir() {
		final ServletRequestInfo actual = ServletRequestInfo.create(
				"foo/bar/index.html", servletContext);
		assertThat(actual.getPath(), is("foo/bar/index.html"));
		assertThat(actual.getDirectory(), is("/foo/bar"));
	}
}
