package com.ja.smarkdown.location.github;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Assume;
import org.junit.Test;

import com.ja.smarkdown.location.github.GitHubListingProvider;
import com.ja.smarkdown.location.github.GitHubLocation;
import com.ja.smarkdown.model.config.Location;

public class GitHubListingProviderTest {

	private String authToken = System.getProperty("gitAuthToken");

	@Test
	public void testOnEvent() {
		Assume.assumeTrue(authToken != null);
		final Location location = Location
				.create("github:scheuchzer/smarkdown:smarkdown-github/src/test/resources");
		location.getConfig().put(
				GitHubLocation.Properties.authToken.toString(), authToken);

		final GitHubListingProvider provider = new GitHubListingProvider();
		final List<String> actual = provider.getDocuments(Arrays
				.asList(new GitHubLocation(location)));

		assertTrue(actual.contains("smarkdown-it.md"));
		assertThat(actual.size(), is(1));
	}

}
