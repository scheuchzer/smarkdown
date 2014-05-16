package com.ja.smarkdown.location.file;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.ja.smarkdown.model.config.Location;

public class FileListingProviderTest {

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	@Before
	public void init() throws IOException {
		folder.newFile("foo.md");
		folder.newFolder("sub");
		folder.newFile("sub/bar.md");
	}

	@Test
	public void onEventSubDir() {
		final Location location = Location.create(String.format(
				"file://%s/sub", folder.getRoot().getAbsolutePath()));

		final FileListingProvider provider = new FileListingProvider();
		final List<String> actual = provider.getDocuments(Arrays
				.asList(location));
		assertTrue(actual.contains("bar.md"));
		assertThat(actual.size(), is(1));
	}

	@Test
	public void onEvent() {
		final Location location = Location.create(String.format("file://%s",
				folder.getRoot().getAbsolutePath()));

		final FileListingProvider provider = new FileListingProvider();
		final List<String> actual = provider.getDocuments(Arrays
				.asList(location));
		assertTrue(actual.contains("foo.md"));
		assertTrue(actual.contains("sub/bar.md"));
	}

	@Test
	public void onEventWithMountPoint() {
		final Location location = Location.create(String.format("file://%s",
				folder.getRoot().getAbsolutePath()));
		location.getConfig().put(Location.Properties.mountPoint.toString(),
				"test/foo");

		final FileListingProvider provider = new FileListingProvider();
		final List<String> actual = provider.getDocuments(Arrays
				.asList(location));
		assertTrue(actual.contains("test/foo/foo.md"));
		assertTrue(actual.contains("test/foo/sub/bar.md"));
	}
}
