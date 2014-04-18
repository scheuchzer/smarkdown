package com.ja.smarkdown.json;

import java.io.Reader;
import java.util.HashSet;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@Slf4j
public class ListingParser {

	public Set<String> parse(final Reader in) {
		final Set<String> result = new HashSet<>();
		try {
			final JSONParser parser = new JSONParser();
			final JSONObject obj = (JSONObject) parser.parse(in);
			final JSONArray files = (JSONArray) obj.get("files");
			if (files != null) {
				for (final Object o : files) {
					result.add(o.toString());
				}
			}
		} catch (final Exception e) {
			log.error("Listing parsing failed.", e);
		}
		return result;
	}

}
