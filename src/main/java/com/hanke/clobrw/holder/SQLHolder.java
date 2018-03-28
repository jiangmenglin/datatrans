package com.hanke.clobrw.holder;

public class SQLHolder {
	
	private String sourceSql;
	
	private String targetSql;

	public SQLHolder() {
	}

	public SQLHolder(String sourceSql, String targetSql) {
		super();
		this.sourceSql = sourceSql;
		this.targetSql = targetSql;
	}

	public String getSourceSql() {
		return sourceSql;
	}

	public void setSourceSql(String sourceSql) {
		this.sourceSql = sourceSql;
	}

	public String getTargetSql() {
		return targetSql;
	}

	public void setTargetSql(String targetSql) {
		this.targetSql = targetSql;
	}
	
}
