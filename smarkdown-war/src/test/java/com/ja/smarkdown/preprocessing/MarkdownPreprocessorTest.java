package com.ja.smarkdown.preprocessing;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class MarkdownPreprocessorTest {

	@Test
	public void testProcessInternalLink() {
		final MarkdownPreprocessor p = new MarkdownPreprocessor();

		final String actual = p.process("foo/index.html",
				"lorem ipsum [alt](test1.md)");
		assertThat(actual, is("lorem ipsum [alt](test1.html)"));
	}

	@Test
	public void testProcessSingleImage() {
		final MarkdownPreprocessor p = new MarkdownPreprocessor();

		final String actual = p.process("foo/index.html",
				"lorem ipsum ![alt](test1.jpg)");
		assertThat(actual, is("lorem ipsum ![alt](raw/foo/test1.jpg)"));
	}

	@Test
	public void testProcessSameImageTwice() {
		final MarkdownPreprocessor p = new MarkdownPreprocessor();

		final String actual = p.process("foo/index.html",
				"lorem ipsum ![alt](test1.jpg) ![alt](test1.jpg)");
		assertThat(
				actual,
				is("lorem ipsum ![alt](raw/foo/test1.jpg) ![alt](raw/foo/test1.jpg)"));
	}

	@Test
	public void testProcessRelativePath() {
		final MarkdownPreprocessor p = new MarkdownPreprocessor();

		final String actual = p.process("foo/index.html",
				"lorem ipsum ![alt](../test1.jpg)");
		assertThat(actual, is("lorem ipsum ![alt](raw/foo/../test1.jpg)"));
	}

	@Test
	public void testProcessRootPath() {
		final MarkdownPreprocessor p = new MarkdownPreprocessor();

		final String actual = p.process("foo/index.html",
				"lorem ipsum ![alt](/test1.jpg)");
		assertThat(actual, is("lorem ipsum ![alt](/test1.jpg)"));
	}

	@Test
	public void testProcessHttp() {
		final MarkdownPreprocessor p = new MarkdownPreprocessor();

		final String actual = p.process("foo/index.html",
				"lorem ipsum ![alt](http://www.exampled.com/foo.png)");
		assertThat(actual,
				is("lorem ipsum ![alt](http://www.exampled.com/foo.png)"));
	}
}
