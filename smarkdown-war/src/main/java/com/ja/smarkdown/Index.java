package com.ja.smarkdown;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

import lombok.Data;

import org.apache.commons.lang3.StringUtils;

import com.ja.smarkdown.load.MarkdownLoader;
import com.ja.smarkdown.model.MarkdownDocument;
import com.ja.smarkdown.preprocessing.MarkdownPreprocessor;
import com.ja.smarkdown.util.UrlUtils;

@ManagedBean
@RequestScoped
@Data
public class Index {

	@Inject
	private MarkdownLoader loader;

	private String page = "index";

	public String getContent() {
		final MarkdownDocument doc = loader.loadDocument(getPageName() + ".md");
		if (doc == null) {
			return "Page not found.";
		}
		return new MarkdownPreprocessor().process(doc.getContent());
	}

	public String getPageName() {
		return StringUtils.removeEnd(UrlUtils.decode(page), ".html");
	}
}
