package com.foxinmy.deimos.model;

import java.util.Date;

import javax.persistence.Transient;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.foxinmy.deimos.bean.UserConstantBean;

/**
 * 会员
 * 
 * @author liubin
 * 
 */
@Document
public class User extends BaseModel<String> {

	@Transient
	private static final long serialVersionUID = 5924548825162402298L;
	@Transient
	public static final String UESR_ID_SESSION_NAME = "loginId";
	@Transient
	public static final String USER_NAME_COOKIE_NAME = "loginName";

	@Indexed(unique = true)
	// 邮箱
	private String email;

	// 密码
	private String password;

	// 性别
	private char gender;

	// 昵称
	private String nickName;

	// 真实姓名
	private String realName;

	// 城市
	private String city;

	// 生日
	private Date birthday;

	// 注册日期
	private Date registerDate;

	// 个人签名
	private String signature;

	// 账户状态
	private int status;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public void onSave() {
		Date date = new Date();
		setGender(UserConstantBean.UserGender.GENDER_FEMALE.getValue()
				.charValue());
		setStatus(UserConstantBean.UserStatus.STATUS_DISABLED.getValue()
				.intValue());
		setCreateDate(date);
		setModifyDate(date);
		setPassword(DigestUtils.md5Hex(getPassword()));
	}

	@Override
	public void onUpdate() {
		setModifyDate(new Date());
	}

	@Override
	public void onRemove() {
		
	}
}
