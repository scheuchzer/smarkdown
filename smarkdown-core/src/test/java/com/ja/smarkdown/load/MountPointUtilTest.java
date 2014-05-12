package com.ja.smarkdown.load;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.ja.smarkdown.model.config.Location;

public class MountPointUtilTest {

	@Test
	public void testApply() {
		final Location loc = Location.create("classpath:");
		loc.getConfig().put(Location.Properties.mountPoint.toString(),
				"test/foo");
		String actualDocument = MountPointUtil.apply(loc, "/bar/test.md");
		assertThat(actualDocument, is("test/foo/bar/test.md"));

		loc.getConfig().put(Location.Properties.mountPoint.toString(),
				"/test/foo");
		actualDocument = MountPointUtil.apply(loc, "bar/test.md");
		assertThat(actualDocument, is("test/foo/bar/test.md"));

		loc.getConfig().put(Location.Properties.mountPoint.toString(),
				"/test/foo/");
		actualDocument = MountPointUtil.apply(loc, "bar/test.md");
		assertThat(actualDocument, is("test/foo/bar/test.md"));
	}

	@Test
	public void testApplyNoMountPoint() {
		final Location loc = Location.create("classpath:");
		final String actualDocument = MountPointUtil.apply(loc, "bar/test.md");
		assertThat(actualDocument, is("bar/test.md"));
	}

	@Test
	public void testRemove() {
		final Location loc = Location.create("classpath:");
		loc.getConfig().put(Location.Properties.mountPoint.toString(),
				"test/foo");
		final String actualDocument = MountPointUtil.remove(loc,
				"test/foo/bar/test.md");
		assertThat(actualDocument, is("bar/test.md"));
	}

	@Test
	public void testRemoveNoMountPoint() {
		final Location loc = Location.create("classpath:");
		final String actualDocument = MountPointUtil.remove(loc,
				"test/foo/bar/test.md");
		assertThat(actualDocument, is("test/foo/bar/test.md"));
	}
}
