package com.ja.smarkdown.load.github;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

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

	@InjectMocks
	private GitHubDocumentProvider provider;

	@Test
	public void testGetDocumentInRoot() throws Exception {
		final Location location = Location
				.create("github:scheuchzer/smarkdown:");
		final ResourceInfo actual = provider.getDocument(
				Arrays.asList(new GitHubLocation(location)), "README.md");
		assertThat(actual, is(notNullValue()));
		assertThat(actual.getLocation(), is("README.md"));
		assertThat(actual.getInputStream(), is(notNullValue()));
	}

	@Test
	public void testGetDocumentInFolder() throws Exception {
		final Location location = Location
				.create("github:scheuchzer/smarkdown:smarkdown-github/src/test/resources");
		final ResourceInfo actual = provider.getDocument(
				Arrays.asList(new GitHubLocation(location)), "smarkdown-it.md");
		assertThat(actual, is(notNullValue()));
		assertThat(actual.getLocation(), is("smarkdown-it.md"));
		assertThat(actual.getInputStream(), is(notNullValue()));
	}

	// @Test
	// public void testGetDocumentInFolder() throws Exception {
	// doReturn(temp.newFile().toURI().toURL()).when(servletContext)
	// .getResource(eq("/md/index.md"));
	// final Location location = Location.create("webapp:md");
	// final ResourceInfo actual = provider.getDocument(
	// Arrays.asList(location), "index.md");
	// assertThat(actual, is(notNullValue()));
	// assertThat(actual.getLocation(), is("index.md"));
	// assertThat(actual.getInputStream(), is(notNullValue()));
	// }
}
