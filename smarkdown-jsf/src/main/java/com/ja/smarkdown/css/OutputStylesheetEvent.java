package com.ja.smarkdown.css;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OutputStylesheetEvent {
	@Getter
	private final List<OutputStylesheet> outputStylesheets = new ArrayList<>();

	public void registerOutputStylesheet(String library, String name) {
		final OutputStylesheet os = new OutputStylesheet(library, name);
		log.debug("Registering {}", os);
		outputStylesheets.add(os);
	}
}
