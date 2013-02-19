package com.foxinmy.deimos.bean;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.foxinmy.deimos.filter.ResponseInScopeFilter;

public class HttpServletResponseFactoryBean implements
		FactoryBean<HttpServletResponse> {
	@Autowired
	ResponseInScopeFilter responseInScopeFilter;

	@Override
	public HttpServletResponse getObject() throws Exception {
		return responseInScopeFilter.getHttpServletResponse();
	}

	@Override
	public Class<HttpServletResponse> getObjectType() {
		return HttpServletResponse.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

}
