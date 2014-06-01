package com.ja.smarkdown.location.classpath;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.ja.smarkdown.model.config.Location;

public class ClasspathListingProviderTest {

	@Test
	public void testGetDocumentsSubDir() {
		final Location location = Location
				.create("classpath:com/ja/smarkdown/location/classpath");

		final ClasspathListingProvider provider = new ClasspathListingProvider();
		final List<String> actual = provider.getDocuments(Arrays
				.asList(location));
		System.out.println(actual);
		assertTrue(actual.contains("FileInDir.md"));
		assertTrue(actual.contains("dir1/FileInDir1.md"));
		assertThat(actual.size(), is(2));
	}

	@Test
	public void testGetDocumentsClasspathRoot() {
		final Location location = Location.create("classpath:");

		final ClasspathListingProvider provider = new ClasspathListingProvider();
		final List<String> actual = provider.getDocuments(Arrays
				.asList(location));
		assertTrue(actual.contains("FileInRoot.md"));
		assertTrue(actual
				.contains("com/ja/smarkdown/location/classpath/FileInDir.md"));
		assertTrue(actual
				.contains("com/ja/smarkdown/location/classpath/dir1/FileInDir1.md"));
	}

}
