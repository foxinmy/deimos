<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${setting.systemName}</title>
</head>
<body>
	<p>
		hi ${user.nickName},
	</p>
	<p>
		感谢您注册${setting.systemName} , 请点击下面的链接验证您的Email：
	</p>
	<p>
		<a href="${setting.domain}user/verify/${user.id}">${setting.domain}user/verify/${user.id}</a>
	</p>
	-- 
	来自${setting.systemName}
</body>
</html>