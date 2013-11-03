package com.foxinmy.deimos.mongo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.convert.EntityReader;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.mapping.model.BeanWrapper;
import org.springframework.data.mapping.model.MappingException;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.CollectionCallback;
import org.springframework.data.mongodb.core.DocumentCallbackHandler;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.convert.MongoWriter;
import org.springframework.data.mongodb.core.convert.QueryMapper;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.MongoPersistentProperty;
import org.springframework.data.mongodb.core.mapping.MongoSimpleTypes;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.Assert;

import com.foxinmy.deimos.event.AfterRemoveEvent;
import com.foxinmy.deimos.event.AfterUpdateEvent;
import com.foxinmy.deimos.event.BeforeRemoveEvent;
import com.foxinmy.deimos.event.BeforeUpdateEvent;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;
import com.mongodb.util.JSONParseException;

public class StandardMongoTemplate extends MongoTemplate {

	private static final String ID_FIELD = "_id";
	private static final String CLASS_FIELD = "_class";

	private final QueryMapper mapper;

	public StandardMongoTemplate(Mongo mongo, String databaseName) {
		this(new SimpleMongoDbFactory(mongo, databaseName), null);
	}

	public StandardMongoTemplate(MongoDbFactory mongoDbFactory) {
		this(mongoDbFactory, null);
	}

	public StandardMongoTemplate(MongoDbFactory mongoDbFactory,
			MongoConverter mongoConverter) {
		super(mongoDbFactory, mongoConverter);
		if (mongoConverter == null) {
			mongoConverter = getDefaultMongoConverter(mongoDbFactory);
		}
		this.mapper = new QueryMapper(mongoConverter);
	}

	private static final MongoConverter getDefaultMongoConverter(
			MongoDbFactory factory) {
		MappingMongoConverter converter = new MappingMongoConverter(factory,
				new MongoMappingContext());
		converter.afterPropertiesSet();
		return converter;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected <T> void doSave(String collectionName, T objectToSave,
			MongoWriter<T> writer) {
		assertUpdateableIdIfNotSet(objectToSave);

		DBObject dbDoc = new BasicDBObject();

		maybeEmitEvent(new BeforeConvertEvent<T>(objectToSave));

		if (!(objectToSave instanceof String)) {
			writer.write(objectToSave, dbDoc);
		} else {
			try {
				objectToSave = (T) JSON.parse((String) objectToSave);
			} catch (JSONParseException e) {
				throw new MappingException(
						"Could not parse given String to save into a JSON document!",
						e);
			}
		}

		maybeEmitEvent(new BeforeSaveEvent<T>(objectToSave, dbDoc));

		// added by jy.hu 2013-05-01 17:15:13
		if (dbDoc.toMap().size() > 0) {
			writer.write(objectToSave, dbDoc);
		}

		Object id = saveDBObject(collectionName, dbDoc, objectToSave.getClass());

		populateIdIfNecessary(objectToSave, id);
		maybeEmitEvent(new AfterSaveEvent<T>(objectToSave, dbDoc));
	}

	@Override
	protected <T> void doInsert(String collectionName, T objectToSave,
			MongoWriter<T> writer) {
		assertUpdateableIdIfNotSet(objectToSave);

		initializeVersionProperty(objectToSave);

		BasicDBObject dbDoc = new BasicDBObject();

		maybeEmitEvent(new BeforeConvertEvent<T>(objectToSave));
		writer.write(objectToSave, dbDoc);

		maybeEmitEvent(new BeforeSaveEvent<T>(objectToSave, dbDoc));

		// added by jy.hu 2013-05-01 17:15:13
		if (dbDoc.toMap().size() > 0) {
			writer.write(objectToSave, dbDoc);
		}

		Object id = insertDBObject(collectionName, dbDoc,
				objectToSave.getClass());

		populateIdIfNecessary(objectToSave, id);
		maybeEmitEvent(new AfterSaveEvent<T>(objectToSave, dbDoc));
	}

	@Override
	protected <T> void doInsertAll(Collection<? extends T> listToSave,
			MongoWriter<T> writer) {
		Map<String, List<T>> objs = new HashMap<String, List<T>>();

		for (T o : listToSave) {

			MongoPersistentEntity<?> entity = getPersistentEntity(o.getClass());

			if (entity == null) {
				throw new InvalidDataAccessApiUsageException(
						"No Persitent Entity information found for the class "
								+ o.getClass().getName());
			}
			String collection = entity.getCollection();

			List<T> objList = objs.get(collection);
			if (null == objList) {
				objList = new ArrayList<T>();
				objs.put(collection, objList);
			}
			objList.add(o);

		}

		for (Map.Entry<String, List<T>> entry : objs.entrySet()) {
			doInsertBatch(entry.getKey(), entry.getValue(), this.getConverter());
		}
	}

	@Override
	protected <T> void doInsertBatch(String collectionName,
			Collection<? extends T> batchToSave, MongoWriter<T> writer) {
		Assert.notNull(writer);

		List<DBObject> dbObjectList = new ArrayList<DBObject>();
		for (T o : batchToSave) {

			initializeVersionProperty(o);
			BasicDBObject dbDoc = new BasicDBObject();

			maybeEmitEvent(new BeforeConvertEvent<T>(o));
			writer.write(o, dbDoc);

			maybeEmitEvent(new BeforeSaveEvent<T>(o, dbDoc));

			// added by jy.hu 2013-05-01 17:15:13
			if (dbDoc.toMap().size() > 0) {
				writer.write(o, dbDoc);
			}

			dbObjectList.add(dbDoc);
		}
		List<ObjectId> ids = insertDBObjectList(collectionName, dbObjectList);
		int i = 0;
		for (T obj : batchToSave) {
			if (i < ids.size()) {
				populateIdIfNecessary(obj, ids.get(i));
				maybeEmitEvent(new AfterSaveEvent<T>(obj, dbObjectList.get(i)));
			}
			i++;
		}
	}

	@Override
	public void remove(Object object) {
		// added by jy.hu 2013-05-10 16:22:31
		DBObject dbo = new BasicDBObject();
		maybeEmitEvent(new BeforeConvertEvent<Object>(object));
		this.getConverter().write(object, dbo);
		maybeEmitEvent(new BeforeRemoveEvent<Object>(object, dbo));

		super.remove(object);

		maybeEmitEvent(new AfterRemoveEvent<Object>(object, dbo));
	}

	@Override
	public void remove(Object object, String collection) {
		// added by jy.hu 2013-05-10 16:22:31
		DBObject dbo = new BasicDBObject();
		maybeEmitEvent(new BeforeConvertEvent<Object>(object));
		this.getConverter().write(object, dbo);
		maybeEmitEvent(new BeforeRemoveEvent<Object>(object, dbo));

		super.remove(object, collection);

		maybeEmitEvent(new AfterRemoveEvent<Object>(object, dbo));
	}

	public WriteResult update(final Object object) {
		return update(object, new String[] {});
	}

	// added by jy.hu 2013-06-16 00:10:02
	public <O> WriteResult update(O obj, String... exclude) {
		MongoPersistentEntity<?> entity = getPersistentEntity(obj.getClass());

		if (entity == null) {
			throw new InvalidDataAccessApiUsageException(
					"No Persitent Entity information found for the class "
							+ obj.getClass().getName());
		}
		final DBObject dbo = new BasicDBObject();
		maybeEmitEvent(new BeforeConvertEvent<O>(obj));
		this.getConverter().write(obj, dbo);

		maybeEmitEvent(new BeforeUpdateEvent<O>(obj, dbo));
		// added by jy.hu 2013-06-17 09:01:51
		if (dbo.toMap().size() > 0) {
			this.getConverter().write(obj, dbo);
		}

		Object id = dbo.get(ID_FIELD);
		dbo.removeField(ID_FIELD);
		dbo.removeField(CLASS_FIELD);

		Update update = Update.fromDBObject(dbo, exclude);
		update = Update.fromDBObject(
				new BasicDBObject("$set", update.getUpdateObject()),
				new String[] {});

		final DBObject upObject = update.getUpdateObject();
		final DBObject idObject = new BasicDBObject(ID_FIELD, id);

		WriteResult result = execute(entity.getCollection(),
				new CollectionCallback<WriteResult>() {
					public WriteResult doInCollection(DBCollection collection)
							throws MongoException, DataAccessException {
						return collection.update(idObject, upObject, false,
								false);
					}
				});

		maybeEmitEvent(new AfterUpdateEvent<O>(obj, dbo));

		return result;
	}

	private MongoPersistentEntity<?> getPersistentEntity(Class<?> type) {
		return type == null ? null : this.getConverter().getMappingContext()
				.getPersistentEntity(type);
	}

	private void assertUpdateableIdIfNotSet(Object entity) {

		MongoPersistentEntity<?> persistentEntity = getPersistentEntity(entity
				.getClass());
		MongoPersistentProperty idProperty = persistentEntity == null ? null
				: persistentEntity.getIdProperty();

		if (idProperty == null) {
			return;
		}

		ConversionService service = this.getConverter().getConversionService();
		Object idValue = BeanWrapper.create(entity, service).getProperty(
				idProperty, Object.class, true);

		if (idValue == null
				&& !MongoSimpleTypes.AUTOGENERATED_ID_TYPES.contains(idProperty
						.getType())) {
			throw new InvalidDataAccessApiUsageException(
					String.format(
							"Cannot autogenerate id of type %s for entity of type %s!",
							idProperty.getType().getName(), entity.getClass()
									.getName()));
		}
	}

	/**
	 * 根据ID保存子文档
	 * 
	 * @param <T>
	 * @param id
	 * @param collectionName
	 * @param subDocumentName
	 * @param entity
	 */
	public <T> void saveSubEntityById(Object id, String collectionName,
			T entity, String subDocumentName) {
		BasicDBObject dbDoc = new BasicDBObject();

		maybeEmitEvent(new BeforeConvertEvent<T>(entity));
		getConverter().write(entity, dbDoc);
		dbDoc.remove(CLASS_FIELD);

		maybeEmitEvent(new BeforeSaveEvent<T>(entity, dbDoc));

		final DBObject q = new BasicDBObject(ID_FIELD, mapper.convertId(id));
		Update update = Update.update(subDocumentName, dbDoc);
		final DBObject u = update.getUpdateObject();
		execute(collectionName, new CollectionCallback<WriteResult>() {

			@Override
			public WriteResult doInCollection(DBCollection collection)
					throws MongoException, DataAccessException {
				return collection.update(q, u, false, false);
			}

		});
		populateIdIfNecessary(entity, id);
		maybeEmitEvent(new AfterSaveEvent<T>(entity, dbDoc));
	}

	class SubDocumentCallbackHandler implements DocumentCallbackHandler {
		private DBObject subObj;
		private String subName;
		private MongoConverter converter;

		public SubDocumentCallbackHandler(MongoConverter converter,
				String subName) {
			this.converter = converter;
			this.subName = subName;
		}

		@Override
		public void processDocument(DBObject dbObject) throws MongoException,
				DataAccessException {
			subObj = new BasicDBObject();
			converter.write(dbObject.get(subName), subObj);
			subObj.removeField(CLASS_FIELD);
		}

		public DBObject getSubObj() {
			return subObj;
		}
	}

	/**
	 * 查询子文档
	 * 
	 * @param id
	 * @param collectionName
	 * @param clazz
	 * @return
	 */
	public <T> T findSubEntityById(Object id, String collectionName,
			Class<T> subClazz, final String subDocumentName) {
		Query query = Query.query(Criteria.where(ID_FIELD).is(mapper.convertId(id)));
		query.fields().include(subDocumentName);
		EntityReader<? super T, DBObject> readerToUse = super.getConverter();
		SubDocumentCallbackHandler handler = new SubDocumentCallbackHandler(
				super.getConverter(), subDocumentName);
		executeQuery(query, collectionName, handler);
		return readerToUse.read(subClazz, handler.getSubObj());
	}

	private void initializeVersionProperty(Object entity) {

		MongoPersistentEntity<?> mongoPersistentEntity = getPersistentEntity(entity
				.getClass());

		if (mongoPersistentEntity != null
				&& mongoPersistentEntity.hasVersionProperty()) {
			BeanWrapper<PersistentEntity<Object, ?>, Object> wrapper = BeanWrapper
					.create(entity, this.getConverter().getConversionService());
			wrapper.setProperty(mongoPersistentEntity.getVersionProperty(), 0);
		}
	}
}
