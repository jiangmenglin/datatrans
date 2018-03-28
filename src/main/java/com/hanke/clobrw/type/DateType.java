package com.hanke.clobrw.type;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DateType implements IColumnType {

	public void setParameter(PreparedStatement pst, ResultSet rs,
			IColumnType selectColumnType, int index) {
		if(selectColumnType.getValue(rs, index) instanceof Date) {
			try {
				pst.setDate(index, (Date)selectColumnType.getValue(rs, index));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(selectColumnType.getValue(rs, index) instanceof Timestamp) {
			Timestamp t = (Timestamp)selectColumnType.getValue(rs, index);
			Date d = new Date(t.getTime());
			try {
				pst.setDate(index, d);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			throw new RuntimeException("DateType 没有设置值");
		}
	}

	public Object getValue(ResultSet rs, int index) {
		try {
			return rs.getDate(index);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
