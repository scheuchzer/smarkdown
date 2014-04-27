package com.ja.smarkdown.load.url;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.Test;

import com.ja.smarkdown.load.LoadEvent;
import com.ja.smarkdown.model.ResourceInfo;

public class UrlMarkdownProviderTest {

	private final String workingDir = new File(".").getAbsoluteFile()
			.getParent() + "/src/test/resources";

	@Test
	public void testOnEventFile() {
		final LoadEvent event = new LoadEvent(String.format(
				"file://%s/ClasspathTest1.md", workingDir));
		final UrlMarkdownProvider provider = new UrlMarkdownProvider();
		provider.onEvent(event);
		assertThat(event.getResults().size(), is(1));
		assertThat(
				event.getResults().get(0),
				is(new ResourceInfo(UrlMarkdownProvider.class, String.format(
						"file:%s/ClasspathTest1.md", workingDir), null)));
	}

	@Test
	public void testOnEventSubdir() {
		final LoadEvent event = new LoadEvent(String.format(
				"file://%s/dir1/ClasspathTest2.md", workingDir));
		final UrlMarkdownProvider provider = new UrlMarkdownProvider();
		provider.onEvent(event);
		assertThat(event.getResults().size(), is(1));
		assertThat(
				event.getResults().get(0),
				is(new ResourceInfo(UrlMarkdownProvider.class, String.format(
						"file:%s/dir1/ClasspathTest2.md", workingDir), null)));
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
