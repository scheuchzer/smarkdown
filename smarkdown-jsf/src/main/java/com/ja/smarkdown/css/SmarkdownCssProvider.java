package com.ja.smarkdown.css;

import javax.enterprise.event.Observes;

public class SmarkdownCssProvider {

	public void onEvent(@Observes OutputStylesheetEvent event) {
		event.registerOutputStylesheet("css", "smarkdown.css");
	}
}
