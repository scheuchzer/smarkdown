package com.ja.smarkdown.javascript;

import lombok.Data;

@Data
public class Script {

	private final String library;
	private final String name;
	private final String src;

	public Script(String src) {
		this.src = src;
		library = null;
		name = null;
	}

	public Script(String library, String name) {
		this.library = library;
		this.name = name;
		src = null;
	}
}
