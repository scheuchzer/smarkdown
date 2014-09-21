package com.ja.smarkdown.yuml;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.ja.smarkdown.model.ResourceInfo;
import com.ja.smarkdown.model.config.Location;
import com.ja.smarkdown.processing.DefaultLineContext;
import com.ja.smarkdown.processing.MetaData;

public class YumlContentDataProcessorTest {

	@Test
	public void testProcess() {
		List<String> lines = Arrays.asList("test", "```yuml", "[foo]^-[bar]",
				"```");
		ResourceInfo resource = new ResourceInfo("index.html",
				Location.create("foo://index.md"), null);
		YumlContentDataProcessor processor = new YumlContentDataProcessor();
		StringBuilder actual = new StringBuilder();
		for (String line : lines) {
			MetaData metaData = new MetaData(resource, line, null);
			DefaultLineContext ctx = new DefaultLineContext(line, metaData);
			ctx.setCurrentOwner(processor);
			processor.start(metaData, new StringBuilder());
			processor.processLine(line, ctx);
			String newLine = ctx.applyActions();
			if (newLine != null) {
				if (actual.length() > 0) {
					actual.append('\n');
				}
				actual.append(newLine);
			}
		}
		System.out.println(actual);
		assertThat(
				actual.toString(),
				is("test\n<p><img src='http://yuml.me/diagram/scruffy;dir:TB/class/[foo]^-[bar].svg'/></p>"));
	}
}
