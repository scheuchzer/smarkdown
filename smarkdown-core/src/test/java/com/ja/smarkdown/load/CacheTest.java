package com.ja.smarkdown.load;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.ja.smarkdown.model.config.Location;

public class CacheTest {

	@Test
	public void testPutAndGet() throws Exception {
		final Cache cache = new Cache();

		final Location loc1 = Location.create("classpath:");
		loc1.setCacheDuration(10);

		final Location loc2 = Location.create("classpath:");
		loc2.setCacheDuration(2000);

		final List<String> docs1 = Arrays.asList("Doc1");
		final List<String> docs2 = Arrays.asList("Doc2");

		cache.put(loc1, docs1);
		cache.put(loc2, docs2);

		Thread.sleep(50);

		final List<String> actual1 = cache.get(loc1);
		final List<String> actual2 = cache.get(loc2);

		assertThat(actual1, is(nullValue()));
		assertThat(actual2, is(docs2));
	}
}
