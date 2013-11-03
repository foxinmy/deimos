package com.foxinmy.deimos.type;

/**
 * @author liubin
 *
 */
public enum QuestionType {

	/**
	 *　系统显示出单词并发音,用户选择单词所表达的含义.
	 */
	TYPE_1(0),
	
	/**
	 *　系统显示出中文含义,用户选择正确的单词.
	 */
	TYPE_2(1),
	
	/**
	 *　系统发音,用户选择单词所表达的含义
	 */
	TYPE_3(2),
	
	/**
	 *　系统发音,用户拼写单词.
	 */
	TYPE_4(3);
	
	public Integer value;
	
	QuestionType(Integer value) {
		this.value = value;
	}
	
	private static String[] VALUES = {"TYPE_1", "TYPE_2", "TYPE_3", "TYPE_4"};
	
	/**
	 * 根据值取枚举
	 * @param value
	 * @return
	 */
	public static QuestionType valueOf(Integer value) {
		if(value == null) {
			return null;
		}
		String name = VALUES[value];
		return valueOf(name);
	}
	
}
