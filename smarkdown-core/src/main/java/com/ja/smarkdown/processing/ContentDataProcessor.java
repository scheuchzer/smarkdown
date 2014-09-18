package com.ja.smarkdown.processing;

public interface ContentDataProcessor extends LineProcessor {

	void start(MetaData metaData, StringBuilder out);

	void end(MetaData metaData, StringBuilder out);

}
