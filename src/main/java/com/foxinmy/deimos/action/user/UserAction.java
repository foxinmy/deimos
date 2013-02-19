package com.foxinmy.deimos.action.user;

import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.foxinmy.deimos.model.User;
import com.foxinmy.deimos.service.UserService;
import com.foxinmy.jycore.util.StringUtil;

@Controller
@RequestMapping("/user")
public class UserAction extends BaseUserAction {

	@Autowired
	private UserService userService;

	private static final String LOGIN_MESSAGE_KEY = "loginMsg";

	@RequestMapping("/login")
	public ModelAndView login(User user, Model model) throws Exception {
		int loginFlag = userService.userLogin(user);
		if (loginFlag == UserService.USER_NOT_FOUND) {
			model.addAttribute(LOGIN_MESSAGE_KEY, "用户不存在,请注册!");
		} else if (loginFlag == UserService.PASSWORD_NOT_MATCH) {
			model.addAttribute(LOGIN_MESSAGE_KEY, "用户名或密码输入错误!");
		} else {
			// 登录成功操作
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
			if (!StringUtil.isBlank(user.getNickName()))
				username = user.getNickName();
			httpSession.setAttribute(User.UESR_ID_SESSION_NAME, user.getId());
			httpSession.setAttribute(User.USER_NAME_COOKIE_NAME,
					URLEncoder.encode(username, "UTF-8"));
			setCookie(User.USER_NAME_COOKIE_NAME,
					URLEncoder.encode(username, "UTF-8"));
			return new ModelAndView("redirect:/index.action");

		}
		return new ModelAndView("/login");
	}

	@RequestMapping("/logout")
	public ModelAndView logout() {
		removeSession(User.UESR_ID_SESSION_NAME);
		removeSession(User.USER_NAME_COOKIE_NAME);
		removeCookie(User.USER_NAME_COOKIE_NAME);
		return new ModelAndView("redirect:/index.action");
	}
}
