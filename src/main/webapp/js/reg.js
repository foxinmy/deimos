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
		var that = $(this);
		that.attr("src", that.attr("path")+"?timestamp" + (new Date()).valueOf());
	});
});