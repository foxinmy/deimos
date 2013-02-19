package com.foxinmy.deimos.captcha;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.foxinmy.deimos.util.SpringUtil;
import com.octo.captcha.service.CaptchaService;

public class CaptchaUtil {

	/**
	 * 根据HttpServletRequest校验验证码
	 * 
	 * @param parameterName
	 *            参数名称
	 * 
	 * @return 是否验证通过
	 */
	public static boolean validateCaptchaByRequest(HttpServletRequest request,
			String parameterName) {
		String captchaID = request.getSession().getId();
		String challengeResponse = StringUtils.upperCase(request
				.getParameter(parameterName));

		if (StringUtils.isEmpty(captchaID)
				|| StringUtils.isEmpty(challengeResponse)) {
			return false;
		}

		CaptchaService captchaService = (CaptchaService) SpringUtil
				.getBean(CaptchaEngine.CAPTCHA_SERVICE_BEAN_NAME);
		try {
			return captchaService.validateResponseForID(captchaID,
					challengeResponse);
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 根据HttpServletRequest校验验证码,使用默认参数名称
	 * 
	 * @return 是否验证通过
	 */
	public static boolean validateCaptchaByRequest(HttpServletRequest request) {
		return validateCaptchaByRequest(request,
				CaptchaEngine.CAPTCHA_PARAMETER_NAME);
	}
}
