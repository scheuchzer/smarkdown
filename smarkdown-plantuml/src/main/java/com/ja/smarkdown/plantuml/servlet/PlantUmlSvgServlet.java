package com.ja.smarkdown.plantuml.servlet;

import javax.servlet.annotation.WebServlet;

import net.sourceforge.plantuml.FileFormat;

@WebServlet(urlPatterns = { "/raw/plantuml/svg/*" })
public class PlantUmlSvgServlet extends AbstractPlantUmlServlet {

	private static final long serialVersionUID = 1L;

	public PlantUmlSvgServlet() {
		super("/svg/", "image/svg+xml", FileFormat.SVG);
	}

}
