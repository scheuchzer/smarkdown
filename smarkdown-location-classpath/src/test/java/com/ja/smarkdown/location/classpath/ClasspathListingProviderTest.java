package com.ja.smarkdown.location.classpath;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.ja.smarkdown.location.classpath.ClasspathListingProvider;
import com.ja.smarkdown.model.config.Location;

public class ClasspathListingProviderTest {

	@Test
	public void testGetDocumentsSubDir() {
		final Location location = Location.create("classpath:dir1");

		final ClasspathListingProvider provider = new ClasspathListingProvider();
		final List<String> actual = provider.getDocuments(Arrays
				.asList(location));
		assertTrue(actual.contains("ClasspathTest2.md"));
		assertThat(actual.size(), is(1));
	}

	@Test
	public void testGetDocumentsClasspathRoot() {
		final Location location = Location.create("classpath:");

		final ClasspathListingProvider provider = new ClasspathListingProvider();
		final List<String> actual = provider.getDocuments(Arrays
				.asList(location));
		assertTrue(actual.contains("ClasspathTest1.md"));
		assertTrue(actual.contains("dir1/ClasspathTest2.md"));
		assertThat(actual.size(), is(2));
	}

	@Test
	public void onEventWithMountPoint() {
		final Location location = Location.create("classpath:");
		location.getConfig().put(Location.Properties.mountPoint.toString(),
				"test/foo");

		final ClasspathListingProvider provider = new ClasspathListingProvider();
		final List<String> actual = provider.getDocuments(Arrays
				.asList(location));
		assertTrue(actual.contains("test/foo/ClasspathTest1.md"));
		assertTrue(actual.contains("test/foo/dir1/ClasspathTest2.md"));
	}
}
