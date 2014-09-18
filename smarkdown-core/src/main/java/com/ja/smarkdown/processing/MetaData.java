package com.ja.smarkdown.processing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MetaData {

	private Map<String, List<Object>> data = new HashMap<>();

	public void add(String key, Object value) {
		List<Object> values = data.get(key);
		if (values == null) {
			values = new ArrayList<Object>();
			data.put(key, values);
		}
		values.add(value);
	}

	public List<Object> get(String key) {
		return data.get(key);
	}

}
