package com.hanke.clobrw.type;

import java.io.Reader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClobType implements IColumnType {

	public ClobType() {
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
			Reader reader=((java.sql.Clob)rs.getClob(index)).getCharacterStream();
			return reader;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
