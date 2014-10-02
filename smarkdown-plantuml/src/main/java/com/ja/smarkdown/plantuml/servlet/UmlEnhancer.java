package com.ja.smarkdown.plantuml.servlet;

public class UmlEnhancer {

	public String enhance(String text) {
		String uml = text;
		if (!text.startsWith("@start")) {
			StringBuilder buf = new StringBuilder();
			buf.append("@startuml\n");
			buf.append(text);
			if (!text.endsWith("\n")) {
				buf.append("\n");
			}
			buf.append("@enduml");
			uml = buf.toString();
		}
		return uml;
	}
}
