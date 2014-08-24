package com.ja.smarkdown.location.file;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;

import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.ja.smarkdown.json.ListingParser;
import com.ja.smarkdown.model.Listing;
import com.ja.smarkdown.model.config.Location;

@RunWith(MockitoJUnitRunner.class)
public class FileListingProviderTest {

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();
	@Mock
	private ListingParser parser;
	@InjectMocks
	private FileListingProvider provider = new FileListingProvider();

	@Before
	public void init() throws IOException {
		folder.newFile("foo.md");
		folder.newFolder("sub");
		folder.newFile("sub/bar.md");
	}

	@Test
	public void getDocumentsSubDir() {
		final Location location = Location.create(String.format(
				"file://%s/sub", folder.getRoot().getAbsolutePath()));

		final List<String> actual = provider.getDocuments(Arrays
				.asList(location));
		assertTrue(actual.contains("bar.md"));
		assertThat(actual.size(), is(1));
	}

	@Test
	public void getDocuments() {
		final Location location = Location.create(String.format("file://%s",
				folder.getRoot().getAbsolutePath()));

		final List<String> actual = provider.getDocuments(Arrays
				.asList(location));
		assertTrue(actual.contains("foo.md"));
		assertTrue(actual.contains("sub/bar.md"));
	}

	@Test
	public void getDocumentsWithListingFile() throws IOException {
		folder.newFile("listing.json");
		Listing listing = new Listing();
		listing.getFiles().add("fromListing.md");
		doReturn(listing).when(parser).parse(any(Reader.class));
		final Location location = Location.create(String.format("file://%s",
				folder.getRoot().getAbsolutePath()));

		final List<String> actual = provider.getDocuments(Arrays
				.asList(location));
		assertThat(actual.size(), is(1));
		assertTrue(actual.contains("fromListing.md"));

	}

	@Test
	public void getDocumentsWithMountPoint() {
		final Location location = Location.create(String.format("file://%s",
				folder.getRoot().getAbsolutePath()));
		location.getConfig().put(Location.Properties.mountPoint.toString(),
				"test/foo");

		final List<String> actual = provider.getDocuments(Arrays
				.asList(location));
		assertTrue(actual.contains("test/foo/foo.md"));
		assertTrue(actual.contains("test/foo/sub/bar.md"));
	}
}
