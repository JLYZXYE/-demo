package org.seckill.service;

import java.util.List;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExcution;
import org.seckill.enity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;

/*
 * 站在使用者角度
 */
public interface SeckillService {
	List<Seckill> getSeckillList();
	
	Seckill getById(long SeckillId);
	/*
	 * 秒杀开启输出秒杀接口地址
	 * 否则输出系统时间和秒杀时间
	 */
	Exposer exportSeckillUrl(long seckillId);
	/*
	 * 秒杀操作
	 */
	SeckillExcution executeSeckill(long seckillId,long userPhone,String md5)throws SeckillException,RepeatKillException,SeckillCloseException;
		
	
	SeckillExcution executeSeckillProduce(long seckillId,long userPhone,String md5)throws SeckillException,RepeatKillException,SeckillCloseException;
	
	
}
