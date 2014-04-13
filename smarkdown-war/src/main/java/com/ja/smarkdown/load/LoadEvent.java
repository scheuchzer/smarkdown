package com.ja.smarkdown.load;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import com.ja.smarkdown.model.MarkdownDocument;

@Data
public class LoadEvent {

	private final List<MarkdownDocument> results = new ArrayList<>();
	
	private final String documentUrl;
	
	public void addResult(MarkdownDocument document) {
		results.add(document);
	}
	
}
