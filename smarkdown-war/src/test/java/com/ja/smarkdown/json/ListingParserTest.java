package com.ja.smarkdown.json;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;
import java.util.Set;

import org.junit.Test;

public class ListingParserTest {

	@Test
	public void testParse() {
		final ListingParser parser = new ListingParser();
		final String content = "{\"files\":[\"smarkdown/1.md\", \"2.md\"]}";
		try (StringReader in = new StringReader(content)) {
			final Set<String> files = parser.parse(in);
			assertThat(files.size(), is(2));
			assertTrue(files.contains("smarkdown/1.md"));
			assertTrue(files.contains("2.md"));
		}
	}

	@Test
	public void testParseNoFilesObject() {
		final ListingParser parser = new ListingParser();
		final String content = "{\"foo\":[\"smarkdown/1.md\", \"2.md\"]}";
		try (StringReader in = new StringReader(content)) {
			final Set<String> files = parser.parse(in);
			assertThat(files.size(), is(0));
		}
	}
}
