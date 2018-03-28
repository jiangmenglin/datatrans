package com.hanke.clobrw.holder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ColumnHolder {
	
	private Column source;
	
	private Column target;
	
	private int index;
	
	public void setParameter(PreparedStatement pst, ResultSet rs) {
		target.getType().setParameter(pst, rs, source.getType(), index);
	}
	
	public ColumnHolder(){}

	public ColumnHolder(Column source, Column target, int index) {
		super();
		this.source = source;
		this.target = target;
		this.index = index;
	}

	public Column getSource() {
		return source;
	}

	public void setSource(Column source) {
		this.source = source;
	}

	public Column getTarget() {
		return target;
	}

	public void setTarget(Column target) {
		this.target = target;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
}
