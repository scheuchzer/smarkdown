package com.ja.smarkdown.processing.plugins;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import java.io.ByteArrayInputStream;

import org.junit.Test;

import com.ja.smarkdown.model.ResourceInfo;
import com.ja.smarkdown.model.config.Location;
import com.ja.smarkdown.processing.DefaultLineContext;
import com.ja.smarkdown.processing.DummyRequestInfo;
import com.ja.smarkdown.processing.MetaData;

public class ImageContentDataProcessorTest {

	@Test
	public void testProcessImage() {
		String line = "test ![bla](myImg.png)";
		ResourceInfo resource = new ResourceInfo("index.html",
				Location.create("foo://index.md"), new ByteArrayInputStream(
						line.getBytes()));
		MetaData metaData = new MetaData(resource, line, new DummyRequestInfo());
		ImageContentDataProcessor processor = new ImageContentDataProcessor();
		DefaultLineContext ctx = new DefaultLineContext(line, metaData);
		ctx.setCurrentOwner(processor);
		processor.start(metaData, new StringBuilder());
		processor.processLine(line, ctx);
		String actual = ctx.applyActions();
		assertThat(
				actual,
				is("test ![bla](http://localhost:8080/smarkdown/raw/abc/myImg.png)"));
	}
	
}
