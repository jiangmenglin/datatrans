package com.hanke.clobrw.type;

import java.io.Reader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StringType implements IColumnType {

	public StringType() {
	}

	public void setParameter(PreparedStatement pst, ResultSet rs,
			IColumnType selectColumnType, int index) {
		try {
			pst.setCharacterStream(index, (Reader)selectColumnType.getValue(rs, index));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Object getValue(ResultSet rs, int index) {
		try {
			return rs.getCharacterStream(index);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
