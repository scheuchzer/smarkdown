package com.ja.smarkdown.javascript;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ScriptEvent {

	@Getter
	private List<Script> headerScripts = new ArrayList<>();
	@Getter
	private List<Script> endOfBodyScripts = new ArrayList<>();
	@Getter
	private List<Script> headerScriptsHosted = new ArrayList<>();
	@Getter
	private List<Script> endOfBodyScriptsHosted = new ArrayList<>();

	public void registerHeaderScript(String library, String name) {
		final Script script = new Script(library, name);
		log.info("Register headerScript {}", script);
		headerScriptsHosted.add(script);
	}

	public void registerHeaderScript(String src) {
		final Script script = new Script(src);
		log.info("Register headerScript {}", script);
		headerScripts.add(script);
	}

	public void registerEndOfBodyScript(String library, String name) {
		final Script script = new Script(library, name);
		log.info("Register endOfBodyScript {}", script);
		endOfBodyScriptsHosted.add(script);
	}

	public void registerEndOfBodyScript(String src) {
		final Script script = new Script(src);
		log.info("Register endOfBodyScript {}", script);
		endOfBodyScripts.add(script);
	}
}
