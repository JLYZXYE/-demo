package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.enity.SuccessSeckill;

public interface SuccessKilledDao {
	
	
	int insertSuccessKilled(@Param("seckillId")long seckillId, @Param("userPhone")long userPhone);
	
	SuccessSeckill queryByIdWithSeckill(@Param("seckillId")long seckillId, @Param("userPhone")long userPhone);;
	
	
}
