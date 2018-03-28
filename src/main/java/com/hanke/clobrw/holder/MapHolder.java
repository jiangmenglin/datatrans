package com.hanke.clobrw.holder;

import java.util.List;

import com.hanke.clobrw.generator.ISqlGenerator;

public class MapHolder {
	private ISqlGenerator generator = null; //crud type
	
	//oracle中的表名和MySQL中的表名的对应
	private String sourceName = null;
	private String targetName = null;
	
	//oracle中的表和MySQL中的表的关联字段
	private Column sourceId;
	
	private Column targetId;
	
	//oracle表的字段和MySQL的字段的对应
	private List<ColumnHolder> columns;
	
	public MapHolder() {
	}

	public MapHolder(ISqlGenerator generator, String sourceName,
			String targetName, Column sourceId, Column targetId,
			List<ColumnHolder> columns) {
		super();
		this.generator = generator;
		this.sourceName = sourceName;
		this.targetName = targetName;
		this.sourceId = sourceId;
		this.targetId = targetId;
		this.columns = columns;
	}

	public ISqlGenerator getGenerator() {
		return generator;
	}

	public void setGenerator(ISqlGenerator generator) {
		this.generator = generator;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public Column getSourceId() {
		return sourceId;
	}

	public void setSourceId(Column sourceId) {
		this.sourceId = sourceId;
	}

	public Column getTargetId() {
		return targetId;
	}

	public void setTargetId(Column targetId) {
		this.targetId = targetId;
	}

	public List<ColumnHolder> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnHolder> columns) {
		this.columns = columns;
	}
	
}
