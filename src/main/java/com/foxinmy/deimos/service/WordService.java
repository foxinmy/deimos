package com.foxinmy.deimos.service;

import java.util.List;

import com.foxinmy.deimos.model.Question;
import com.foxinmy.deimos.model.Word;

public interface WordService {
	public List<Question> buildQuestions(Word word, List<String> similarWords);
}
