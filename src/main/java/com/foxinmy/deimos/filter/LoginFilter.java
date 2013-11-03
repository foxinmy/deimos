package com.foxinmy.deimos.filter;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;

import com.foxinmy.deimos.bean.WebConstantBean;
import com.foxinmy.deimos.model.User;

/**
 * 
 * @author liubin
 * 
 */
public class LoginFilter implements Filter {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	protected String paramValue;

	protected String loginUrl;

	protected Set<String> exceptPatterns = new LinkedHashSet<String>();

	private AntPathMatcher matcher = new AntPathMatcher();

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		paramValue = null;
		loginUrl = null;
		exceptPatterns.clear();
		matcher = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = ((HttpServletRequest) servletRequest);
		String url = request.getServletPath();
		String base = request.getContextPath();
		boolean flag = false;

		if (url.replace(base, "").equals("/")) {
			flag = true;
		} else {
			for (String patternUrl : exceptPatterns) {
				if (StringUtils.isBlank(patternUrl))
					continue;
				if (matcher.match(patternUrl, url)) {
					flag = true;
					break;
				}
			}
		}
		if (flag) {
			chain.doFilter(servletRequest, servletResponse);
		} else {
			HttpSession session = request.getSession();
			HttpServletResponse response = (HttpServletResponse) servletResponse;
			Object obj = session.getAttribute(User.UESR_ID_SESSION_NAME);
			if (obj == null) {
				response.sendRedirect(String.format("%1$s%2$s?%3$s=%4$s", base,loginUrl,WebConstantBean.RETURN_URL_KEY,URLEncoder.encode(request.getRequestURL().toString(),"UTF-8")));
			} else {
				chain.doFilter(servletRequest, servletResponse);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		paramValue = filterConfig.getInitParameter("URL_EXCEPT");
		loginUrl = filterConfig.getInitParameter("LOGIN_URL");
		String[] exceptUrls = paramValue.split(",");
		for (String url : exceptUrls) {
			exceptPatterns.add(url);
		}
	}
}
