package com.ja.smarkdown.load.classpath;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.ja.smarkdown.load.LoadEvent;
import com.ja.smarkdown.model.MarkdownDocument;

public class ClasspathMarkdownProviderTest {

	@Test
	public void testOnEventRoot() {
		final LoadEvent event = new LoadEvent("classpath:ClasspathTest1.md");
		final ClasspathMarkdownProvider provider = new ClasspathMarkdownProvider();
		provider.onEvent(event);
		assertThat(event.getResults().size(), is(1));
		assertThat(event.getResults().get(0), is(new MarkdownDocument("test1")));
	}

	@Test
	public void testOnEventSubdir() {
		final LoadEvent event = new LoadEvent(
				"classpath:dir1/ClasspathTest2.md");
		final ClasspathMarkdownProvider provider = new ClasspathMarkdownProvider();
		provider.onEvent(event);
		assertThat(event.getResults().size(), is(1));
		assertThat(event.getResults().get(0), is(new MarkdownDocument("test2")));
	}

	@Test
	public void testOnEventNotFound() {
		final LoadEvent event = new LoadEvent("classpath:dir1/foo.md");
		final ClasspathMarkdownProvider provider = new ClasspathMarkdownProvider();
		provider.onEvent(event);
		assertThat(event.getResults().size(), is(0));
	}
}
