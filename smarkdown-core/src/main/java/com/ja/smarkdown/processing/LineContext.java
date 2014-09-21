package com.ja.smarkdown.processing;

public interface LineContext {

	void addMetaData(String key, Object value);

	void remove();

	void dontCare();

	void insertBefore(String content);

	void insertAfter(String content);

	void custom(Action action);
}
