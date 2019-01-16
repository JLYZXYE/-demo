package org.seckill.enity;

import java.io.Serializable;
import java.util.Date;

public class SuccessSeckill implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long seckillId;
	
	private long userPhone;
	
	private short state;
	
	private Date startTime;
	
	
	private Seckill seckill;

	public Seckill getSeckill() {
		return seckill;
	}

	public void setSeckill(Seckill seckill) {
		this.seckill = seckill;
	}

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public long getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(long userPhone) {
		this.userPhone = userPhone;
	}

	public short getState() {
		return state;
	}

	public void setState(short state) {
		this.state = state;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Override
	public String toString() {
		return "SuccessSeckill [seckillId=" + seckillId + ", userPhone=" + userPhone + ", state=" + state
				+ ", startTime=" + startTime + "]";
	}
	
	

}
