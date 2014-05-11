package com.ja.smarkdown.load.classpath;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.ja.smarkdown.load.ListEvent;
import com.ja.smarkdown.model.config.Location;

public class ClasspathListingProviderTest {

	@Test
	public void onEventSubDir() {
		final Location location = Location.create("classpath:dir1");
		final ListEvent event = new ListEvent(location);

		final ClasspathListingProvider provider = new ClasspathListingProvider();
		provider.onEvent(event);
		System.out.println(event.getResults());
		assertTrue(event.getResults().contains("ClasspathTest2.md"));
		assertThat(event.getResults().size(), is(1));
	}

	@Test
	public void onEvent() {
		final Location location = Location.create("classpath:");
		final ListEvent event = new ListEvent(location);

		final ClasspathListingProvider provider = new ClasspathListingProvider();
		provider.onEvent(event);
		System.out.println(event.getResults());
		assertTrue(event.getResults().contains("ClasspathTest1.md"));
		assertTrue(event.getResults().contains("dir1/ClasspathTest2.md"));
	}

	@Test
	public void onEventWithMountPoint() {
		final Location location = Location.create("classpath:");
		location.getConfig().put(Location.Properties.mountPoint.toString(),
				"test/foo");
		final ListEvent event = new ListEvent(location);

		final ClasspathListingProvider provider = new ClasspathListingProvider();
		provider.onEvent(event);
		System.out.println(event.getResults());
		assertTrue(event.getResults().contains("test/foo/ClasspathTest1.md"));
		assertTrue(event.getResults().contains(
				"test/foo/dir1/ClasspathTest2.md"));
	}
}
