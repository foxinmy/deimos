package com.foxinmy.deimos.service.impl;

import java.io.Serializable;

import org.apache.commons.lang.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

import com.foxinmy.deimos.service.RedisService;

@Service
public class RedisServiceImpl implements RedisService {

	@Autowired
	private RedisTemplate<Serializable, Serializable> redisTemplate;
	
	@Autowired
	private Jedis jedis;

	public void set(final Serializable key, final Serializable value) {
		redisTemplate.execute(new RedisCallback<Object>() {
			public Object doInRedis(RedisConnection con)
					throws DataAccessException {
				byte[] key_ = SerializationUtils.serialize(key);
				byte[] value_ = SerializationUtils.serialize(value);
				con.set(key_, value_);
				return true;
			}
		});
	}

	public void set(final Serializable key, final Serializable value,
			final long liveTime) {
		redisTemplate.execute(new RedisCallback<Object>() {
			public Object doInRedis(RedisConnection con)
					throws DataAccessException {
				byte[] key_ = SerializationUtils.serialize(key);
				byte[] value_ = SerializationUtils.serialize(value);
				con.set(key_, value_);
				con.expire(key_, liveTime);
				return true;
			}
		});
	}

	public void del(final Serializable... keys) {
		redisTemplate.execute(new RedisCallback<Object>() {
			public Object doInRedis(RedisConnection con)
					throws DataAccessException {
				for (Serializable key : keys) {
					con.del(SerializationUtils.serialize(key));
				}
				return true;
			}
		});
	}

	public boolean is(final Serializable key) {
		Boolean sign = redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection con)
					throws DataAccessException {
				byte[] _key = SerializationUtils.serialize(key);
				return con.exists(_key);
			}
		});
		return sign.booleanValue();
	}

	public void flush() {
		redisTemplate.execute(new RedisCallback<Object>() {
			public Object doInRedis(RedisConnection con)
					throws DataAccessException {
				con.flushDb();
				return true;
			}
		});
	}

	public Serializable get(final Serializable key) {
		return redisTemplate.execute(new RedisCallback<Serializable>() {
			public Serializable doInRedis(RedisConnection con)
					throws DataAccessException {

				byte[] _key = SerializationUtils.serialize(key);
				byte[] bytes = con.get(_key);
				if (bytes != null && bytes.length > 0)
					return (Serializable) SerializationUtils.deserialize(bytes);
				return null;
			}
		});
	}

	@Override
	public RedisTemplate<Serializable, Serializable> getRedisTemplate() {
		return redisTemplate;
	}
	@Override
	public Jedis getJedis(){
		return jedis;
	}
}
