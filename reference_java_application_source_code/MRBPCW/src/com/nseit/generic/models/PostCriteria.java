package com.nseit.generic.models;

public class PostCriteria {
	Integer min;
	Integer max;
	
	public PostCriteria(Integer min,Integer max){
		this.min=min;
		this.max=max;
	}
	public Integer getMin() {
		return min;
	}
	public void setMin(Integer min) {
		this.min = min;
	}
	public Integer getMax() {
		return max;
	}
	public void setMax(Integer max) {
		this.max = max;
	}

}
