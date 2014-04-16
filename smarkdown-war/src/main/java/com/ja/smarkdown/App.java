package com.ja.smarkdown;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import lombok.Data;

import org.apache.commons.lang3.StringUtils;

@ApplicationScoped
@ManagedBean
@Data
public class App {

	private String name;
	private String pagesTheme;
	private String slidesTransition;
	private String slidesTheme;

	@PostConstruct
	public void init() {
		name = getInitParam("smarkdown.app.name", "Smarkdown");
		pagesTheme = getInitParam("smarkdown.pages.theme", "bootstrap");
		slidesTransition = getInitParam("smarkdown.slides.transition",
				"default");
		slidesTheme = getInitParam("smarkdown.slides.theme", "default");
	}

	private String getInitParam(final String name, final String def) {
		final FacesContext ctx = FacesContext.getCurrentInstance();
		String value = ctx.getExternalContext().getInitParameter("appName");
		if (StringUtils.isEmpty(value)) {
			value = def;
		}
		return value;
	}

}
