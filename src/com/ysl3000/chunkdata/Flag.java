package com.ysl3000.chunkdata;

import java.io.Serializable;

public class Flag implements Serializable{

	private static final long serialVersionUID = 1L;
	private String key;
	private boolean value;

	public Flag(String key, boolean value) {
		this.key = key;
		this.value = value;
	}

	public Boolean getValue() {
		return value;
	}

	public String getKey() {
		return this.key;
	}

	public void setValue(boolean value){
		this.value = value;
	}
	public void changeValue(boolean b) {
		this.value = b;
	}
}
