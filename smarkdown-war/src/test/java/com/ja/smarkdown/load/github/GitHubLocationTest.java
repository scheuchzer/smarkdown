package com.ja.smarkdown.load.github;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.ja.smarkdown.model.config.Location;

public class GitHubLocationTest {

	@Test
	public void testGetRepoNameAndPathWithoutPath() {
		final GitHubLocation loc = new GitHubLocation(
				Location.create("github:foo/bar"));
		assertThat(loc.getRepoName(), is("foo/bar"));
		assertThat(loc.getPath(), is("/"));
	}

	@Test
	public void testGetRepoNameAndPath() {
		final GitHubLocation loc = new GitHubLocation(
				Location.create("github:foo/bar:a/b/c.md"));
		assertThat(loc.getRepoName(), is("foo/bar"));
		assertThat(loc.getPath(), is("a/b/c.md"));
	}

}
