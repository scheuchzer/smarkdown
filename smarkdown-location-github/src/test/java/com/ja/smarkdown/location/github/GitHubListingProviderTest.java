package com.ja.smarkdown.location.github;

import static org.hamcrest.Matchers.greaterThan;
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
import org.junit.Rule;
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
public class GitHubListingProviderTest {

	@Mock
	private ListingParser parser;
	@Rule
	public AuthTokenRule authTokenRule = new AuthTokenRule();
	@InjectMocks
	private GitHubListingProvider provider = new GitHubListingProvider();

	@Test
	public void testGetDocumentsSubDir() {
		authTokenRule.assumeAuthToken();
		final Location location = Location
				.create("github:scheuchzer/smarkdown:smarkdown-location-github/src/test/resources");
		authTokenRule.setAuthToken(location);

		final List<String> actual = provider.getDocuments(Arrays
				.asList(new GitHubLocation(location)));

		assertTrue(actual.contains("smarkdown-it.md"));
		assertThat(actual.size(), is(1));
	}

	@Test
	public void testGetDocuments() {
		authTokenRule.assumeAuthToken();
		final Location location = Location
				.create("github:scheuchzer/smarkdown");
		authTokenRule.setAuthToken(location);

		final List<String> actual = provider.getDocuments(Arrays
				.asList(new GitHubLocation(location)));

		assertThat(actual.size(), is(greaterThan(1)));
	}

	@Test
	public void testGetDocumentsWithListingFile() {
		authTokenRule.assumeAuthToken();
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

		final Location location = Location
				.create("github:scheuchzer/smarkdown-test-listing");
		authTokenRule.setAuthToken(location);

		final List<String> actual = provider.getDocuments(Arrays
				.asList(new GitHubLocation(location)));

		assertThat(actual.size(), is(3));
		assertTrue(actual.contains("fromListing1.md"));
		assertTrue(actual.contains("fromListing2.md"));
		assertTrue(actual.contains("demo/fromListing3.md"));
	}
}
