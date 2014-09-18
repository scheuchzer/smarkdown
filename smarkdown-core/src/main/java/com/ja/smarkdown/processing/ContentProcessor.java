package com.ja.smarkdown.processing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContentProcessor {

	@Inject
	@Setter
	private Instance<MetaDataProcessor> metaDataProcessors;
	@Inject
	@Setter
	private Instance<ContentDataProcessor> contentDataProcessors;

	public String process(String content) throws ProcessingException {
		try {
			MetaData metaData = new MetaData();
			List<MetaDataProcessor> metaDataExecutionList = createExecutionOrder(metaDataProcessors
					.iterator());

			startMetaDataProcessing(metaData, metaDataExecutionList);
			StringBuilder contentAfterMetaDataProcessing = processLines(
					content, metaData, metaDataExecutionList);
			endMetaDataProcessing(metaData, metaDataExecutionList);

			List<ContentDataProcessor> contentDataExecutionList = createExecutionOrder(contentDataProcessors
					.iterator());
			StringBuilder result = new StringBuilder();
			startContentDataProcessing(metaData, result,
					contentDataExecutionList);
			StringBuilder contentAfterContentDataProcessing = processLines(
					contentAfterMetaDataProcessing.toString(), metaData,
					contentDataExecutionList);
			result.append(contentAfterContentDataProcessing);
			endContentDataProcessing(metaData, result, contentDataExecutionList);

			return result.toString();
		} catch (IOException e) {
			throw new ProcessingException("Processing failed", e);
		}
	}

	private void startContentDataProcessing(MetaData metaData,
			StringBuilder out,
			List<ContentDataProcessor> contentDataExecutionList) {
		for (ContentDataProcessor processor : contentDataExecutionList) {
			processor.start(metaData, out);
		}

	}

	private void endContentDataProcessing(MetaData metaData, StringBuilder out,
			List<ContentDataProcessor> contentDataExecutionList) {
		for (ContentDataProcessor processor : contentDataExecutionList) {
			processor.end(metaData, out);
		}

	}

	private <T extends LineProcessor> StringBuilder processLines(
			String content, MetaData metaData, List<T> executionList)
			throws IOException {
		final List<DefaultLineContext<T>> dataConfiguration = new ArrayList<>();

		final BufferedReader dataReader = new BufferedReader(new StringReader(
				content));
		String line = null;
		while ((line = dataReader.readLine()) != null) {
			dataConfiguration.add(processLine(line, executionList, metaData));
		}

		StringBuilder contentAfterDataProcessing = new StringBuilder();
		for (DefaultLineContext<T> config : dataConfiguration) {
			String newline = config.applyActions();
			if (newline != null) {
				contentAfterDataProcessing.append(newline).append('\n');
			}
		}
		return contentAfterDataProcessing;
	}

	private void startMetaDataProcessing(MetaData metaData,
			List<MetaDataProcessor> metaDataExecutionList) {
		for (MetaDataProcessor processor : metaDataExecutionList) {
			processor.start(metaData);
		}
	}

	private void endMetaDataProcessing(MetaData metaData,
			List<MetaDataProcessor> metaDataExecutionList) {
		for (MetaDataProcessor processor : metaDataExecutionList) {
			processor.end(metaData);
		}
	}

	private <T extends LineProcessor> DefaultLineContext<T> processLine(
			String line, List<T> executionList, MetaData metaData) {
		DefaultLineContext<T> ctx = new DefaultLineContext<>(line, metaData);
		for (T processor : executionList) {
			ctx.setCurrentOwner(processor);
			processor.processLine(line, ctx);
		}
		return ctx;
	}

	private <T> List<T> createExecutionOrder(Iterator<T> iterator) {
		List<T> processors = new ArrayList<T>();
		while (iterator.hasNext()) {
			processors.add(iterator.next());
		}
		log.info("Processor order is: {}", processors);
		return processors;
	}
}
