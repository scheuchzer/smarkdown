package com.ja.smarkdown;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import lombok.Data;

import org.apache.commons.lang3.StringUtils;

import com.ja.smarkdown.load.MarkdownLoader;
import com.ja.smarkdown.model.MarkdownDocument;

@ManagedBean
@RequestScoped
@Data
public class Slides {

	@Inject
	private MarkdownLoader loader;

	@Inject
	private App app;

	private String page;

	public String getContent() {
		final MarkdownDocument doc = loader.loadDocument(getPageName() + ".md");
		if (doc == null) {
			return "Page not found.";
		}
		return doc.getContent();
	}

	public String getPageName() {
		return StringUtils.substringBetween(page, "slides/", ".html");
	}

	public String getTheme() {
		final String requestedTheme = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap().get("theme");
		return requestedTheme != null ? requestedTheme : app.getSlidesTheme();
	}

	public String getTransition() {
		final String requestedTransition = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap()
				.get("transition");
		return requestedTransition != null ? requestedTransition : app
				.getSlidesTransition();
	}
}
