package com.ja.smarkdown;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

import lombok.Data;

import org.apache.commons.lang3.StringUtils;

import com.ja.smarkdown.load.ResourceLoader;
import com.ja.smarkdown.model.ResourceInfo;
import com.ja.smarkdown.preprocessing.MarkdownPreprocessor;
import com.ja.smarkdown.util.UrlUtils;

@ManagedBean
@RequestScoped
@Data
public class Index {

	@Inject
	private MarkdownPreprocessor preprocessor;
	@Inject
	private ResourceLoader loader;

	private String page = "index";
	
	private Boolean checkDuplicates;

	public String getContent() {
		System.out.println("#############"+checkDuplicates);
		final ResourceInfo doc = loader.loadResource(getPageName() + ".md", checkDuplicates);
		if (doc == null) {
			return "Page not found.";
		}
		return preprocessor.process(page, doc);
	}

	public String getPageName() {
		return StringUtils.removeEnd(UrlUtils.decode(page), ".html");
	}
}
