package com.ja.smarkdown.location.classpath;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;

import com.ja.smarkdown.model.ResourceInfo;
import com.ja.smarkdown.model.config.Location;

public class ClasspathDocumentProviderTest {

	@Test
	public void testGetDocumentClasspathRoot() {
		final Location location = Location.create("classpath:");
		final ClasspathDocumentProvider provider = new ClasspathDocumentProvider();
		final ResourceInfo actual = provider.getDocument(
				Arrays.asList(location), "FileInRoot.md");
		assertThat(actual, is(notNullValue()));
	}

	@Test
	public void testGetDocumentSubDir() {
		final Location location = Location.create("classpath:");
		final ClasspathDocumentProvider provider = new ClasspathDocumentProvider();
		final ResourceInfo actual = provider.getDocument(
				Arrays.asList(location),
				"com/ja/smarkdown/location/classpath/FileInDir.md");
		assertThat(actual, is(notNullValue()));
	}

	@Test
	public void testGetDocumentWithMountPoint() {
		final Location location = Location
				.create("classpath:com/ja/smarkdown/location/classpath");
		location.getConfig().put(Location.Properties.mountPoint.toString(),
				"test/foo");
		final ClasspathDocumentProvider provider = new ClasspathDocumentProvider();
		final ResourceInfo actual = provider.getDocument(
				Arrays.asList(location), "FileInDir.md");
		assertThat(actual, is(notNullValue()));
	}

	@Test
	public void testGetDocumentWithMountPointEndingSlash() {
		final Location location = Location
				.create("classpath:com/ja/smarkdown/location/classpath/");
		location.getConfig().put(Location.Properties.mountPoint.toString(),
				"test/foo");
		final ClasspathDocumentProvider provider = new ClasspathDocumentProvider();
		final ResourceInfo actual = provider.getDocument(
				Arrays.asList(location), "FileInDir.md");
		assertThat(actual, is(notNullValue()));
	}

	@Test
	public void testGetDocumentSubDirWithMountPoint() {
		final Location location = Location
				.create("classpath:com/ja/smarkdown/location/classpath");
		location.getConfig().put(Location.Properties.mountPoint.toString(),
				"test/foo");
		final ClasspathDocumentProvider provider = new ClasspathDocumentProvider();
		final ResourceInfo actual = provider.getDocument(
				Arrays.asList(location), "dir1/FileInDir1.md");
		assertThat(actual, is(notNullValue()));
	}

	/**
	 * Package-Style is not supported, just directory-style
	 */
	@Test
	public void testGetDocumentSubDirWithMountPointPackageStyle() {
		final Location location = Location
				.create("classpath:com.ja.smarkdown.location.classpath");
		location.getConfig().put(Location.Properties.mountPoint.toString(),
				"test/foo");
		final ClasspathDocumentProvider provider = new ClasspathDocumentProvider();
		final ResourceInfo actual = provider.getDocument(
				Arrays.asList(location), "dir1/FileInDir1.md");
		assertThat(actual, is(nullValue()));
	}

	@Test
	public void testGetDocumentSubDirWithMountPointCom() {
		final Location location = Location.create("classpath:com");
		location.getConfig().put(Location.Properties.mountPoint.toString(),
				"test/foo");
		final ClasspathDocumentProvider provider = new ClasspathDocumentProvider();
		final ResourceInfo actual = provider.getDocument(
				Arrays.asList(location),
				"ja/smarkdown/location/classpath/dir1/FileInDir1.md");
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
