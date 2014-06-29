package com.ja.smarkdown;

import java.net.HttpURLConnection;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Data;

import org.apache.commons.lang3.StringUtils;

import com.ja.smarkdown.load.ResourceLoader;
import com.ja.smarkdown.model.ResourceInfo;
import com.ja.smarkdown.preprocessing.MarkdownPreprocessor;
import com.ja.smarkdown.util.UrlUtils;

@Named
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
		final ResourceInfo doc = loader.loadResource(getPageName() + ".md",
				checkDuplicates);
		if (doc == null) {
			FacesContext.getCurrentInstance().getExternalContext()
					.setResponseStatus(HttpURLConnection.HTTP_NOT_FOUND);
			return "Page not found.";
		}
		return preprocessor.process(page, doc);
	}

	public String getPageName() {
		return StringUtils.removeEnd(UrlUtils.decode(page), ".html");
	}
}
