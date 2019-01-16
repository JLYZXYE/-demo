package org.seckill.dto;
/*
 * 
 * 秒杀结果
 */

import org.seckill.enity.Seckill;
import org.seckill.enity.SuccessSeckill;
import org.seckill.enums.SeckillEnum;

public class SeckillExcution {
	private long seckillId;
	
	private int state;
	
	private String stateInfo;
	
	private SuccessSeckill successSeckill;

	@Override
	public String toString() {
		return "SeckillExcution [seckillId=" + seckillId + ", state=" + state + ", stateInfo=" + stateInfo
				+ ", successSeckill=" + successSeckill + "]";
	}

	//成功
	public SeckillExcution(long seckillId, SeckillEnum seckillEnum, SuccessSeckill successSeckill) {
		super();
		this.seckillId = seckillId;
		this.state = seckillEnum.getState();
		this.stateInfo = seckillEnum.getStateInfo();
		this.successSeckill = successSeckill;
	}

	//失败
	public SeckillExcution(long seckillId, SeckillEnum seckillEnum) {
		super();
		this.seckillId = seckillId;
		this.state = seckillEnum.getState();
		this.stateInfo = seckillEnum.getStateInfo();
	}

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public SuccessSeckill getSuccessSeckill() {
		return successSeckill;
	}

	public void setSuccessSeckill(SuccessSeckill successSeckill) {
		this.successSeckill = successSeckill;
	}
		
}
