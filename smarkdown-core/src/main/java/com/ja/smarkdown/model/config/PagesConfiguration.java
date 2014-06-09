package com.ja.smarkdown.model.config;

import lombok.Data;

@Data
public class PagesConfiguration {

	private String theme = "bootstrap";
	
	private boolean checkForDuplicates = false;
}
