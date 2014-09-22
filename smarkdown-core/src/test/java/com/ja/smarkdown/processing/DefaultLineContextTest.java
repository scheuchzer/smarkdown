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
		final DefaultLineContext ctx = new DefaultLineContext(
				line, createTestingMetaData());
		ctx.setCurrentOwner(donothing1);
		donothing1.processLine(line, ctx);
		ctx.setCurrentOwner(donothing2);
		donothing2.processLine(line, ctx);

		final String actual = ctx.applyActions();
		assertThat(actual, is(line));
	}

	private MetaData createTestingMetaData() {
		return new MetaData(null, null, new DummyRequestInfo());
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
		final DefaultLineContext ctx = new DefaultLineContext(
				line, createTestingMetaData());
		ctx.setCurrentOwner(donothing1);
		donothing1.processLine(line, ctx);
		ctx.setCurrentOwner(remove);
		remove.processLine(line, ctx);

		final String actual = ctx.applyActions();
		assertThat(actual, isEmptyOrNullString());
	}

	@Test
	public void testApplyInsertBefore() {

		final MetaDataProcessor before = new AbstractMetaDataProcessor() {

			@Override
			public void processLine(final String line, final LineContext ctx) {
				ctx.insertBefore("inserted");
			}

		};

		final String line = "abcdef";
		final DefaultLineContext ctx = new DefaultLineContext(
				line, createTestingMetaData());
		ctx.setCurrentOwner(before);
		before.processLine(line, ctx);

		final String actual = ctx.applyActions();
		assertThat(actual, is("inserted\nabcdef"));
	}

	@Test
	public void testApplyInsertAfter() {

		final MetaDataProcessor after = new AbstractMetaDataProcessor() {

			@Override
			public void processLine(final String line, final LineContext ctx) {
				ctx.insertAfter("inserted");
			}

		};

		final String line = "abcdef";
		final DefaultLineContext ctx = new DefaultLineContext(
				line, createTestingMetaData());
		ctx.setCurrentOwner(after);
		after.processLine(line, ctx);

		final String actual = ctx.applyActions();
		assertThat(actual, is("abcdef\ninserted"));
	}

	@Test
	public void testApplyInsertBeforeAfter() {
		final MetaDataProcessor before = new AbstractMetaDataProcessor() {

			@Override
			public void processLine(final String line, final LineContext ctx) {
				ctx.insertBefore("inserted");
			}

		};
		final MetaDataProcessor after = new AbstractMetaDataProcessor() {

			@Override
			public void processLine(final String line, final LineContext ctx) {
				ctx.insertAfter("inserted");
			}

		};

		final String line = "abcdef";
		final DefaultLineContext ctx = new DefaultLineContext(
				line, createTestingMetaData());
		ctx.setCurrentOwner(before);
		before.processLine(line, ctx);
		ctx.setCurrentOwner(after);
		after.processLine(line, ctx);

		final String actual = ctx.applyActions();
		assertThat(actual, is("inserted\nabcdef\ninserted"));
	}

	@Test
	public void testApplyInsertBeforeRemoveAfter() {
		final MetaDataProcessor before = new AbstractMetaDataProcessor() {

			@Override
			public void processLine(final String line, final LineContext ctx) {
				ctx.insertBefore("inserted");
			}

		};
		final MetaDataProcessor after = new AbstractMetaDataProcessor() {

			@Override
			public void processLine(final String line, final LineContext ctx) {
				ctx.insertAfter("inserted");
			}

		};
		final MetaDataProcessor remove = new AbstractMetaDataProcessor() {

			@Override
			public void processLine(final String line, final LineContext ctx) {
				ctx.remove();
			}

		};

		final String line = "abcdef";
		final DefaultLineContext ctx = new DefaultLineContext(
				line, createTestingMetaData());
		ctx.setCurrentOwner(before);
		before.processLine(line, ctx);
		ctx.setCurrentOwner(after);
		after.processLine(line, ctx);
		ctx.setCurrentOwner(remove);
		remove.processLine(line, ctx);

		final String actual = ctx.applyActions();
		assertThat(actual, is("inserted\ninserted"));
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
		final MetaData metaData = createTestingMetaData();
		final DefaultLineContext ctx = new DefaultLineContext(
				line, metaData);
		ctx.setCurrentOwner(processor);
		processor.processLine(line, ctx);
		List<Object> values = metaData.get("foo");
		assertThat(values.isEmpty(), is(true));
		ctx.applyActions();
		values = metaData.get("foo");
		assertThat(values.size(), is(2));
		assertTrue(values.contains("bar"));
		assertTrue(values.contains("tar"));
		values = metaData.get("zar");
		assertThat(values.isEmpty(), is(true));
	}
}
