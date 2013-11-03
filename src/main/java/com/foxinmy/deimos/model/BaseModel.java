package com.foxinmy.deimos.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Transient;

import org.springframework.data.annotation.Id;

public class BaseModel<ID extends Serializable> implements Serializable {

	private static final long serialVersionUID = -6718838800112233445L;

	@Transient
	public static final String CREATE_DATE_PROPERTY_NAME = "createDate";// "创建日期"属性名称
	@Transient
	public static final String MODIFY_DATE_PROPERTY_NAME = "modifyDate";// "修改日期"属性名称
	@Transient
	public static final String ON_SAVE_METHOD_NAME = "onSave"; // "保存"方法名称
	@Transient
	public static final String ON_UPDATE_METHOD_NAME = "onUpdate"; // "更新"方法名称
	public static final String ON_REMOVE_METHOD_NAME = "onRemove"; // "删除"方法名称

	@Id
	protected ID id;// ID
	protected Date createDate;// 创建日期
	protected Date modifyDate;// 修改日期

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	@Transient
	public void onSave() {
	}

	@Transient
	public void onUpdate() {
	}

	@Transient
	public void onRemove(){
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null) {
			return false;
		}
		if (object instanceof BaseModel) {
			BaseModel<?> baseModel = (BaseModel<?>) object;
			if (this.getId() == null || baseModel.getId() == null) {
				return false;
			} else {
				return (this.getId().equals(baseModel.getId()));
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return id == null ? System.identityHashCode(this) : (this.getClass()
				.getName() + this.getId()).hashCode();
	}
}
