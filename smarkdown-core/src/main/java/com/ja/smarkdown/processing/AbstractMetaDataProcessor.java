package com.ja.smarkdown.processing;

import javax.enterprise.context.Dependent;

import lombok.Getter;

@Dependent
public abstract class AbstractMetaDataProcessor implements MetaDataProcessor {

	@Getter
	private MetaData metaData;

	@Override
	public void start(MetaData metaData) {
		this.metaData = metaData;

	}

	@Override
	public void processLine(String line, LineContext ctx) {

	}

	@Override
	public void end(MetaData metaData) {

	}

}
