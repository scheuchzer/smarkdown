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

	public void registerHeaderScript(String src) {
		final Script script = new Script(src);
		log.info("Register headerScript {}", script);
		headerScripts.add(script);
	}
	
	public void registerEndOfBodyScriptscript(String src) {
		final Script script = new Script(src);
		log.info("Register endOfBodyScript {}", script);
		endOfBodyScripts.add(script);
	}
}
