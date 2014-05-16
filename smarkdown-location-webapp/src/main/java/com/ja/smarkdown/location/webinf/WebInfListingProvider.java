package com.ja.smarkdown.location.webinf;

import com.ja.smarkdown.location.AbstractServletContextListingProvider;

public class WebInfListingProvider extends
		AbstractServletContextListingProvider {

	protected WebInfListingProvider() {
		super("web-inf:", "/WEB-INF");
	}
}
