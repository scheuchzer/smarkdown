package com.ja.smarkdown.processing.plugins;

import com.ja.smarkdown.model.ResourceInfo;
import com.ja.smarkdown.processing.AbstractContentDataProcessor;
import com.ja.smarkdown.processing.LineContext;
import com.ja.smarkdown.processing.MetaData;

/**
 * Adds a list of duplicate locations to the beginning of the document.
 * 
 * @author Thomas Scheuchzer
 *
 */
public class CheckDuplicatesContentDataProcessor extends
		AbstractContentDataProcessor {

	private boolean addDuplicateInfo;

	@Override
	public void processLine(String line, LineContext ctx) {
		final MetaData metaData = getMetaData();
		if (addDuplicateInfo) {
			final StringBuilder buf = new StringBuilder();
			buf.append("<div class='alert alert-info'>");
			buf.append("Document found in multiple locations!");
			buf.append("<ul>");
			buf.append("<li>")
					.append(metaData.getResource().getLocation().getUrl())
					.append("</li>");
			for (final ResourceInfo ri : metaData.getResource().getOverridden()) {
				buf.append("<li>").append(ri.getLocation().getUrl())
						.append(ri.getPath()).append("</li>");
			}
			buf.append("</ul>");
			buf.append("</div>\n");
			ctx.insertBefore(buf.toString());
			addDuplicateInfo = false;
		}

	}

	public void start(MetaData metaData, StringBuilder out) {
		super.start(metaData, out);
		if (!metaData.getResource().getOverridden().isEmpty()) {
			addDuplicateInfo = true;
		}
	}

}
