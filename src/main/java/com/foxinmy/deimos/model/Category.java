package com.foxinmy.deimos.model;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 教材类别
 * 
 * @author liubin
 */
@Document
public class Category extends BaseModel<String> {

	private static final long serialVersionUID = -6570374022734207990L;

	// 类别名称
	private String name;

	// 备注
	private String remark;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}