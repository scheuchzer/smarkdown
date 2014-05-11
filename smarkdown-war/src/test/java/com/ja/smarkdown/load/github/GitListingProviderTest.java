package com.ja.smarkdown.load.github;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Assume;
import org.junit.Test;

import com.ja.smarkdown.load.ListEvent;
import com.ja.smarkdown.model.config.Location;

public class GitListingProviderTest {

	private String authToken = System.getProperty("gitAuthToken");

	@Test
	public void testOnEvent() {
		Assume.assumeTrue(authToken != null);
		final Location location = Location
				.create("github:scheuchzer/smarkdown:smarkdown-github");
		location.getConfig().put(
				GitHubLocation.Properties.authToken.toString(), authToken);
		final ListEvent event = new ListEvent(location);

		final GitListingProvider provider = new GitListingProvider();
		provider.onEvent(event);

		assertTrue(event.getResults().contains("smarkdown-it.md"));
		assertThat(event.getResults().size(), is(1));
	}

	@Test
	public void testOnEventMountPoint() {
		Assume.assumeTrue(authToken != null);
		final Location location = Location
				.create("github:scheuchzer/smarkdown:smarkdown-github");
		location.getConfig().put(
				GitHubLocation.Properties.authToken.toString(), authToken);
		location.setMountPoint("test");
		final ListEvent event = new ListEvent(location);

		final GitListingProvider provider = new GitListingProvider();
		provider.onEvent(event);

		assertTrue(event.getResults().contains("test/smarkdown-it.md"));
		assertThat(event.getResults().size(), is(1));
	}
}
