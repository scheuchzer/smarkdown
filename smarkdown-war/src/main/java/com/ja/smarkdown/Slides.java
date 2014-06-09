package com.ja.smarkdown;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import lombok.Data;

import org.apache.commons.lang3.StringUtils;

import com.ja.smarkdown.load.ResourceLoader;
import com.ja.smarkdown.model.ResourceInfo;
import com.ja.smarkdown.preprocessing.MarkdownPreprocessor;

@ManagedBean
@RequestScoped
@Data
public class Slides {

	@Inject
	private ResourceLoader loader;
	@Inject
	private MarkdownPreprocessor preprocessor;
	@Inject
	private App app;

	private String page;

	public String getContent() {
		final ResourceInfo doc = loader.loadResource(getPageName() + ".md");
		if (doc == null) {
			return "Page not found.";
		}
		return preprocessor.process(getPageName(), doc);
	}

	public String getPageName() {
		return StringUtils.substringBetween(page, "slides/", ".html");
	}

	public String getTheme() {
		final String requestedTheme = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap().get("theme");
		return requestedTheme != null ? requestedTheme : app.getConfig()
				.getSlides().getTheme();
	}

	public String getTransition() {
		final String requestedTransition = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap()
				.get("transition");
		return requestedTransition != null ? requestedTransition : app
				.getConfig().getSlides().getTransition();
	}
}
