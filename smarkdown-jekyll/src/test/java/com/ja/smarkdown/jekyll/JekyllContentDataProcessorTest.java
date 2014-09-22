package com.ja.smarkdown.jekyll;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.ja.smarkdown.processing.MetaData;

public class JekyllContentDataProcessorTest {

	@Test
	public void testStartTitleCatTags() {
		MetaData metaData = new MetaData(null, null, null);
		metaData.add("jekyll.title", "Foo Bar");
		metaData.add("jekyll.category", "Tar");
		metaData.add("jekyll.tags", "A");
		metaData.add("jekyll.tags", "G");

		JekyllContentDataProcessor processor = new JekyllContentDataProcessor();
		StringBuilder actual = new StringBuilder();
		processor.start(metaData, actual);
		System.out.println(actual);
		assertEquals(
				"#Foo Bar <span class=\"label label-info\">Tar</span> \n"
						+ "<span class=\"label label-primary\">A</span> <span class=\"label label-primary\">G</span> \n",
				actual.toString());
	}

	@Test
	public void testStartTitle() {
		MetaData metaData = new MetaData(null, null, null);
		metaData.add("jekyll.title", "Foo Bar");

		JekyllContentDataProcessor processor = new JekyllContentDataProcessor();
		StringBuilder actual = new StringBuilder();
		processor.start(metaData, actual);
		System.out.println(actual);
		assertEquals("#Foo Bar\n", actual.toString());
	}

	@Test
	public void testStartTitleTags() {
		MetaData metaData = new MetaData(null, null, null);
		metaData.add("jekyll.title", "Foo Bar");
		metaData.add("jekyll.tags", "A");

		JekyllContentDataProcessor processor = new JekyllContentDataProcessor();
		StringBuilder actual = new StringBuilder();
		processor.start(metaData, actual);
		System.out.println(actual);
		assertEquals("#Foo Bar\n"
				+ "<span class=\"label label-primary\">A</span> \n",
				actual.toString());
	}

	@Test
	public void testProcessHighlightXml() {
		List<String> lines = Arrays.asList("{% highlight xml %}", "<xml/>",
				"{% endhighlight %}", "content");
		JekyllContentDataProcessor processor = new JekyllContentDataProcessor();
		MetaData metaData = new MetaData(null, null, null);
		String actual = ProcessorHelper
				.processLines(lines, processor, metaData);

		assertThat(actual, is("```xml\n<xml/>\n```\n\ncontent"));
	}

	@Test
	public void testProcessHighlight() {
		List<String> lines = Arrays.asList("{% highlight     %}", "<xml/>",
				"{% endhighlight %}", "content");
		JekyllContentDataProcessor processor = new JekyllContentDataProcessor();
		MetaData metaData = new MetaData(null, null, null);
		String actual = ProcessorHelper
				.processLines(lines, processor, metaData);

		assertThat(actual, is("```\n<xml/>\n```\n\ncontent"));
	}

}
