package com.foxinmy.deimos.common;

public class CommonUtil {
	public static final String WEB_APP_ROOT_KEY = "user.dir";// WebRoot路径KEY

	/**
	 * 获取WebRoot路径
	 * 
	 * @return WebRoot路径
	 */
	public static String getWebRootPath() {
		return System.getProperty(WEB_APP_ROOT_KEY);
	}
}
