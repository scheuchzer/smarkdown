package com.ja.smarkdown;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import com.ja.smarkdown.load.DocumentScanner;
import com.ja.smarkdown.model.ListingDocument;
import com.ja.smarkdown.model.Sitemap;

@ManagedBean
@ApplicationScoped
public class Toc {

	@Inject
	private DocumentScanner scanner;

	public ListingDocument[] getPages() {
		return scanner.getDocuments().toArray(new ListingDocument[0]);
	}

	public String getContent() {
		final Sitemap sitemap = new Sitemap();
		sitemap.addAll(scanner.getDocuments());
		return sitemap.toString();
	}
}
