package com.ja.smarkdown.location.classpath;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;

import com.ja.smarkdown.location.classpath.ClasspathDocumentProvider;
import com.ja.smarkdown.model.ResourceInfo;
import com.ja.smarkdown.model.config.Location;

public class ClasspathDocumentProviderTest {

	@Test
	public void testGetDocumentClasspathRoot() {
		final Location location = Location.create("classpath:");
		final ClasspathDocumentProvider provider = new ClasspathDocumentProvider();
		final ResourceInfo actual = provider.getDocument(
				Arrays.asList(location), "ClasspathTest1.md");
		assertThat(actual, is(notNullValue()));
	}

	@Test
	public void testGetDocumentSubDir() {
		final Location location = Location.create("classpath:");
		final ClasspathDocumentProvider provider = new ClasspathDocumentProvider();
		final ResourceInfo actual = provider.getDocument(
				Arrays.asList(location), "dir1/ClasspathTest2.md");
		assertThat(actual, is(notNullValue()));
	}

	@Test
	public void testGetDocumentSubDirWithMountPoint() {
		final Location location = Location.create("classpath:");
		location.getConfig().put(Location.Properties.mountPoint.toString(),
				"test/foo");
		final ClasspathDocumentProvider provider = new ClasspathDocumentProvider();
		final ResourceInfo actual = provider.getDocument(
				Arrays.asList(location), "test/foo/dir1/ClasspathTest2.md");
		assertThat(actual, is(notNullValue()));
	}

	@Test
	public void testGetDocumentNotFound() {
		final Location location = Location.create("classpath:");
		final ClasspathDocumentProvider provider = new ClasspathDocumentProvider();
		final ResourceInfo actual = provider.getDocument(
				Arrays.asList(location), "not-found.md");
		assertThat(actual, is(nullValue()));
	}
}
