package com.hanke.clobrw.type;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TimestampType implements IColumnType {

	public void setParameter(PreparedStatement pst, ResultSet rs,
			IColumnType selectColumnType, int index) {
		if(selectColumnType.getValue(rs, index) instanceof Timestamp)
			try {
				pst.setTimestamp(index, (Timestamp)selectColumnType.getValue(rs, index));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		else if(selectColumnType.getValue(rs, index) instanceof Date)
			try {
				Date d = (Date)selectColumnType.getValue(rs, index);
				Timestamp t = new Timestamp(d.getTime());
				pst.setTimestamp(index, t);
			} catch (Exception e) {
				e.printStackTrace();
			}
		else
			throw new RuntimeException("TimestampType 没有设置值");
	}

	public Object getValue(ResultSet rs, int index) {
		try {
			return rs.getTimestamp(index);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
