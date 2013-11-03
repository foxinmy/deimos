package com.foxinmy.deimos.common;

public class SystemSetting {
	// 小数位精确方式(四舍五入、向上取整、向下取整)
	public enum RoundType {
		roundHalfUp, roundUp, roundDown
	}

	// 运算符(加、减、乘、除)
	public enum Operator {
		add, subtract, multiply, divide
	}
	
	private String domain;//域名
	private String systemName;// 系统名称
	private String systemVersion;// 系统版本
	private String systemDescription;// 系统描述
	private String contextPath;// 虚拟路径
	private String imageUploadPath;// 图片上传路径
	private String imageBrowsePath;// 图片浏览路径
	private String adminLoginUrl;// 后台登录URL
	private String adminLoginProcessingUrl;// 后台登录处理URL
	private Boolean isShowPoweredInfo;// 是否显示Powered信息
	private String hotSearch;// 热门搜索关键词
	private String metaKeywords;// 首页页面关键词
	private String metaDescription;// 首页页面描述
	private String address;// 联系地址
	private String phone;// 联系电话
	private String zipCode;// 邮编
	private String email;// 联系email
	private String certtext;// 备案号
	private String smtpFromMail;// 发件人邮箱
	private String smtpHost;// SMTP服务器地址
	private Integer smtpPort;// SMTP服务器端口
	private String smtpUsername;// SMTP用户名
	private String smtpPassword;// SMTP密码
	private Boolean isGzipEnabled;// 是否开启GZIP功能
	private Integer buildHtmlDelayTime;// 生成HTML任务延时(单位: 秒)

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getSystemVersion() {
		return systemVersion;
	}

	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
	}

	public String getSystemDescription() {
		return systemDescription;
	}

	public void setSystemDescription(String systemDescription) {
		this.systemDescription = systemDescription;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public String getImageUploadPath() {
		return imageUploadPath;
	}

	public void setImageUploadPath(String imageUploadPath) {
		this.imageUploadPath = imageUploadPath;
	}

	public String getImageBrowsePath() {
		return imageBrowsePath;
	}

	public void setImageBrowsePath(String imageBrowsePath) {
		this.imageBrowsePath = imageBrowsePath;
	}

	public String getAdminLoginUrl() {
		return adminLoginUrl;
	}

	public void setAdminLoginUrl(String adminLoginUrl) {
		this.adminLoginUrl = adminLoginUrl;
	}

	public String getAdminLoginProcessingUrl() {
		return adminLoginProcessingUrl;
	}

	public void setAdminLoginProcessingUrl(String adminLoginProcessingUrl) {
		this.adminLoginProcessingUrl = adminLoginProcessingUrl;
	}

	public Boolean getIsShowPoweredInfo() {
		return isShowPoweredInfo;
	}

	public void setIsShowPoweredInfo(Boolean isShowPoweredInfo) {
		this.isShowPoweredInfo = isShowPoweredInfo;
	}

	public String getHotSearch() {
		return hotSearch;
	}

	public void setHotSearch(String hotSearch) {
		this.hotSearch = hotSearch;
	}

	public String getMetaKeywords() {
		return metaKeywords;
	}

	public void setMetaKeywords(String metaKeywords) {
		this.metaKeywords = metaKeywords;
	}

	public String getMetaDescription() {
		return metaDescription;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCerttext() {
		return certtext;
	}

	public void setCerttext(String certtext) {
		this.certtext = certtext;
	}

	public String getSmtpFromMail() {
		return smtpFromMail;
	}

	public void setSmtpFromMail(String smtpFromMail) {
		this.smtpFromMail = smtpFromMail;
	}

	public String getSmtpHost() {
		return smtpHost;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public Integer getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(Integer smtpPort) {
		this.smtpPort = smtpPort;
	}

	public String getSmtpUsername() {
		return smtpUsername;
	}

	public void setSmtpUsername(String smtpUsername) {
		this.smtpUsername = smtpUsername;
	}

	public String getSmtpPassword() {
		return smtpPassword;
	}

	public void setSmtpPassword(String smtpPassword) {
		this.smtpPassword = smtpPassword;
	}

	public Boolean getIsGzipEnabled() {
		return isGzipEnabled;
	}

	public void setIsGzipEnabled(Boolean isGzipEnabled) {
		this.isGzipEnabled = isGzipEnabled;
	}

	public Integer getBuildHtmlDelayTime() {
		return buildHtmlDelayTime;
	}

	public void setBuildHtmlDelayTime(Integer buildHtmlDelayTime) {
		this.buildHtmlDelayTime = buildHtmlDelayTime;
	}

}
