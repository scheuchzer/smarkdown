package com.ja.smarkdown.plantuml.servlet;

import javax.servlet.annotation.WebServlet;

import net.sourceforge.plantuml.FileFormat;

@WebServlet(urlPatterns = { "/raw/plantuml/png/*" })
public class PlantUmlPngServlet extends AbstractPlantUmlServlet {

	private static final long serialVersionUID = 1L;

	public PlantUmlPngServlet() {
		super("/png/", "image/png", FileFormat.PNG);
	}

}
