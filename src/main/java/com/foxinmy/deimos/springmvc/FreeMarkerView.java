package com.foxinmy.deimos.springmvc;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import freemarker.template.SimpleHash;

public class FreeMarkerView extends org.springframework.web.servlet.view.freemarker.FreeMarkerView {
	
	private static final String BASE = "base";

	@Override
	protected SimpleHash buildTemplateModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response) {
		SimpleHash superHash = super.buildTemplateModel(model, request,
				response);
		superHash.put(BASE, request.getContextPath());
		return superHash;
	}

}
