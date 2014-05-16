package com.ja.smarkdown.location.file;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.ja.smarkdown.model.ResourceInfo;
import com.ja.smarkdown.model.config.Location;

public class FileDocumentProviderTest {

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
	public void testGetDocumentFile() {
		final Location location = Location.create(String.format("file://%s",
				workingDir));
		final FileDocumentProvider provider = new FileDocumentProvider();
		final ResourceInfo actual = provider.getDocument(
				Arrays.asList(location), "foo.md");
		assertThat(actual, is(notNullValue()));
	}

	@Test
	public void testGetDocumentSubdir() {
		final Location location = Location.create(String.format("file://%s",
				workingDir));
		final FileDocumentProvider provider = new FileDocumentProvider();
		final ResourceInfo actual = provider.getDocument(
				Arrays.asList(location), "sub/bar.md");
		assertThat(actual, is(notNullValue()));
	}

	@Test
	public void testGetDocumentSubdirWithMountPoint() {
		final Location location = Location.create(String.format("file://%s",
				workingDir));
		location.getConfig().put(Location.Properties.mountPoint.toString(),
				"test/foo");
		final FileDocumentProvider provider = new FileDocumentProvider();
		final ResourceInfo actual = provider.getDocument(
				Arrays.asList(location), "test/foo/sub/bar.md");
		assertThat(actual, is(notNullValue()));
	}

	@Test
	public void testGetDocumentNotFound() {
		final Location location = Location.create(String.format("file://%s",
				workingDir));
		final FileDocumentProvider provider = new FileDocumentProvider();
		final ResourceInfo actual = provider.getDocument(
				Arrays.asList(location), "sub/tar.md");
		assertThat(actual, is(nullValue()));
	}
}
