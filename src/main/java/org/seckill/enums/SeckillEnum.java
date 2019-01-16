package org.seckill.enums;

public enum SeckillEnum {
	SUCCESS(1,"秒杀成功"),
	END(0,"秒杀结束"),
	Repeat_KILL(-1,"重复秒杀"),
	INNER_KILL(-2,"内部错误"),
	DATA_REWRITE(-3,"数据篡改");
	
	
	private int state;
	private String stateInfo;
	private SeckillEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
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
	
	
	public static SeckillEnum stateOf(int index) {
		for (SeckillEnum state: values()) {
			if(state.getState() == index) {
				return state;
			}
		}
		return null;
	}

}
