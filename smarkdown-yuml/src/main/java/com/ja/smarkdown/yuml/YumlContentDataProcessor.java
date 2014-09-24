package com.ja.smarkdown.yuml;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

import com.ja.smarkdown.processing.AbstractContentDataProcessor;
import com.ja.smarkdown.processing.LineContext;
import com.ja.yuml.render.remote.DiagramType;
import com.ja.yuml.render.remote.Direction;
import com.ja.yuml.render.remote.Style;
import com.ja.yuml.render.remote.YumlRemoteRenderer;

@Slf4j
public class YumlContentDataProcessor extends AbstractContentDataProcessor {

	private boolean inProcessing;

	private Style style = Style.scruffy;
	private Direction direction = Direction.topDown;
	private DiagramType diagramType = null;
	private List<String> errors = new ArrayList<>();

	private StringBuilder yumlDsl = new StringBuilder();

	@Override
	public void processLine(String line, LineContext ctx) {
		if (!inProcessing && StringUtils.startsWith(line, "```yuml")) {
			inProcessing = true;
			ctx.remove();
		} else if (inProcessing && StringUtils.startsWith(line, "```")) {
			inProcessing = false;
			ctx.remove();
			String url = null;
			if (diagramType == null) {
				url = new YumlRemoteRenderer().createUrl(yumlDsl.toString(),
						style, direction);
			} else {
				url = new YumlRemoteRenderer().createUrl(yumlDsl.toString(),
						diagramType, style, direction);
			}
			ctx.insertAfter(String.format("<p><img src='%s'/></p>", url));
			if (!errors.isEmpty()) {
				StringBuilder html = new StringBuilder();
				html.append("<div class='alert alert-error'><ul>");
				for (String error : errors) {
					html.append("<li>").append(error).append("</li>");
				}
				html.append("</ul></div>");
				ctx.insertAfter(html.toString());
			}
			reset();
		} else if (inProcessing) {
			if (StringUtils.startsWith(line, "##")
					&& !StringUtils.startsWith(line, "###")) {
				String keyValueString = StringUtils.trim(StringUtils
						.substringAfter(line, "##"));
				readValue(keyValueString);
			}
			yumlDsl.append(line).append('\n');
			ctx.remove();
		}
	}

	private void readValue(String keyValueString) {
		String[] keyValue = StringUtils.split(keyValueString, "=");
		if (keyValue.length != 2) {
			log.warn("Incomplete yuml config={}", keyValueString);
		} else {
			try {
				switch (keyValue[0]) {
				case "style":
					style = Style.valueOf(keyValue[1]);
					break;
				case "direction":
					direction = Direction.valueOf(keyValue[1]);
					break;
				case "type":
					if ("usecase".equals(keyValue[1])) {
						diagramType = DiagramType.useCaseDiagram;
					} else {
						diagramType = DiagramType.valueOf(keyValue[1]
								+ "Diagram");
					}
					break;
				default:
					errors.add(String.format("Unknown yuml configuration [%s]",
							keyValueString));
				}
			} catch (Exception e) {
				errors.add(String.format("Invalid yuml configuration [%s]",
						keyValueString));
			}
		}
	}

	private void reset() {
		style = Style.scruffy;
		direction = Direction.topDown;
		diagramType = null;
		errors = new ArrayList<>();
		yumlDsl = new StringBuilder();
	}
}
