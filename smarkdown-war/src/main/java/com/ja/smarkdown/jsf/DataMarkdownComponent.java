package com.ja.smarkdown.jsf;

import java.io.IOException;
import java.io.PrintWriter;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

@FacesComponent(value = "data-markdown")
public class DataMarkdownComponent extends UIComponentBase {
	@Override
	public String getFamily() {
		return "dataMarkdownComponent";
	}

	@Override
	public void encodeBegin(final FacesContext context) throws IOException {
		final String value = (String) getAttributes().get("value");
		final PrintWriter out = new PrintWriter(context.getResponseWriter());
		out.println("<section data-markdown data-separator=\"^\\n\\n\\n\" data-vertical=\"^\\n\\n\" data-notes=\"^Note:\">");
		out.println("<script type=\"text/template\">");
		out.println(value);
		out.println("</script>");
		out.println("</section>");

	}
}
