package com.ja.smarkdown;

import javax.inject.Inject;
import javax.inject.Named;

import lombok.Data;

import com.ja.smarkdown.model.config.SmarkdownConfiguration;

@Named
@Data
public class App {

	@Inject
	private SmarkdownConfiguration config;

}
