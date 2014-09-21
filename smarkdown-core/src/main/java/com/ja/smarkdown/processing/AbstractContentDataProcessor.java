package com.ja.smarkdown.processing;

import javax.enterprise.context.Dependent;

import lombok.Getter;

@Dependent
public abstract class AbstractContentDataProcessor implements
		ContentDataProcessor {
	@Getter
	private MetaData metaData;

	@Override
	public void start(MetaData metaData, StringBuilder out) {
		this.metaData = metaData;
	}

	@Override
	public void processLine(final String line, final LineContext ctx) {

	}

	@Override
	public void end(MetaData metaData, StringBuilder out) {

	}

}
