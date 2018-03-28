package com.hanke.clobrw.holder;

import com.hanke.clobrw.type.IColumnType;

public class Column {
	
	private String name;
	
	private IColumnType type;

	public Column() {
	}

	public Column(String name, IColumnType type) {
		super();
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public IColumnType getType() {
		return type;
	}

	public void setType(IColumnType type) {
		this.type = type;
	}
	
	

}
