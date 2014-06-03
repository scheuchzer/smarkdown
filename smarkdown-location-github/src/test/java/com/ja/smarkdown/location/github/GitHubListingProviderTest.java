package com.ja.smarkdown.location.github;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;

import com.ja.smarkdown.model.config.Location;

public class GitHubListingProviderTest {

	@Rule
	public AuthTokenRule authTokenRule = new AuthTokenRule();

	@Test
	public void testGetDocumentsSubDir() {
		authTokenRule.assumeAuthToken();
		final Location location = Location
				.create("github:scheuchzer/smarkdown:smarkdown-location-github/src/test/resources");
		authTokenRule.setAuthToken(location);

		final GitHubListingProvider provider = new GitHubListingProvider();
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

		final GitHubListingProvider provider = new GitHubListingProvider();
		final List<String> actual = provider.getDocuments(Arrays
				.asList(new GitHubLocation(location)));

		assertTrue(actual.contains("smarkdown-it.md"));
		assertThat(actual.size(), is(1));
	}

}
