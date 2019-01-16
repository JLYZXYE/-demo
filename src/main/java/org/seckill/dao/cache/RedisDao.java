package org.seckill.dao.cache;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.seckill.enity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

public class RedisDao {
	private final Logger logger = LoggerFactory.getLogger(RedisDao.class);
	private final  JedisPool  jesdisPool;
	
	public RedisDao(String ip, int port,String password) {
		JedisPoolConfig config = new JedisPoolConfig();
		config =new JedisPoolConfig();
		       config.setMaxTotal(60000);//设置最大连接数  
		       config.setMaxIdle(1000); //设置最大空闲数 
		       config.setMaxWaitMillis(3000);//设置超时时间  
		       config.setTestOnBorrow(true);
	    	   jesdisPool =new JedisPool (config, ip, port, 0, password);
	}
	
	
	private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);
	public Seckill getSeckill(long seckillId) {
		try {
			Jedis jedis = jesdisPool.getResource();
			try {
				String key = "seckill:"+seckillId;
				//redis内部没有实现内部序列化
				//反序列化protostuff
				byte[] bytes = jedis.get(key.getBytes());
				if(bytes != null) {
					Seckill seckill = schema.newMessage();
					ProtobufIOUtil.mergeFrom(bytes, seckill, schema);
					return seckill;
				}
				
			} finally {
				jedis.close();
			}
		} catch (Exception e) {
			logger.debug("getReids:" + e.toString());
		}
		return null;
	}
	
	public String putSeckill(Seckill seckill) {
		try {
			Jedis jedis = jesdisPool.getResource();
			try {
				String key = "seckill:"+seckill.getSeckillId();
				//序列化
				byte[] bytes = ProtobufIOUtil.toByteArray(seckill, schema, LinkedBuffer.allocate
						(LinkedBuffer.DEFAULT_BUFFER_SIZE));
				//超时缓存
				int timeout = 60 * 60;//1小时
				String result = jedis.setex(key.getBytes(),timeout,bytes);
				return result;
			} finally {
				jedis.close();
			}
		} catch (Exception e) {
			logger.debug("putReids:" + e.toString());
		}
		return null;
	}
}
