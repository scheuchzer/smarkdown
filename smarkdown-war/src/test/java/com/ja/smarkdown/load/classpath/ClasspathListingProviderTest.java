package com.ja.smarkdown.load.classpath;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.ja.smarkdown.load.ListEvent;

public class ClasspathListingProviderTest {

	@Test
	public void onEventSubDir() {
		final ListEvent event = new ListEvent("classpath:dir1");

		final ClasspathListingProvider provider = new ClasspathListingProvider();
		provider.onEvent(event);
		System.out.println(event.getResults());
		assertTrue(event.getResults().contains("ClasspathTest2.md"));
		assertThat(event.getResults().size(), is(1));
	}

	@Test
	public void onEvent() {
		final ListEvent event = new ListEvent("classpath:");

		final ClasspathListingProvider provider = new ClasspathListingProvider();
		provider.onEvent(event);
		System.out.println(event.getResults());
		assertTrue(event.getResults().contains("ClasspathTest1.md"));
		assertTrue(event.getResults().contains("dir1/ClasspathTest2.md"));
	}
}
