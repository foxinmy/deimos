package com.foxinmy.deimos.service;

import java.io.Serializable;

import org.springframework.data.redis.core.RedisTemplate;

import redis.clients.jedis.Jedis;

public interface RedisService {
	
	public RedisTemplate<Serializable, Serializable> getRedisTemplate();
	public Jedis getJedis();
	public void set(final Serializable key, final Serializable value);
	public void set(final Serializable key, final Serializable value,final long liveTime);
	public void del(final Serializable... keys);
	public boolean is(final Serializable key);
	public void flush();
	public Serializable get(final Serializable key);
}
