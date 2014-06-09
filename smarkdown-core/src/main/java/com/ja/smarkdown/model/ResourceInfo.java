package com.ja.smarkdown.model;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.ja.smarkdown.model.config.Location;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(exclude = { "inputStream" })
@EqualsAndHashCode(exclude = { "inputStream" })
public class ResourceInfo {

	private final String path;
	private final Location location;
	private final InputStream inputStream;
	private final List<ResourceInfo> overridden = new ArrayList<>();
	
}
