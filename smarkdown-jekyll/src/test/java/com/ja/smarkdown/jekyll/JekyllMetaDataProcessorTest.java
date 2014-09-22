package com.ja.smarkdown.jekyll;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.ja.smarkdown.processing.MetaData;

public class JekyllMetaDataProcessorTest {

	@Test
	public void testProcess() {
		List<String> lines = Arrays.asList("---", "title: \"Foo Bar\"",
				"foo:bar", "---", "content");
		JekyllMetaDataProcessor processor = new JekyllMetaDataProcessor();
		MetaData metaData = new MetaData(null, null, null);
		processor.start(metaData);
		String actual = ProcessorHelper.processLines(lines, processor, metaData);
		processor.end(metaData);
		
		assertThat(actual, is("content"));
		assertThat(metaData.get("jekyll.title").get(0).toString(), is("Foo Bar"));
		assertThat(metaData.get("jekyll.foo").get(0).toString(), is("bar"));
	}
	
	@Test
	public void testProcessInclude() {
		List<String> lines = Arrays.asList("foo", "{% include JB/setup %}",
				"bar");
		JekyllMetaDataProcessor processor = new JekyllMetaDataProcessor();
		MetaData metaData = new MetaData(null, null, null);
		String actual = ProcessorHelper
				.processLines(lines, processor, metaData);

		assertThat(actual, is("foo\nbar"));
	}
}
