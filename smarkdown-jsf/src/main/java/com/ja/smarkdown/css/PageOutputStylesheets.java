package com.ja.smarkdown.css;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class PageOutputStylesheets {

	@Inject
	private Event<OutputStylesheetEvent> events;
	
	public List<OutputStylesheet> getStylesheets() {
		OutputStylesheetEvent event = new OutputStylesheetEvent();
		events.fire(event);
		System.out.println("####"+event.getOutputStylesheets());
		return event.getOutputStylesheets();
	}
	
}
