package com.ja.smarkdown.location.http;

import lombok.extern.slf4j.Slf4j;

import com.github.tomakehurst.wiremock.common.Notifier;

@Slf4j
public class Slf4JNotifier implements Notifier {

	@Override
	public void info(String message) {
		log.info(message);
	}

	@Override
	public void error(String message, Throwable t) {
		log.error(message, t);
	}

	@Override
	public void error(String message) {
		log.error(message);
	}

}
