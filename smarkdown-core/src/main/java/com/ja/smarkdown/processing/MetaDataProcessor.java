package com.ja.smarkdown.processing;

public interface MetaDataProcessor extends LineProcessor {

	void start(MetaData metaData);

	void end(MetaData metaData);

}
