package com.ja.smarkdown.json;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.util.Set;

import org.junit.Test;

import com.ja.smarkdown.model.Listing;

public class ListingParserTest {

	@Test
	public void testParse() {
		final ListingParser parser = new ListingParser();
		final String content = "{\"files\":[\"smarkdown/1.md\", \"2.md\"]}";
		try (StringReader in = new StringReader(content)) {
			final Set<String> files = parser.parse(in).getFiles();
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
			final Set<String> files = parser.parse(in).getFiles();
			assertThat(files.size(), is(0));
		}
	}

	@Test
	public void testWrite() throws Exception {

		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		final ListingParser parser = new ListingParser();

		final Listing listing = new Listing();
		listing.getFiles().add("foo.md");
		listing.getFiles().add("bar.md");
		parser.write(listing, out);
		final String result = out.toString();
		assertThat(result, is("{\"files\":[\"foo.md\",\"bar.md\"]}"));
	}
}
