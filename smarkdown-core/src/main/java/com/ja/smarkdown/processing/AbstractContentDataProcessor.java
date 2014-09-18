package com.ja.smarkdown.processing;

import javax.enterprise.context.Dependent;

@Dependent
public abstract class AbstractContentDataProcessor implements
		ContentDataProcessor {

	@Override
	public void start(MetaData metaData, StringBuilder out) {

	}

	@Override
	public void processLine(final String line, final LineContext ctx) {

	}

	@Override
	public void end(MetaData metaData, StringBuilder out) {

	}

}
