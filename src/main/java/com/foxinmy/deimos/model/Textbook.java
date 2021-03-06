package com.foxinmy.deimos.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 教材
 * 
 * @author liubin
 */
@Document
public class Textbook extends BaseModel<String> {

	private static final long serialVersionUID = 723361294150876509L;

	// 教材名称
	private String name;

	// 教材类别
	@DBRef
	private Category category;

	// 单词总数量
	private long wordCount;

	// 创建日期
	private Date createDate;

	// 备注
	private String remark;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public long getWordCount() {
		return wordCount;
	}

	public void setWordCount(long wordCount) {
		this.wordCount = wordCount;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}