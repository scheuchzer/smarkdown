package com.ja.smarkdown.processing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public abstract class Action {
	private final Object owner;
	private final ActionType type;
	private Object comment;

	public abstract String apply(final String line, final MetaData md);

}
