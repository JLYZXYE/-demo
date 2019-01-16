package org.seckill.dao;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.enity.SuccessSeckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class SuccessKilledDaoTest {

	
	@Resource
	private SuccessKilledDao successKilledDao;
	
	@Test
	public void testInsertSuccessKilled() {
		int count =  successKilledDao.insertSuccessKilled(1000L,110L);
		
		System.out.println("count"+count);
		
	}

	@Test
	public void testQueryByIdWithSeckill() {
		SuccessSeckill successSeckill = successKilledDao.queryByIdWithSeckill(1000L, 110L);
		System.out.println(successSeckill.getSeckillId());
	}

}
