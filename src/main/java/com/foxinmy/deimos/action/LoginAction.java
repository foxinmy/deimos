package com.foxinmy.deimos.action;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.foxinmy.deimos.bean.WebConstantBean;
import com.foxinmy.deimos.captcha.CaptchaEngine;

@Controller
public class LoginAction extends BaseAction {

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(Model model) {
		String reurl = getParameter(WebConstantBean.RETURN_URL_KEY);
		if(StringUtils.isBlank(reurl))
			model.addAttribute(WebConstantBean.RETURN_URL_KEY, reurl);
		return "/login";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String registerPage() {
		return registerView();
	}

	@RequestMapping(value = "/reg", method = RequestMethod.GET)
	public String regPage() {
		setAttribute("captchaName", CaptchaEngine.CAPTCHA_PARAMETER_NAME);
		setAttribute("captchaUrl", CaptchaEngine.CAPTCHA_IMAGE_URL);
		return "/reg";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String homePage() {
		return "/index";
	}

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String _indexPage() {
		return indexPage();
	}
	
	@RequestMapping(value = "/user/index", method = RequestMethod.GET)
	public String indexPage() {
		return "/user/index";
	}
}
