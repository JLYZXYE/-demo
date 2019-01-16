package org.seckill.dao;

import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dao.SeckillDao;
import org.seckill.enity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/*
 * 
 * 配置spring junit 整合，junit启动加载SpringIo	c容器
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring 配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})

public class SeckillDaoTest {

	//注入依赖
	@Resource
	private SeckillDao seckillDao;
	
	
	@Test
	public void testReduceNumber() {
//		System.out.println("count" = count);
		Date now = new Date();
		int count = seckillDao.reduceNumber(1000L, now);
		System.out.println("count:"+count);
	}

	@Test
	public void testQueryById() {
		System.out.println("seckillDao:" +seckillDao);
		Seckill seckill = seckillDao.queryById(1000);
		System.out.println(seckill.getName());
	}

	@Test
	public void testQueryAll() {
		List<Seckill> seckills = seckillDao.queryAll(0, 2);
		for (Seckill seckill : seckills) {
			System.out.println(seckill.getName());
		}
	}

}
