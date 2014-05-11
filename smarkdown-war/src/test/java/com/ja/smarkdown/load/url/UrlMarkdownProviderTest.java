package com.ja.smarkdown.load.url;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.ja.smarkdown.load.LoadEvent;
import com.ja.smarkdown.model.ResourceInfo;

public class UrlMarkdownProviderTest {

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	private String workingDir;

	@Before
	public void init() throws IOException {
		folder.newFile("foo.md");
		folder.newFolder("sub");
		folder.newFile("sub/bar.md");
		workingDir = folder.getRoot().getAbsolutePath();
	}

	@Test
	public void testOnEventFile() {
		final LoadEvent event = new LoadEvent(String.format("file://%s/foo.md",
				workingDir));
		final UrlMarkdownProvider provider = new UrlMarkdownProvider();
		provider.onEvent(event);
		assertThat(event.getResults().size(), is(1));
		assertThat(
				event.getResults().get(0),
				is(new ResourceInfo(UrlMarkdownProvider.class, String.format(
						"file:%s/foo.md", workingDir), null)));
	}

	@Test
	public void testOnEventSubdir() {
		final LoadEvent event = new LoadEvent(String.format(
				"file://%s/sub/bar.md", workingDir));
		final UrlMarkdownProvider provider = new UrlMarkdownProvider();
		provider.onEvent(event);
		assertThat(event.getResults().size(), is(1));
		assertThat(
				event.getResults().get(0),
				is(new ResourceInfo(UrlMarkdownProvider.class, String.format(
						"file:%s/sub/bar.md", workingDir), null)));
	}

	@Test
	public void testOnEventNotFound() {
		final LoadEvent event = new LoadEvent(String.format(
				"file://%s/blabla.md", workingDir));
		final UrlMarkdownProvider provider = new UrlMarkdownProvider();
		provider.onEvent(event);
		assertThat(event.getResults().size(), is(0));
	}
}
