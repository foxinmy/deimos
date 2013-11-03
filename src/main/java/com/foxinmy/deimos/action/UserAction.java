package com.foxinmy.deimos.action;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.foxinmy.deimos.bean.UserConstantBean;
import com.foxinmy.deimos.bean.WebConstantBean;
import com.foxinmy.deimos.captcha.CaptchaUtil;
import com.foxinmy.deimos.model.User;
import com.foxinmy.deimos.service.MailService;
import com.foxinmy.deimos.service.UserService;
import com.foxinmy.jycore.constant.ConstantBean;

@Controller
@RequestMapping("/user")
public class UserAction extends BaseAction {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserService userService;

	@Autowired
	private MailService mailService;

	/**
	 * 用户登录
	 * 
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView login(User user,@PathVariable("reurl") String url) {
		ConstantBean<Integer> loginFlag = userService.userLogin(user);
		ModelAndView mv = new ModelAndView("/login");
	
		String reurl = getParameter(WebConstantBean.RETURN_URL_KEY);
		if (StringUtils.isNotBlank(reurl))
			mv.addObject(WebConstantBean.RETURN_URL_KEY, reurl);
		if (loginFlag == UserConstantBean.UserLogin.USER_NOT_FOUND) {
			mv.addObject(WebConstantBean.ERROR_MESSAGE_KEY,
					UserConstantBean.UserLogin.USER_NOT_FOUND.getName());
		} else if (loginFlag == UserConstantBean.UserLogin.PASSWORD_NOT_MATCH) {
			mv.addObject(WebConstantBean.ERROR_MESSAGE_KEY,
					 UserConstantBean.UserLogin.PASSWORD_NOT_MATCH.getName());
		} else {
			login_success(user);
			if (StringUtils.isNotBlank(reurl)) {
				try {
					getResponse().sendRedirect(reurl);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				return new ModelAndView("redirect:index");
			}
		}
		return mv;
	}

	/**
	 * 用户注销
	 * 
	 * @return
	 */
	@RequestMapping("/logout")
	public ModelAndView logout() {
		removeSession(User.UESR_ID_SESSION_NAME);
		removeSession(User.USER_NAME_COOKIE_NAME);
		removeCookie(User.USER_NAME_COOKIE_NAME);
		return new ModelAndView("redirect:/index");
	}

	/**
	 * 用户注册
	 * 
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping("/register")
	public String register(User user, Model model) {
		// 验证码是否正确
		if (!CaptchaUtil.validateCaptchaByRequest(getRequest())) {
			model.addAttribute(WebConstantBean.ERROR_MESSAGE_KEY,
					"请输入正确的验证码");
			return registerView();
		}
		// 用户是否存在
		if (userService.userIsExists(user.getEmail())) {
			model.addAttribute(WebConstantBean.ERROR_MESSAGE_KEY,
					"该邮箱已经注册过了");
			return registerView();
		}

		// 持久化操作
		userService.save(user);
		// 发送验证邮件
		mailService.sendUserVerifyEmail(user);

		return "redirect:email_verify_link";

	}

	/**
	 * 邮箱验证
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/verify/{id}", method = RequestMethod.GET)
	public ModelAndView verify(@PathVariable String id, Model model) {
		User user = userService.findById(id);
		if (user == null) {
			// model.addAttribute(ERROR_MESSAGE_KEY, "用户不存在");
			return new ModelAndView("redirect:/login");
		}
		int user_status_usebled = UserConstantBean.UserStatus.STATUS_USABLED
				.getValue().intValue();
		if (user.getStatus() == user_status_usebled) {
			// model.addAttribute(ERROR_MESSAGE_KEY, "用户邮箱已经验证");
			return new ModelAndView("redirect:/login");
		}
		user.setStatus(user_status_usebled);
		userService.update(user);
		// model.addAttribute(ERROR_MESSAGE_KEY, "恭喜你,验证成功!");
		login_success(user);
		return new ModelAndView("/user/email_verify_success");
	}

	@RequestMapping("/weibo")
	public ModelAndView weibo(Model model) {
		return new ModelAndView("/user/weibo");
	}

	// 登录成功操作
	private void login_success(User user) {
		try {
			// 防止Session Fixation攻击
			HttpSession httpSession = getSession();
			Enumeration<?> enumeration = httpSession.getAttributeNames();
			Map<String, Object> sessionMap = new HashMap<String, Object>();
			while (enumeration.hasMoreElements()) {
				String key = (String) enumeration.nextElement();
				sessionMap.put(key, httpSession.getAttribute(key));
			}
			httpSession.invalidate();
			httpSession = getSession(true);
			for (String key : sessionMap.keySet()) {
				Object value = sessionMap.get(key);
				httpSession.setAttribute(key, value);
			}
			String username = user.getEmail();
			if (StringUtils.isNotBlank(user.getNickName()))
				username = user.getNickName();
			httpSession.setAttribute(User.UESR_ID_SESSION_NAME, user.getId());
			httpSession.setAttribute(User.USER_NAME_COOKIE_NAME,
					URLEncoder.encode(username, "UTF-8"));
			setCookie(User.USER_NAME_COOKIE_NAME,
					URLEncoder.encode(username, "UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
}
