package com.ja.smarkdown.plantuml.servlet;

import javax.servlet.annotation.WebServlet;

import net.sourceforge.plantuml.FileFormat;

@WebServlet(urlPatterns = { "/raw/plantuml/txt/*" })
public class PlantUmlAsciiServlet extends AbstractPlantUmlServlet {

	private static final long serialVersionUID = 1L;

	public PlantUmlAsciiServlet() {
		super("/txt/", "text/plain;charset=UTF-8", FileFormat.UTXT);
	}

}