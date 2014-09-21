package com.ja.smarkdown;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;

import lombok.Data;

import org.apache.commons.lang3.StringUtils;

import com.ja.smarkdown.load.ResourceLoader;
import com.ja.smarkdown.model.ResourceInfo;
import com.ja.smarkdown.processing.ContentProcessor;
import com.ja.smarkdown.processing.ProcessingException;

@Named
@RequestScoped
@Data
public class Slides {

	@Inject
	private ResourceLoader loader;
	@Inject
	private ContentProcessor contentProcessor;
	@Inject
	private ServletContext servletContext;
	@Inject
	private App app;

	private String page;

	public String getContent() {
		final ResourceInfo doc = loader.loadResource(getPageName() + ".md");
		if (doc == null) {
			return "Page not found.";
		}
		try {
			return contentProcessor.process(doc,
					ServletRequestInfo.create(page, servletContext));
		} catch (ProcessingException e) {
			throw new RuntimeException(e);
		}
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
