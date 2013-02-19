package com.foxinmy.deimos.action.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class LoginAction {

	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String loginPage() {
		return "/login";
	}
	
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public String registerPage() {
		return "/register";
	}
	
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String indexPage(){
		return "/index";
	}
}
