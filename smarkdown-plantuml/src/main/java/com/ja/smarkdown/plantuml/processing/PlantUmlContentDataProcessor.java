package com.ja.smarkdown.plantuml.processing;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

import org.apache.commons.lang3.StringUtils;

import com.ja.smarkdown.plantuml.servlet.UmlEncoder;
import com.ja.smarkdown.processing.AbstractContentDataProcessor;
import com.ja.smarkdown.processing.LineContext;

@Slf4j
public class PlantUmlContentDataProcessor extends AbstractContentDataProcessor {
	private enum Format {
		svg, png, txt
	};

	private boolean inProcessing;
	private List<String> errors = new ArrayList<>();
	private Format format = Format.png;
	private String css;
	private StringBuilder plantUml = new StringBuilder();

	@Override
	public void processLine(String line, LineContext ctx) {
		if (!inProcessing && StringUtils.startsWith(line, "```plant")) {
			inProcessing = true;
			ctx.remove();
		} else if (inProcessing && StringUtils.startsWith(line, "```")) {
			inProcessing = false;
			ctx.remove();

			log.info("Processing plantUml={}", plantUml);
			String uml = StringUtils.trimToEmpty(plantUml.toString());
			if (!uml.startsWith("@start")) {
				plantUml.insert(0, "@startuml\n");
				plantUml.append("\n@enduml");
			}

			String encoded = new UmlEncoder().encode(plantUml.toString());

			if (format.equals(Format.txt)) {
				SourceStringReader reader = new SourceStringReader(
						plantUml.toString());
				try {
					ByteArrayOutputStream buffer = new ByteArrayOutputStream();
					reader.generateImage(buffer, new FileFormatOption(
							FileFormat.UTXT, false));
					ctx.insertAfter(String.format("\n<pre><code>%s\n</code></pre>\n",
							new String(buffer.toByteArray())));
				} catch (Exception e) {
					errors.add("Ascii image creation failed. " + e.getMessage());
				}

			} else {
				final String imgUrl = String.format("%s/raw/plantuml/%s/%s",
						getMetaData().getRequestInfo().getBaseUrl(),
						format.toString(), encoded);
				ctx.insertAfter(String.format("![%s](%s)",
						StringUtils.trimToEmpty(css), imgUrl));
			}
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
			} else {
				plantUml.append(line).append('\n');
			}
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
				case "format":
					format = Format.valueOf(keyValue[1].toLowerCase());
					break;
				case "css":
					css = keyValue[1];
					break;
				default:
					errors.add(String.format(
							"Unknown plantUml configuration [%s]",
							keyValueString));
				}
			} catch (Exception e) {
				errors.add(String.format("Invalid plantUml configuration [%s]",
						keyValueString));
			}
		}
	}

	private void reset() {
		css = null;
		format = Format.png;
		errors = new ArrayList<>();
		plantUml = new StringBuilder();
	}
}
