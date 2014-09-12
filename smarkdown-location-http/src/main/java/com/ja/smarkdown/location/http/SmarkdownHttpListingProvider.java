package com.ja.smarkdown.location.http;

import java.net.MalformedURLException;
import java.net.URL;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

@Slf4j
public class SmarkdownHttpListingProvider extends AbstractHttpListingProvider {

	@Override
	protected URL resolveListingFileUrl(HttpLocation location,
			String listingFileName) throws MalformedURLException {
		return new URL(String.format("%s/%s",
				StringUtils.substringAfter(location.getUrl(), "smarkdown:"),
				listingFileName));
	}

	@Override
	protected String transformFileName(HttpLocation location, final String file) {
		String target = file;
		if (StringUtils.startsWith(target, "raw/")) {
			target = StringUtils.substringAfter(target, "raw/");
			log.debug("Transforming file name. source={}, target={}", file,
					target);
		}
		return super.transformFileName(location, target);
	}
}
