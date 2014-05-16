package com.ja.smarkdown.load.webinf;

import com.ja.smarkdown.load.AbstractServletContextListingProvider;

public class WebInfListingProvider extends
		AbstractServletContextListingProvider {

	protected WebInfListingProvider() {
		super("web-inf:", "/WEB-INF");
	}
}
