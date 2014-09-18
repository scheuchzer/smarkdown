package com.ja.smarkdown.processing;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class DefaultLineContextTest {

	@Test
	public void testApplyDoNothing() {

		final MetaDataProcessor donothing1 = new AbstractMetaDataProcessor() {

			@Override
			public void processLine(final String line, final LineContext ctx) {
				ctx.dontCare();
			}

		};
		final MetaDataProcessor donothing2 = new AbstractMetaDataProcessor() {

			@Override
			public void processLine(final String line, final LineContext ctx) {
				ctx.dontCare();
			}

		};

		final String line = "abcdef";
		final DefaultLineContext<MetaDataProcessor> ctx = new DefaultLineContext<MetaDataProcessor>(
				line, new MetaData());
		ctx.setCurrentOwner(donothing1);
		donothing1.processLine(line, ctx);
		ctx.setCurrentOwner(donothing2);
		donothing2.processLine(line, ctx);

		final String actual = ctx.applyActions();
		assertThat(actual, is(line));
	}

	@Test
	public void testApplyDoNothingRemove() {

		final MetaDataProcessor donothing1 = new AbstractMetaDataProcessor() {

			@Override
			public void processLine(final String line, final LineContext ctx) {
				ctx.dontCare();
			}

		};
		final MetaDataProcessor remove = new AbstractMetaDataProcessor() {

			@Override
			public void processLine(final String line, final LineContext ctx) {
				ctx.remove();
			}

		};

		final String line = "abcdef";
		final DefaultLineContext<MetaDataProcessor> ctx = new DefaultLineContext<MetaDataProcessor>(
				line, new MetaData());
		ctx.setCurrentOwner(donothing1);
		donothing1.processLine(line, ctx);
		ctx.setCurrentOwner(remove);
		remove.processLine(line, ctx);

		final String actual = ctx.applyActions();
		assertThat(actual, isEmptyOrNullString());
	}

	@Test
	public void testAddMetaData() {
		final MetaDataProcessor processor = new AbstractMetaDataProcessor() {

			@Override
			public void processLine(final String line, final LineContext ctx) {
				ctx.addMetaData("foo", "bar");
				ctx.addMetaData("foo", "tar");
				ctx.dontCare();
			}

		};

		final String line = "abcdef";
		final MetaData metaData = new MetaData();
		final DefaultLineContext<MetaDataProcessor> ctx = new DefaultLineContext<MetaDataProcessor>(
				line, metaData);
		ctx.setCurrentOwner(processor);
		processor.processLine(line, ctx);
		List<Object> values = metaData.get("foo");
		assertThat(values, is(nullValue()));
		ctx.applyActions();
		values = metaData.get("foo");
		assertThat(values.size(), is(2));
		assertTrue(values.contains("bar"));
		assertTrue(values.contains("tar"));
		values = metaData.get("zar");
		assertThat(values, is(nullValue()));
	}
}
