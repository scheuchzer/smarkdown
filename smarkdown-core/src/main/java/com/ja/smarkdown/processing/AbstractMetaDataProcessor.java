package com.ja.smarkdown.processing;

import javax.enterprise.context.Dependent;

@Dependent
public abstract class AbstractMetaDataProcessor implements MetaDataProcessor {

	@Override
	public void start(MetaData metaData) {

	}

	@Override
	public void processLine(String line, LineContext ctx) {

	}

	@Override
	public void end(MetaData metaData) {

	}

}
