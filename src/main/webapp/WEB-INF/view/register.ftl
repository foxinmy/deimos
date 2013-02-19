<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>deimos - 注册</title>
	<link rel="stylesheet" href="${base}/css/base.css" type="text/css" media="screen"/>
	<link rel="icon" href="${base}/images/favicon.ico" type="image/x-icon" />
	<script type="text/javascript" src="${base}/js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${base}/js/base.js"></script>
	<link rel="stylesheet" href="${base}/css/login.css" type="text/css" media="screen" />
</head>
<body>
	<div class="wrapper">
		<div id="header">
			deimos
		</div>
		<div id="reg-wrapper">
			<form id="registerForm" name="register" method="post">
				<h1>注册</h1>
				<fieldset class="error_msg" id="r_error_msg">${errorMsg!}</fieldset>
				<fieldset id="r_inputs">
					<span style="margin-left:20px">邮 箱：<input id="r_email" type="email" required><br/></span>
					<span style="margin-left:20px">密 码：<input id="r_password" type="password" required><br/></span>
					<span>重复密码：<input id="r_password_confirm" type="password" required><br/></span>
					<span style="margin-left:12px">验证码：<input id="r_verification_code" name="j_captcha" type="text" required style="width:250px">
						<img src="${base}/jcaptcha" alt="换一张" id="captchaImage" class="captchaImage"/>
					</span>
				</fieldset>
				<fieldset id="r_actions">
					<input type="submit" id="r_submit" value="注册">
				</fieldset>
			</form>
		</div>
		<#include "inc/footer.dec">
	</div>
	<script type="text/javascript">
	var emailReg = /^[0-9a-zA-Z_\-\.]+@[0-9a-zA-Z_\-]+(\.[0-9a-zA-Z_\-]+)*$/;
	$(function(){
			var $captchaImage = $("#captchaImage");
			$("#register").submit(function() {
				var email = $("#r_email").val();
				if(email && email.length > 32) {
					$("#r_error_msg").html("邮箱长度不能超过32位").show();
					return false;
				}
				
				var password = $("#r_password").val();
				if(password && (password.length < 6 || password.length > 18)) {
					$("#r_error_msg").html("密码长度应该大于等于6位小于等于18位").show();
					return false;
				}
				var passwordConfirm = $("#r_password_confirm").val();
				if(password && passwordConfirm && passwordConfirm != password) {
					$("#r_error_msg").html("两次输入的密码必须相同").show();
					return false;
				}
			});
			// 刷新验证码图片
			$captchaImage.click( function() {
				$captchaImage.attr("src", "${base}/jcaptcha?timestamp" + (new Date()).valueOf());
			});
	});
</script>
</body>
</html>
