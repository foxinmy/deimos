package com.foxinmy.deimos.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.foxinmy.deimos.captcha.CaptchaEngine;
import com.foxinmy.deimos.filter.ResponseInScopeFilter;
import com.foxinmy.jycore.util.json.JsonUtil;

/**
 * @title Action基础类
 * @description 提供对request等对象的常用操作方法
 * @author jy.hu , 2013-1-29
 */
public class BaseAction {

	private static final String HEADER_ENCODING = "UTF-8";
	private static final boolean HEADER_NO_CACHE = true;
	private static final String HEADER_TEXT_CONTENT_TYPE = "text/plain";
	private static final String HEADER_JSON_CONTENT_TYPE = "text/plain";

	public static final String ERROR = "error";
	public static final String NONE = "none";

	public static final String STATUS_PARAMETER_NAME = "status";// 操作状态参数名称
	public static final String MESSAGE_PARAMETER_NAME = "message";// 操作消息参数名称

	// 操作状态（警告、错误、成功）
	public enum Status {
		warn, error, success
	}

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private ResponseInScopeFilter responseInScopeFilter;
	@Autowired
	private ServletContext servletContext;

	protected HttpServletRequest getRequest() {
		return request;
	}
	
	// 获取Response
	protected HttpServletResponse getResponse() {
		return responseInScopeFilter.getHttpServletResponse();
	}

	// 获取ServletContext
	protected ServletContext getServletContext() {
		return servletContext;
	}

	// 获取Attribute
	protected Object getAttribute(String name) {
		return getRequest().getAttribute(name);
	}

	// 设置Attribute
	protected void setAttribute(String name, Object value) {
		getRequest().setAttribute(name, value);
	}

	// 获取Parameter
	protected String getParameter(String name) {
		return getRequest().getParameter(name);
	}

	// 获取Parameter数组
	protected String[] getParameterValues(String name) {
		return getRequest().getParameterValues(name);
	}

	// 获取Session
	protected HttpSession getSession() {
		return getSession(true);
	}

	protected HttpSession getSession(boolean isNew) {
		return getRequest().getSession(isNew);
	}

	// 获取Session属性的值
	protected Object getSession(String name) {
		HttpSession session = getSession();
		return session.getAttribute(name);
	}

	// 设置Session属性的值
	protected void setSession(String name, Object value) {
		HttpSession session = getSession();
		session.setAttribute(name, value);
	}

	// 移除Session属性的值
	protected void removeSession(String name) {
		HttpSession session = getSession();
		session.removeAttribute(name);
	}

	// 获取Cookie
	protected String getCookie(String name) {
		Cookie cookies[] = getRequest().getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (name.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	// 设置Cookie
	protected void setCookie(String name, String value) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath(getRequest().getContextPath() + "/");
		System.out.println(getResponse());
		getResponse().addCookie(cookie);
	}

	// 设置Cookie
	protected void setCookie(String name, String value, Integer maxAge) {
		Cookie cookie = new Cookie(name, value);
		if (maxAge != null) {
			cookie.setMaxAge(maxAge);
		}
		cookie.setPath(getRequest().getContextPath() + "/");
		getResponse().addCookie(cookie);
	}

	// 移除Cookie
	protected void removeCookie(String name) {
		Cookie cookie = new Cookie(name, null);
		cookie.setMaxAge(0);
		cookie.setPath(getRequest().getContextPath() + "/");
		getResponse().addCookie(cookie);
	}

	// 获取真实路径
	protected String getRealPath(String path) {
		return getServletContext().getRealPath(path);
	}

	// 获取ContextPath
	protected String getContextPath() {
		return getRequest().getContextPath();
	}

	// AJAX输出
	protected String ajax(String content, String contentType) {
		try {
			HttpServletResponse response = initResponse(contentType);
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return NONE;
	}

	// 根据文本内容输出AJAX
	protected String ajax(String text) {
		return ajax(text, HEADER_TEXT_CONTENT_TYPE);
	}

	// 根据操作状态输出AJAX
	protected String ajax(Status status) {
		HttpServletResponse response = initResponse(HEADER_JSON_CONTENT_TYPE);
		try {
			Map<String, String> jsonMap = new HashMap<String, String>();
			jsonMap.put(STATUS_PARAMETER_NAME, status.toString());
			JsonUtil.writeValue(response.getWriter(), jsonMap);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
		return NONE;
	}

	// 根据操作状态、消息内容输出AJAX
	protected String ajax(Status status, String message) {
		HttpServletResponse response = initResponse(HEADER_JSON_CONTENT_TYPE);
		try {
			Map<String, String> jsonMap = new HashMap<String, String>();
			jsonMap.put(STATUS_PARAMETER_NAME, status.toString());
			jsonMap.put(MESSAGE_PARAMETER_NAME, message);
			JsonUtil.writeValue(response.getWriter(), jsonMap);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
		return NONE;
	}

	// 根据Object输出AJAX
	protected String ajax(Object object) {
		HttpServletResponse response = initResponse(HEADER_JSON_CONTENT_TYPE);
		try {
			JsonUtil.writeValue(response.getWriter(), object);
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
		return NONE;
	}

	// 根据boolean状态输出AJAX
	protected String ajax(boolean booleanStatus) {
		HttpServletResponse response = initResponse(HEADER_JSON_CONTENT_TYPE);
		try {
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put(STATUS_PARAMETER_NAME, booleanStatus);
			JsonUtil.writeValue(response.getWriter(), jsonMap);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
		return NONE;
	}

	// json输出到响应流
	protected void writeJson(String json) {
		HttpServletResponse response = initResponse(HEADER_JSON_CONTENT_TYPE);
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		writer.write(json);
		writer.flush();
	}

	private HttpServletResponse initResponse(String contentType) {
		HttpServletResponse response = getResponse();
		response.setContentType(contentType + ";charset=" + HEADER_ENCODING);
		if (HEADER_NO_CACHE) {
			response.setDateHeader("Expires", 1L);
			response.addHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
		}
		return response;
	}
	
	protected String registerView(){
		setAttribute("captchaName", CaptchaEngine.CAPTCHA_PARAMETER_NAME);
		setAttribute("captchaUrl", CaptchaEngine.CAPTCHA_IMAGE_URL);
		return "/register";
	}

}
