package com.ja.smarkdown.processing.plugins;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;

import org.junit.Test;

import com.ja.smarkdown.model.ResourceInfo;
import com.ja.smarkdown.model.config.Location;
import com.ja.smarkdown.processing.DefaultLineContext;
import com.ja.smarkdown.processing.DummyRequestInfo;
import com.ja.smarkdown.processing.MetaData;

public class CheckDuplicatesContentDataProcessorTest {

	@Test
	public void testProcessCheckDuplicateWithDuplicate() {
		String line = "test";
		ResourceInfo resource = new ResourceInfo("index.html",
				Location.create("foo://index.md"), new ByteArrayInputStream(
						line.getBytes()));
		resource.getOverridden().add(
				new ResourceInfo("index.html", Location
						.create("bar://index.md"), null));
		MetaData metaData = new MetaData(resource, line, new DummyRequestInfo());
		CheckDuplicatesContentDataProcessor processor = new CheckDuplicatesContentDataProcessor();
		DefaultLineContext ctx = new DefaultLineContext(line, metaData);
		ctx.setCurrentOwner(processor);
		processor.start(metaData, new StringBuilder());
		processor.processLine(line, ctx);
		String actual = ctx.applyActions();
		assertThat(
				actual,
				is("<div class='alert alert-info'>Document found in multiple locations!<ul><li>foo://index.md</li><li>bar://index.mdindex.html</li></ul></div>\n\ntest"));
	}

	@Test
	public void testProcessCheckDuplicateWithNoDuplicate() {
		String line = "test";
		ResourceInfo resource = new ResourceInfo("index.html",
				Location.create("foo://index.md"), new ByteArrayInputStream(
						line.getBytes()));
		MetaData metaData = new MetaData(resource, line, new DummyRequestInfo());
		CheckDuplicatesContentDataProcessor processor = new CheckDuplicatesContentDataProcessor();
		DefaultLineContext ctx = new DefaultLineContext(line, metaData);
		ctx.setCurrentOwner(processor);
		processor.start(metaData, new StringBuilder());
		processor.processLine(line, ctx);
		String actual = ctx.applyActions();
		assertThat(actual, is("test"));
	}
}
