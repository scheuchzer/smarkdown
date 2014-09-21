package com.ja.smarkdown.jekyll;

import java.util.List;

import lombok.experimental.ExtensionMethod;

import org.apache.commons.lang3.StringUtils;

import com.ja.smarkdown.processing.AbstractContentDataProcessor;
import com.ja.smarkdown.processing.LineContext;
import com.ja.smarkdown.processing.MetaData;

@ExtensionMethod(StringUtils.class)
public class JekyllContentDataProcessor extends AbstractContentDataProcessor {

	@Override
	public void start(MetaData metaData, StringBuilder out) {
		super.start(metaData, out);
		List<Object> title = metaData.get("jekyll.title");
		if (!title.isEmpty()) {
			out.append("#").append(title.get(0));
			List<Object> category = metaData.get("jekyll.category");
			if (!category.isEmpty()) {
				out.append(String.format(
						" <span class=\"label label-info\">%s</span> ", category.get(0)));
			}
			out.append('\n');
		}
		List<Object> tags = metaData.get("jekyll.tags");
		if (!tags.isEmpty()) {
			for (Object tag : tags) {
				out.append(String.format(
						"<span class=\"label label-primary\">%s</span> ", tag));
			}
			out.append('\n');
		}
	}

	@Override
	public void processLine(String line, LineContext ctx) {
		if (line.startsWith("{%") && line.contains("endhighlight")
				&& line.endsWith("%}")) {
			ctx.remove();
			ctx.insertAfter("```\n");
		} else if (line.startsWith("{%") && line.contains("highlight")
				&& line.endsWith("%}")) {
			ctx.remove();
			String lang = line.substringBetween("highlight", "%}")
					.trimToEmpty();
			ctx.insertAfter(String.format("```%s", lang));
		}
	}
}
