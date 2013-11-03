<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<script type="text/javascript" src="${base}/js/reg.js"></script>
</head>
<body>
	<div class="wrapper" style="width:600px">
		<div id="reg-wrapper">
			<form id="registerForm" action="${base}/user/register" name="register" method="post">
				<h1>注册</h1>
				<fieldset class="error_msg" id="r_error_msg">${errorMsg!}</fieldset>
				<fieldset id="r_inputs">
					邮箱：<input id="r_email" name="email" type="email" maxlength="50" required>
					昵称：<input id="r_name" name="nickName" type="text" maxlength="30" required>
					密码：<input id="r_password" name="password" type="password" maxlength="16" required>
					验证码：<input id="r_verification_code" name="j_captcha" type="text" maxlength="4" required style="width:250px">
						<img src="${base}/images/jcaptcha" path="${base}/images/jcaptcha" alt="换一张" id="captchaImage" class="captchaImage"/>
				</fieldset>
				<fieldset id="r_actions">
					<input type="submit" id="r_submit" value="注册">
				</fieldset>
			</form>
		</div>
	</div>
</body>
</html>
