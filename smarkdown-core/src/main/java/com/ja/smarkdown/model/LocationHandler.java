package com.ja.smarkdown.model;

import java.util.List;

import com.ja.smarkdown.model.config.Location;

public interface LocationHandler {

	List<String> listDocuments();

	ResourceInfo loadDocument(final String resource);

	void add(final Location location);

}
