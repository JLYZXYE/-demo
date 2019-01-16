package org.seckill.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.tomcat.util.security.MD5Encoder;
import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExcution;
import org.seckill.dto.SeckillResult;
import org.seckill.enity.Seckill;
import org.seckill.enity.SuccessSeckill;
import org.seckill.enums.SeckillEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Service
public class SeckillServiceImpl implements SeckillService{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SeckillDao seckillDao;
	
	@Autowired
	private SuccessKilledDao successKilledDao;
	
	private final String slat = "sdasdasdasiuqwh";
	
	@Override
	public List<Seckill> getSeckillList() {
		return seckillDao.queryAll(0, 4);	
	}

	@Override
	public Seckill getById(long SeckillId) {
		// TODO Auto-generated method stub
		return seckillDao.queryById(SeckillId);
	}

	@Override
	public Exposer exportSeckillUrl(long seckillId) {
		// TODO Auto-generated method stub
		Seckill seckill = seckillDao.queryById(seckillId);
		if(seckill == null) {
			return new Exposer(false,seckillId);
		}
		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		Date now = new Date();
		if(now.getTime() < startTime.getTime() || now.getTime() > endTime.getTime()) {
			return new Exposer(false, seckillId,now.getTime(),startTime.getTime(),endTime.getTime());
		}
		String md5 = getMD5(seckillId);
		return new Exposer(true,md5,seckillId);
		
	}

	@Override
	@Transactional
	public SeckillExcution executeSeckill(long seckillId, long userPhone, String md5)
			throws SeckillException, RepeatKillException, SeckillCloseException {
		
		//执行秒杀逻辑：减库存 +记录购买行为
		Date now = new Date();
		try {
			if(md5 == null || !md5.equals(getMD5(seckillId))) {
				throw new SeckillException("seckill data rewrite");
			}
			int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
			if(insertCount <= 0) {
				throw new RepeatKillException("seckill repeated");
			} else {
				int updateCount = seckillDao.reduceNumber(seckillId, now);
				if(updateCount <= 0) {
					//没有更新
					throw new SeckillCloseException("seckill has closed");
				} else {
					SuccessSeckill successSeckill = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
					return new SeckillExcution(seckillId,SeckillEnum.SUCCESS, successSeckill);
				}
			}
			
		} catch (SeckillCloseException e1) {
			throw e1;
		}catch (RepeatKillException e2) {
			throw e2;
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			//编译器异常转换运行期异常
			throw new SeckillException("seckill inner error"+e.getMessage());
		}
		
	}
	
	@RequestMapping(value="/time/now",method=RequestMethod.GET)
	public SeckillResult<Long> time(){
		Date now = new Date();
		return new SeckillResult<Long>(true,now.getTime());
	}
	
	private String getMD5(long seckillId) {
		String base = seckillId + "/" + slat;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}

	@Override
	public SeckillExcution executeSeckillProduce(long seckillId, long userPhone, String md5)
			throws SeckillException, RepeatKillException, SeckillCloseException {
		
		if(md5 == null || !md5.equals(getMD5(seckillId))) {
			return new SeckillExcution(seckillId,SeckillEnum.Repeat_KILL);
		}
		// TODO Auto-generated method stub
		return null;
	}

}
