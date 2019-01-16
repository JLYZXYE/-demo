package org.seckill.dao.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dao.SeckillDao;
import org.seckill.enity.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisDaoTest {
	
	@Autowired private RedisDao redisDao;
	
	@Autowired private SeckillDao seckillDao;
	
	private long seckillId = 1000;

	@Test
	public void testRedisSeckill() {
		
		Seckill seckill = redisDao.getSeckill(seckillId);
		if(seckill == null) {
			seckill = seckillDao.queryById(seckillId);
			if(seckill != null) {
				String result = redisDao.putSeckill(seckill);
				System.out.println(result);
				seckill = redisDao.getSeckill(seckillId);
				System.out.println(seckill);
			}
		}
		
	}
//	@Test
//	public void testGetSeckill() {
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	public void testGetSeckill() {
//		fail("Not yet implemented");
//	}

//	@Test
//	public void testPutSeckill() {
//		fail("Not yet implemented");
//	}

}
