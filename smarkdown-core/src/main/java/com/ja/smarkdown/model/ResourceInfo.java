package com.ja.smarkdown.model;

import java.io.InputStream;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(exclude = { "inputStream" })
@EqualsAndHashCode(exclude = { "inputStream" })
public class ResourceInfo {

	private final Class<?> provider;
	private final InputStream inputStream;

}
