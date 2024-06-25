package com.sixplus.server.api.utils;

public class CmmBean implements Cloneable {
	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public String toString() {
		return CmmUtils.toJsonString(this);
	}
}
