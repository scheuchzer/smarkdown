package com.ja.smarkdown.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class Listing {

	private Set<String> files = new HashSet<>();

}
