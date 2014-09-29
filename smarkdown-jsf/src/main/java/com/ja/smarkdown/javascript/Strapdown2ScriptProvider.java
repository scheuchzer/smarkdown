package com.ja.smarkdown.javascript;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.enterprise.event.Observes;
import javax.faces.context.FacesContext;

public class Strapdown2ScriptProvider {

	private static final String src = "%s/js/strapdown/0.2/strapdown.js";

	public void onEvent(@Observes ScriptEvent event) {
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		ValueExpression expression = FacesContext
				.getCurrentInstance()
				.getApplication()
				.getExpressionFactory()
				.createValueExpression(elContext, "${request.contextPath}",
						String.class);
		Object path = expression.getValue(elContext);
		String contextPath = String.format(src, path);
		event.registerEndOfBodyScript(contextPath);
	}
}
