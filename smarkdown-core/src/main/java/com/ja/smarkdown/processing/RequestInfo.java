package com.ja.smarkdown.processing;

public interface RequestInfo {
	public String getPath();
	public String getDirectory();
	public String getBaseUrl();
	public Object getExternalContext();
}
