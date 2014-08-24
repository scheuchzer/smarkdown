package com.ja.smarkdown;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import com.ja.smarkdown.load.DocumentScanner;
import com.ja.smarkdown.model.ListingDocument;

@WebServlet(urlPatterns = { "/listing.json" })
@Slf4j
public class Listing extends HttpServlet {

	private static final long serialVersionUID = 1L;
	@Inject
	private DocumentScanner scanner;

	@Override
	protected void doGet(final HttpServletRequest req,
			final HttpServletResponse resp) throws ServletException,
			IOException {
		resp.setContentType("application/json");
		PrintWriter writer = resp.getWriter();
		writer.println("{\"files\":[");
		for (ListingDocument ld : scanner.getDocuments()) {
			writer.println(String.format("  \"%s.md\",", ld.getUrlEncodedName()));
		}
		writer.println("]}");
	}

}
