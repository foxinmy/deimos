package com.foxinmy.deimos.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterConvertEvent;
import org.springframework.data.mongodb.core.mapping.event.AfterLoadEvent;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
import org.springframework.data.mongodb.core.mapping.event.MongoMappingEvent;

import com.foxinmy.deimos.event.AfterRemoveEvent;
import com.foxinmy.deimos.event.AfterUpdateEvent;
import com.foxinmy.deimos.event.BeforeRemoveEvent;
import com.foxinmy.deimos.event.BeforeUpdateEvent;
import com.mongodb.DBObject;

public class StandardMongoEventListener<E> implements ApplicationListener<MongoMappingEvent<?>> {

	private static final Logger LOG = LoggerFactory
			.getLogger(StandardMongoEventListener.class);
	private final Class<?> domainClass;

	public StandardMongoEventListener() {
		Class<?> typeArgument = GenericTypeResolver.resolveTypeArgument(
				this.getClass(), AbstractMongoEventListener.class);
		this.domainClass = typeArgument == null ? Object.class : typeArgument;
	}

	@Override
	public void onApplicationEvent(MongoMappingEvent<?> event) {
		/***************** 以下代码来自[AbstractMongoEventListener.java] *****************/

		if (event instanceof AfterLoadEvent) {
			AfterLoadEvent<?> afterLoadEvent = (AfterLoadEvent<?>) event;

			if (domainClass.isAssignableFrom(afterLoadEvent.getType())) {
				onAfterLoad(event.getDBObject());
			}

			return;
		}
		@SuppressWarnings("unchecked")
		E source = (E) event.getSource();

		// Check for matching domain type and invoke callbacks
		if (source != null && !domainClass.isAssignableFrom(source.getClass())) {
			return;
		}

		if (event instanceof BeforeConvertEvent) {
			onBeforeConvert(source);
		} else if (event instanceof BeforeSaveEvent) {
			onBeforeSave(source, event.getDBObject());
		} else if (event instanceof AfterSaveEvent) {
			onAfterSave(source, event.getDBObject());
		} else if (event instanceof AfterConvertEvent) {
			onAfterConvert(event.getDBObject(), source);
		}
		/***************** [AbstractMongoEventListener.java]结束 *****************/

		else if (event instanceof BeforeRemoveEvent) {
			onBeforeRemove(source, event.getDBObject());
		}
		else if (event instanceof AfterRemoveEvent) {
			onAfterRemove(source, event.getDBObject());
		}
		else if (event instanceof BeforeUpdateEvent) {
			onBeforeUpdate(source, event.getDBObject());
		}
		else if (event instanceof AfterUpdateEvent) {
			onAfterUpdate(source, event.getDBObject());
		}
	}

	public void onBeforeRemove(E source, DBObject dbo) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("onBeforeRemove(" + source + ", " + dbo + ")");
		}
	}

	public void onAfterRemove(E source, DBObject dbo) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("onAfterRemove(" + source + ", " + dbo + ")");
		}
	}

	public void onBeforeUpdate(E source, DBObject dbo) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("onBeforeUpdate(" + source + ", " + dbo + ")");
		}
	}

	public void onAfterUpdate(E source, DBObject dbo) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("onAfterUpdate(" + source + ", " + dbo + ")");
		}
	}
	public void onBeforeConvert(E source) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("onBeforeConvert(" + source + ")");
		}
	}

	public void onBeforeSave(E source, DBObject dbo) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("onBeforeSave(" + source + ", " + dbo + ")");
		}
	}

	public void onAfterSave(E source, DBObject dbo) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("onAfterSave(" + source + ", " + dbo + ")");
		}
	}

	public void onAfterLoad(DBObject dbo) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("onAfterLoad(" + dbo + ")");
		}
	}

	public void onAfterConvert(DBObject dbo, E source) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("onAfterConvert(" + dbo + "," + source + ")");
		}
	}
}
