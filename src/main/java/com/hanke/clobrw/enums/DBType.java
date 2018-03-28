package com.hanke.clobrw.enums;

public enum DBType {
	ORACLE("oracle"),MYSQL("mysql");
	
	private String name;
	
	private DBType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}

}
