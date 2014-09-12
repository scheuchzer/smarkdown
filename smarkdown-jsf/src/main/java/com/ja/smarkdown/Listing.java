package com.ja.smarkdown;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ja.smarkdown.load.DocumentScanner;
import com.ja.smarkdown.model.ListingDocument;

@WebServlet(urlPatterns = { "/listing.json" })
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
		final List<ListingDocument> documents = scanner.getDocuments();
		for (int i = 0; i < documents.size(); i++) {
			final ListingDocument ld = documents.get(i);
			if (i > 0) {
				writer.println(",");
			}
			writer.print(String.format("  \"raw/%s.md\"",
					ld.getUrlEncodedName()));

		}
		writer.print("]}");
	}

}
