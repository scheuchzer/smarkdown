package com.ja.smarkdown.processing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

import com.ja.smarkdown.model.ResourceInfo;

@Data
public class MetaData {
	private final ResourceInfo resource;
	private final String originalContent;
	private final RequestInfo requestInfo;
	
	private final Map<String, List<Object>> data = new HashMap<>();

	public void add(String key, Object value) {
		List<Object> values = data.get(key);
		if (values == null) {
			values = new ArrayList<Object>();
			data.put(key, values);
		}
		values.add(value);
	}

	public List<Object> get(String key) {
		List<Object> values = data.get(key);
		return values == null ? Collections.emptyList() : values;
	}

}
