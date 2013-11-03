package com.foxinmy.deimos.listener;

import java.io.Serializable;

import com.foxinmy.deimos.model.BaseModel;
import com.mongodb.DBObject;

public class StandardMongoListener<E extends BaseModel<Serializable>> extends
		StandardMongoEventListener<E> {

	@Override
	public void onBeforeSave(E source, DBObject dbo) {
		System.out.println("****************保存之前****************");
		System.out.println(source);
		System.out.println(dbo);
		((BaseModel<Serializable>)source).onSave();
		//ReflectionUtil.invokeMethod(source, BaseModel.ON_SAVE_METHOD_NAME);
	}
 
	@Override
	public void onAfterSave(E source, DBObject dbo) {
		System.out.println("****************保存之后****************");
		System.out.println(source);
		System.out.println(dbo);
	}

	@Override
	public void onAfterLoad(DBObject dbo) {
		System.out.println("****************加载之后****************");
		System.out.println(dbo);
	}

	@Override
	public void onBeforeRemove(E source, DBObject dbo) {
		System.out.println("****************删除之前****************");
		System.out.println(source);
		System.out.println(dbo);
		((BaseModel<Serializable>)source).onRemove();
		//ReflectionUtil.invokeMethod(source, BaseModel.ON_REMOVE_METHOD_NAME);
	}

	@Override
	public void onAfterRemove(E source, DBObject dbo) {
		System.out.println("****************删除之后****************");
		System.out.println(source);
		System.out.println(dbo);
	}

	@Override
	public void onBeforeUpdate(E source, DBObject dbo) {
		System.out.println("****************更新之前****************");
		System.out.println(source);
		System.out.println(dbo);
		((BaseModel<Serializable>)source).onUpdate();
		//ReflectionUtil.invokeMethod(source, BaseModel.ON_UPDATE_METHOD_NAME);
	}

	@Override
	public void onAfterUpdate(E source, DBObject dbo) {
		System.out.println("****************更新之后****************");
		System.out.println(source);
		System.out.println(dbo);
	}

}
