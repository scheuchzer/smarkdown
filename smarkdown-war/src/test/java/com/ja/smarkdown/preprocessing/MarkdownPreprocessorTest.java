package com.ja.smarkdown.preprocessing;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;

import javax.servlet.ServletContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MarkdownPreprocessorTest {

	@Mock
	private ServletContext servletContext;
	@InjectMocks
	private MarkdownPreprocessor processor = new MarkdownPreprocessor();

	@Test
	public void testProcessInternalLink() {
		doReturn("smarkdown").when(servletContext).getContextPath();
		final String actual = processor.process("foo/index.html",
				"lorem ipsum [alt](test1.md)");
		assertThat(actual, is("lorem ipsum [alt](test1.html)"));
	}

	@Test
	public void testProcessSingleImage() {
		doReturn("smarkdown").when(servletContext).getContextPath();
		final String actual = processor.process("foo/index.html",
				"lorem ipsum ![alt](test1.jpg)");
		assertThat(actual,
				is("lorem ipsum ![alt](smarkdown/raw/foo/test1.jpg)"));
	}

	@Test
	public void testProcessSingleImageIndex() {
		doReturn("smarkdown").when(servletContext).getContextPath();
		final String actual = processor.process("index.html",
				"lorem ipsum ![alt](test1.jpg)");
		assertThat(actual, is("lorem ipsum ![alt](smarkdown/raw/test1.jpg)"));
	}

	@Test
	public void testProcessSameImageTwice() {
		doReturn("smarkdown").when(servletContext).getContextPath();
		final String actual = processor.process("foo/index.html",
				"lorem ipsum ![alt](test1.jpg) ![alt](test1.jpg)");
		assertThat(
				actual,
				is("lorem ipsum ![alt](smarkdown/raw/foo/test1.jpg) ![alt](smarkdown/raw/foo/test1.jpg)"));
	}

	@Test
	public void testProcessRelativePath() {
		doReturn("smarkdown").when(servletContext).getContextPath();
		final String actual = processor.process("foo/index.html",
				"lorem ipsum ![alt](../test1.jpg)");
		assertThat(actual,
				is("lorem ipsum ![alt](smarkdown/raw/foo/../test1.jpg)"));
	}

	@Test
	public void testProcessRootPath() {
		doReturn("smarkdown").when(servletContext).getContextPath();
		final String actual = processor.process("foo/index.html",
				"lorem ipsum ![alt](/test1.jpg)");
		assertThat(actual, is("lorem ipsum ![alt](/test1.jpg)"));
	}

	@Test
	public void testProcessHttp() {
		doReturn("smarkdown").when(servletContext).getContextPath();
		final String actual = processor.process("foo/index.html",
				"lorem ipsum ![alt](http://www.exampled.com/foo.png)");
		assertThat(actual,
				is("lorem ipsum ![alt](http://www.exampled.com/foo.png)"));
	}
}
