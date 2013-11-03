package com.foxinmy.deimos.event;

import org.springframework.data.mongodb.core.mapping.event.MongoMappingEvent;

import com.mongodb.DBObject;

public class BeforeRemoveEvent<E> extends MongoMappingEvent<E> {

	private static final long serialVersionUID = 1L;
	
	public BeforeRemoveEvent(E source, DBObject dbo) {
		super(source, dbo);
	}
}
