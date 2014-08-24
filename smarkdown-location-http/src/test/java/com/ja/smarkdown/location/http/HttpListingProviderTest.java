package com.ja.smarkdown.location.http;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.io.Reader;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.ja.smarkdown.json.ListingParser;
import com.ja.smarkdown.model.Listing;
import com.ja.smarkdown.model.config.Location;

@RunWith(MockitoJUnitRunner.class)
public class HttpListingProviderTest {

	@Mock
	private ListingParser parser;
	@InjectMocks
	private HttpListingProvider provider = new HttpListingProvider();

	@Test
	public void testGetDocuments() {
		final Location location = Location.create("http://skalender.ch/~celli");
		final Listing listing = new Listing();
		when(parser.parse(any(Reader.class))).thenAnswer(new Answer<Listing>() {

			@SuppressWarnings("unchecked")
			@Override
			public Listing answer(InvocationOnMock invocation) throws Throwable {
				Reader reader = (Reader) invocation.getArguments()[0];
				List<String> lines = IOUtils.readLines(reader);
				for (String line : lines) {
					if (line.contains(".md")) {
						listing.getFiles().add(
								StringUtils.substringBetween(line, "\""));
					}
				}
				return listing;
			}

		});
		final List<String> actual = provider.getDocuments(Arrays
				.asList(new HttpLocation(location)));

		assertThat(actual.size(), is(2));
		assertTrue(actual.contains("httptest/test1.md"));
		assertTrue(actual.contains("httptest/test1.md"));
	}

	@Ignore
	@Test
	public void testGetDocumentsHttps() {
		final Location location = Location
				.create("https://skalender.ch/~celli");
		final Listing listing = new Listing();
		when(parser.parse(any(Reader.class))).thenAnswer(new Answer<Listing>() {

			@SuppressWarnings("unchecked")
			@Override
			public Listing answer(InvocationOnMock invocation) throws Throwable {
				Reader reader = (Reader) invocation.getArguments()[0];
				List<String> lines = IOUtils.readLines(reader);
				for (String line : lines) {
					if (line.contains(".md")) {
						listing.getFiles().add(
								StringUtils.substringBetween(line, "\""));
					}
				}
				return listing;
			}

		});
		final List<String> actual = provider.getDocuments(Arrays
				.asList(new HttpLocation(location)));

		assertThat(actual.size(), is(2));
		assertTrue(actual.contains("httptest/test1.md"));
		assertTrue(actual.contains("httptest/test1.md"));
	}

}
