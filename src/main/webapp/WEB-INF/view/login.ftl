<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>deimos - 登录</title>
	<link rel="stylesheet" href="${base}/css/base.css" type="text/css" media="screen"/>
	<link rel="icon" href="${base}/images/favicon.ico" type="image/x-icon" />
	<script type="text/javascript" src="${base}/js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${base}/js/base.js"></script>
	<link rel="stylesheet" href="${base}/js/fancybox/jquery.fancybox-1.3.4.css" type="text/css" media="screen"/>
	<script type="text/javascript" src="${base}/js/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
	<link rel="stylesheet" href="${base}/css/login.css" type="text/css" media="screen" />
	<script type="text/javascript" src="${base}/js/login.js"></script>
</head>
<body>
	<div class="wrapper">
		<div id="header">
			deimos
		</div>
		<div id="login-wrapper">
			<form method="post" action="${base}/user/login" name="login" id="loginForm">
				<input name="reurl" type="hidden" value="${reurl!}">
				<h1>登录</h1>
				<fieldset class="error_msg" id="r_error_msg">${errorMsg!}</fieldset>
				<span id="loginMsg"></span>
				<fieldset id="inputs">
					<input id="email" name="email" type="email" placeholder="邮箱" maxlength="50" autofocus required> 
					<input id="password" name="password" type="password" maxlength="16" placeholder="密码" required>
				</fieldset>
				<fieldset id="checks" class="hidden">
					<span>验证码</span>
					<input id="checkcode" type="text" maxlength="4"/>
					<img src="${base}/images/blank_login.gif">
				</fieldset>
				<input type="checkbox" id="remeberme" />记住我
				<fieldset id="actions">
					<input type="submit" id="submit" value="登录"> <a href="">忘记密码?</a><a href="${base}/reg" class="fancybox">注册</a>
				</fieldset>
			</form>
		</div>
		<#include "inc/footer.dec">
	</div>
</body>
</html>
