package com.ja.smarkdown.load;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.ja.smarkdown.model.config.Location;

public class DocumentFilterTest {

	private final List<String> documents = Arrays.asList("META-INF/a.md",
			"abc/123.md", "abc/456.md", "abc/index.md", "def/123.md",
			"index.md");

	@Test
	public void testFilterDefaultFilter() {
		final DocumentFilter filter = new DocumentFilter();
		final Location location = Location.create("bla://");
		location.getIncludes().add(".*");
		final List<String> actual = filter.filter(documents, location);
		assertThat(actual.size(), is(5));
		assertTrue(actual.contains("abc/123.md"));
		assertTrue(actual.contains("abc/456.md"));
		assertTrue(actual.contains("abc/index.md"));
		assertTrue(actual.contains("def/123.md"));
		assertTrue(actual.contains("index.md"));
	}

	@Test
	public void testFilterDirFilter() {
		final DocumentFilter filter = new DocumentFilter();
		final Location location = Location.create("bla://");
		location.setIncludes(new ArrayList<String>());
		location.getIncludes().add("abc/.*");
		final List<String> actual = filter.filter(documents, location);
		assertThat(actual.size(), is(3));
		assertTrue(actual.contains("abc/123.md"));
		assertTrue(actual.contains("abc/456.md"));
		assertTrue(actual.contains("abc/index.md"));
	}

	@Test
	public void testFilterDirFileFilter() {
		final DocumentFilter filter = new DocumentFilter();
		final Location location = Location.create("bla://");
		location.setIncludes(new ArrayList<String>());
		location.getIncludes().add("abc/index.*");
		final List<String> actual = filter.filter(documents, location);
		assertThat(actual.size(), is(1));
		assertTrue(actual.contains("abc/index.md"));
	}

	@Test
	public void testFilterDirExcludeFilesFilter() {
		final DocumentFilter filter = new DocumentFilter();
		final Location location = Location.create("bla://");
		location.setIncludes(new ArrayList<String>());
		location.getIncludes().add("abc/.*");
		location.getExcludes().add(".*/index.*");
		final List<String> actual = filter.filter(documents, location);
		assertThat(actual.size(), is(2));
		assertTrue(actual.contains("abc/123.md"));
		assertTrue(actual.contains("abc/456.md"));
	}

	@Test
	public void testFilterExcludeFilesFilter() {
		final DocumentFilter filter = new DocumentFilter();
		final Location location = Location.create("bla://");
		location.setIncludes(new ArrayList<String>());
		location.getIncludes().add(".*");
		location.setExcludes(new ArrayList<String>());
		location.getExcludes().add(".*index.*");
		final List<String> actual = filter.filter(documents, location);
		assertThat(actual.size(), is(4));
		assertTrue(actual.contains("META-INF/a.md"));
		assertTrue(actual.contains("abc/123.md"));
		assertTrue(actual.contains("abc/456.md"));
		assertTrue(actual.contains("def/123.md"));
	}
}
