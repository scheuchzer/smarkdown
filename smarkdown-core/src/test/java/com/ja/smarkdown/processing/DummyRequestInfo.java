package com.ja.smarkdown.processing;

public class DummyRequestInfo implements RequestInfo{

	@Override
	public String getDirectory() {
		return "/abc";
	}

	@Override
	public String getBaseUrl() {
		return "http://localhost:8080/smarkdown";
	}

	@Override
	public String getPath() {
		return "abc/index.html";
	}

	@Override
	public Object getExternalContext() {
		return null;
	}
}
