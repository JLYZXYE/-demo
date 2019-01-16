package org.seckill.service;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.enity.Seckill;
import org.seckill.service.impl.SeckillServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml",
						"classpath:spring/spring-service.xml"})
public class SeckillServiceTest {
	private Logger Logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SeckillService seckillService;
	
	
	@Test
	public void testGetSeckillList() {
		
		List<Seckill> sList = seckillService.getSeckillList();
		Logger.info("list{}",sList);
	}

	@Test
	public void testGetById() {
		Logger.info("seckill:"+seckillService.getById(1000L));
	}

	@Test
	public void testExportSeckillUrl() {
		Logger.info("exposeUrl={}",seckillService.exportSeckillUrl(1000L));
		//exposeUrl=Exposer [exposed=true, md5=596abb0dae22806647ad5130abb166b6, seckillId=1000, now=0, start=0, end=0]
	}

	@Test
	public void testExecuteSeckill() {
		
		String md5 = "596abb0dae22806647ad5130abb166b6";
		Logger.info("result={}",seckillService.executeSeckill(1000L, 11110, md5));
		//result=SeckillExcution [seckillId=1000, state=1, stateInfo=秒杀成功, successSeckill=SuccessSeckill [seckillId=1000, userPhone=11110, state=0, startTime=null]]
	}

	@Test
	public void testExecuteSeckillLogic() {
		long id = 1000L;
		long phone = 110L;
		Exposer exposer = seckillService.exportSeckillUrl(id);
		if(exposer.isExposed()) {
			seckillService.executeSeckill(id, phone, exposer.getMd5());
		} else {
			//秒杀没开启
			Logger.warn("exposer={}",exposer);
			//exposer=Exposer [exposed=false, md5=null, seckillId=1000, now=1544577982607, start=1544457600000, end=1544544000000]
		}
	}
}
