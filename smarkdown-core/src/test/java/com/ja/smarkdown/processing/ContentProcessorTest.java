package com.ja.smarkdown.processing;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;

import java.util.Arrays;

import javax.enterprise.inject.Instance;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ContentProcessorTest {

	@Mock
	private Instance<MetaDataProcessor> metaDataProcessors;
	@Mock
	private Instance<ContentDataProcessor> contentDataProcessors;

	private ContentProcessor processor = new ContentProcessor();

	@Before
	public void init() {
		processor.setContentDataProcessors(contentDataProcessors);
		processor.setMetaDataProcessors(metaDataProcessors);
	}

	@Test
	public void testProcessing() throws ProcessingException {
		MetaDataProcessor readTestHeader = new AbstractMetaDataProcessor() {

			private boolean started = false;

			@Override
			public void processLine(String line, LineContext ctx) {
				if (!started && "---".equals(line)) {
					started = true;
					ctx.remove();
				} else if (started && line.startsWith("test=")) {
					ctx.addMetaData("test",
							StringUtils.substringAfter(line, "="));
					ctx.remove();
				} else if (started && "---".equals(line)) {
					started = false;
					ctx.remove();
				} else {
					ctx.dontCare();
				}

			}
		};

		ContentDataProcessor addTestTag = new AbstractContentDataProcessor() {

			@Override
			public void end(MetaData metaData, StringBuilder out) {
				out.append("tag=").append(metaData.get("test").get(0));
			}

		};
		doReturn(Arrays.asList(readTestHeader).iterator()).when(
				metaDataProcessors).iterator();
		doReturn(Arrays.asList(addTestTag).iterator()).when(
				contentDataProcessors).iterator();

		String document = "# test\n" + "---\n" + "test=foo\n" + "---\n"
				+ "the content\n";
		String actual = processor.process(document);
		assertThat(actual, is("# test\nthe content\ntag=foo"));

	}
}
