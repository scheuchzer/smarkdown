package com.ja.smarkdown;

import java.net.HttpURLConnection;

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
import com.ja.smarkdown.util.UrlUtils;

@Named
@RequestScoped
@Data
public class Index {

	@Inject
	private ContentProcessor contentProcessor;
	@Inject
	private ResourceLoader loader;
	@Inject
	private ServletContext servletContext;
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
		try {
			return contentProcessor.process(doc,
					ServletRequestInfo.create(page, servletContext));
		} catch (ProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	public String getPageName() {
		return StringUtils.removeEnd(UrlUtils.decode(page), ".html");
	}
}
