package com.foxinmy.deimos.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class ResponseInScopeFilter implements Filter {
	private ThreadLocal<HttpServletResponse> responses = new ThreadLocal<HttpServletResponse>();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		responses.set(response);
		chain.doFilter(servletRequest, servletResponse);
		responses.remove();
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	public HttpServletResponse getHttpServletResponse() {
		return responses.get();
	}
}
