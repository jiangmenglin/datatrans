package com.hanke.clobrw.type;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BlobType implements IColumnType {

	public BlobType() {
	}

	public void setParameter(PreparedStatement pst, ResultSet rs,
			IColumnType selectColumnType, int index) {
		try {
			pst.setBinaryStream(index, (InputStream)selectColumnType.getValue(rs, index));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Object getValue(ResultSet rs, int index) {
		try {
			return (Blob)rs.getBlob(index).getBinaryStream();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
