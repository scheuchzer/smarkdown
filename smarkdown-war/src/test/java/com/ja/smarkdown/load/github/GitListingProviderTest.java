package com.ja.smarkdown.load.github;

import org.junit.Test;

import com.ja.smarkdown.load.ListEvent;
import com.ja.smarkdown.model.config.Location;

public class GitListingProviderTest {

	@Test
	public void testLoad() {
		final String authToken = "16f20b8f8af4f3e6e9f84706d766d05aa06bdfc0";
		final ListEvent event = new ListEvent(
				Location.create("github:scheuchzer/smarkdown"));

		final GitListingProvider provider = new GitListingProvider();
		provider.onEvent(event);

	}
}
