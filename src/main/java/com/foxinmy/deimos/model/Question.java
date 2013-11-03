package com.foxinmy.deimos.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 问题
 * 
 * @author liubin
 * 
 */
@Document
public class Question extends BaseModel<String> {

	private static final long serialVersionUID = -7037914885139106929L;

	// 类型
	private Integer type;

	// 选项
	private List<String> options;

	// 正确的选项的序号,从1开始
	private Integer rightIndex;

	// 序数,从0开始
	private Integer ordinal;

	// 教材
	@DBRef
	private Word word;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> options) {
		this.options = options;
	}

	public Integer getRightIndex() {
		return rightIndex;
	}

	public void setRightIndex(Integer rightIndex) {
		this.rightIndex = rightIndex;
	}

	public Word getWord() {
		return word;
	}

	public void setWord(Word word) {
		this.word = word;
	}

	public Integer getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(Integer ordinal) {
		this.ordinal = ordinal;
	}

}
