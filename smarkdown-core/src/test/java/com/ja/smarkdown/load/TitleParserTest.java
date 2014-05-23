package com.ja.smarkdown.load;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class TitleParserTest {

	@Test
	public void testToTitleOneWord() {
		final String actual = TitleParser.toTitle("bla");
		assertThat(actual, is("Bla"));
	}

	@Test
	public void testToTitleCamelCase() {
		final String actual = TitleParser.toTitle("fooBar");
		assertThat(actual, is("Foo bar"));
	}

	@Test
	public void testToTitleUnderscore() {
		final String actual = TitleParser.toTitle("foo_bar");
		assertThat(actual, is("Foo bar"));
	}

	@Test
	public void testToTitleDash() {
		final String actual = TitleParser.toTitle("foo-bar");
		assertThat(actual, is("Foo bar"));
	}

	@Test
	public void testToTitleDashSubsection() {
		final String actual = TitleParser.toTitle("foo/bar/foo-bar");
		assertThat(actual, is("Foo bar"));
	}

	@Test
	public void testToTitlePlus() {
		final String actual = TitleParser.toTitle("foo+bar");
		assertThat(actual, is("Foo bar"));
	}
}
