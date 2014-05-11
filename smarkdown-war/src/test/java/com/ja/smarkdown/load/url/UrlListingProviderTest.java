package com.ja.smarkdown.load.url;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.ja.smarkdown.load.ListEvent;
import com.ja.smarkdown.model.config.Location;

public class UrlListingProviderTest {

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
		final ListEvent event = new ListEvent(location);

		final UrlListingProvider provider = new UrlListingProvider();
		provider.onEvent(event);
		System.out.println(event.getResults());
		assertTrue(event.getResults().contains("bar.md"));
		assertThat(event.getResults().size(), is(1));
	}

	@Test
	public void onEvent() {
		final Location location = Location.create(String.format("file://%s",
				folder.getRoot().getAbsolutePath()));
		final ListEvent event = new ListEvent(location);

		final UrlListingProvider provider = new UrlListingProvider();
		provider.onEvent(event);
		System.out.println(event.getResults());
		assertTrue(event.getResults().contains("foo.md"));
		assertTrue(event.getResults().contains("sub/bar.md"));
	}

	@Test
	public void onEventWithMountPoint() {
		final Location location = Location.create(String.format("file://%s",
				folder.getRoot().getAbsolutePath()));
		location.getConfig().put(Location.Properties.mountPoint.toString(),
				"test/foo");
		final ListEvent event = new ListEvent(location);

		final UrlListingProvider provider = new UrlListingProvider();
		provider.onEvent(event);
		System.out.println(event.getResults());
		assertTrue(event.getResults().contains("test/foo/foo.md"));
		assertTrue(event.getResults().contains("test/foo/sub/bar.md"));
	}
}
