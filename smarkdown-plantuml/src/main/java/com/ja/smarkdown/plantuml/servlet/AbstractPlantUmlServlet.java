package com.ja.smarkdown.plantuml.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.ja.smarkdown.model.config.SmarkdownConfiguration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractPlantUmlServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final static String CFG_KEY_FALLBACK_SERVER = "plantuml.fallback.server";
	private final static String CFG_KEY_FALLBACK_ENABLED = "plantuml.fallback.enabled";
	private final static String DEFAULT_FALLBACK_SERVER = "http://www.plantuml.com/plantuml";
	
	private final String separator;
	private final String mediaType;
	private final FileFormat fileFormat;
	private byte[] graphVizNotInstalledImage;

	@Inject
	private SmarkdownConfiguration configuration;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		checkGravVizNotInstalledImage();
		String uml = new UmlDecoder()
				.decode(getSource(request.getRequestURI()));
		log.debug("Rendering uml={}", uml);
		 
		response.setContentType(mediaType);
		SourceStringReader reader = new SourceStringReader(uml);
		try {
			log.debug("Rendering locally");
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			reader.generateImage(buffer,
					new FileFormatOption(fileFormat, false));

			byte[] img = buffer.toByteArray();
			buffer.close();
			// TODO: Remove any scale attributes and render it beforehand to see if the returned image is the error image or not
			if (isErrorImage(img)) {
				handleFallback(request, response);
			} else {
				response.getOutputStream().write(img);
			}
		} catch (Exception e) {
			response.sendError(500, e.getMessage());
		}
	}

	private boolean isErrorImage(byte[] img) {
		return Arrays.equals(img, graphVizNotInstalledImage);
	}

	private void handleFallback(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		if (isFallbackEnabled()) {
		String fallback = String.format("%s%s%s", getFallbackServer(),
				separator, getSource(request.getRequestURI()));
		log.debug(
				"Local rendering failed. Will redirect to fallbackServer={}",
				fallback);
		response.sendRedirect(fallback);
		} else {
			response.getOutputStream().write(graphVizNotInstalledImage);
		}
	}

	private boolean isFallbackEnabled() {
		String value = configuration.getExtensions().get(CFG_KEY_FALLBACK_ENABLED);
		if (value == null) {
			return true;
		}
		return Boolean.valueOf(value);
	}

	private String getFallbackServer() {
		String value = configuration.getExtensions().get(
				CFG_KEY_FALLBACK_SERVER);
		return StringUtils.isEmpty(value) ? DEFAULT_FALLBACK_SERVER : value;
	}

	/**
	 * NOTE: plantUml tries to call graphViz on your host. This will end with
	 * displaying a stacktrace (coded by plantuml) in your log once. If graphViz
	 * isn't available plantUml produces an error image without any further
	 * notice. So we store that image and use it in later requests to detect if
	 * we have to rederect to the fallback server if graphViz is not available.
	 */
	private void checkGravVizNotInstalledImage() {
		if (graphVizNotInstalledImage == null) {
			/*
			 * Try to paint a class diagram. Class diagrams require graphViz. We
			 * paint two classes with quite nonsense names that nobody will use
			 * ever.
			 */
			SourceStringReader reader = new SourceStringReader(
					"@startuml\nERAOIFAWEJFKSLDFJ <|-- fj2salrj3302dsfa\n@enduml");
			try {
				log.debug("Testing for GraphViz capabilities.");
				ByteArrayOutputStream buffer = new ByteArrayOutputStream();
				reader.generateImage(buffer, new FileFormatOption(fileFormat,
						false));
				// If graphViz isn't available, this is the error image now
				// If graphViz is available, this is image will never be equal
				// to any other image
				// that gets generated by anybody (remember the stupid class
				// names)
				graphVizNotInstalledImage = buffer.toByteArray();
			} catch (Exception e) {
				log.warn("GraphViz testing failed.", e);
			}
		}

	}

	protected String getSource(String uri) {
		String[] result = uri.split(separator, 2);
		if (result.length != 2) {
			return "";
		} else {
			return result[1];
		}
	}

}