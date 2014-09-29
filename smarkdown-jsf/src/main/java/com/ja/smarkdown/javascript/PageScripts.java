package com.ja.smarkdown.javascript;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class PageScripts {

	@Inject
	private Event<ScriptEvent> events;

	public ScriptEvent getScripts() {
		ScriptEvent event = new ScriptEvent();
		events.fire(event);
		return event;
	}
}
