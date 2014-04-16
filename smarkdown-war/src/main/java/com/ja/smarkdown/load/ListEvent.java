package com.ja.smarkdown.load;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class ListEvent {

	private final String baseLocation;

	private final Set<String> results = new HashSet<>();

	public void addResult(final String documentName) {
		if (results.contains(documentName)) {
			log.warn("Document {} already present.");
		} else {
			results.add(documentName);
		}
	}

	public void addResults(final Set<String> files) {
		for (final String file : files) {
			addResult(file);
		}
	}

}
