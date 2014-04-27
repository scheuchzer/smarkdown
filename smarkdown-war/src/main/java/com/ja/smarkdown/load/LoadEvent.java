package com.ja.smarkdown.load;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import com.ja.smarkdown.model.ResourceInfo;

@Data
public class LoadEvent {

	private final List<ResourceInfo> results = new ArrayList<>();

	private final String documentUrl;

	public void addResult(final ResourceInfo resource) {
		results.add(resource);
	}

}
