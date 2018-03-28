package com.hanke.clobrw.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;


public interface IColumnType {
	
	void setParameter(PreparedStatement pst, ResultSet rs,
			IColumnType selectColumnType, int index);

	Object getValue(ResultSet rs, int index);
}
