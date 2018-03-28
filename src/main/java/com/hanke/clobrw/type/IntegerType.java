package com.hanke.clobrw.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IntegerType implements IColumnType {

	public IntegerType() {
	}

	public void setParameter(PreparedStatement pst, ResultSet rs,
			IColumnType selectColumnType, int index) {
		try {
			pst.setObject(index, selectColumnType.getValue(rs, index));
		} catch (SQLException e) {
			e.printStackTrace();
		};
	}

	public Object getValue(ResultSet rs, int index) {
		try {
			return rs.getInt(index);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
