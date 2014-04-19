package com.ja.smarkdown.json;

import java.io.OutputStream;
import java.io.Reader;

import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public abstract class AbstractParser<T> {

	private final ObjectMapper mapper = JsonFactory.create();
	private final Class<T> type;

	protected AbstractParser(final Class<T> type) {
		this.type = type;
	}

	public T parse(final Reader in) {
		return mapper.readValue(in, type);
	}

	public void write(final T object, final OutputStream out) {
		mapper.writeValue(out, object);
	}
}
