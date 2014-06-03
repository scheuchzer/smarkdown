package com.ja.smarkdown.location.github;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.ja.smarkdown.model.ResourceInfo;
import com.ja.smarkdown.model.config.Location;

@RunWith(MockitoJUnitRunner.class)
public class GitHubDocumentProviderTest {

	@Rule
	public TemporaryFolder temp = new TemporaryFolder();
	@Rule
	public AuthTokenRule authTokenRule = new AuthTokenRule();
	@InjectMocks
	private GitHubDocumentProvider provider;

	@Test
	@Ignore("Takes a very long time")
	public void testGetDocumentInRoot() throws Exception {
		authTokenRule.assumeAuthToken();
		final Location location = Location
				.create("github:scheuchzer/smarkdown:");
		authTokenRule.setAuthToken(location);
		final ResourceInfo actual = provider.getDocument(
				Arrays.asList(new GitHubLocation(location)), "README.md");
		assertThat(actual, is(notNullValue()));
		assertThat(actual.getInputStream(), is(notNullValue()));
	}

	@Test
	public void testGetDocumentInFolder() throws Exception {
		authTokenRule.assumeAuthToken();
		final Location location = Location
				.create("github:scheuchzer/smarkdown:smarkdown-location-github/src/test/resources");
		authTokenRule.setAuthToken(location);
		final ResourceInfo actual = provider.getDocument(
				Arrays.asList(new GitHubLocation(location)), "smarkdown-it.md");
		assertThat(actual, is(notNullValue()));
		assertThat(actual.getInputStream(), is(notNullValue()));
	}

}
