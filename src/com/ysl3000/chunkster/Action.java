package com.ysl3000.chunkster;

public enum Action {
	REMOVE("removed"), ADD("added"), ADD_FAILED("Failed to add"), REMOVE_FAILED("Failed to remove");
	
	private String value;
	
	private Action(String value){
		this.value = value;
	}
	public String getValue(){
		return this.value;
	}
}
