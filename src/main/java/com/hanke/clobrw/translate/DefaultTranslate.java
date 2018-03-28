package com.hanke.clobrw.translate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hanke.clobrw.holder.ColumnHolder;
import com.hanke.clobrw.holder.MapHolder;
import com.hanke.clobrw.holder.SQLHolder;
import com.hanke.clobrw.util.ConnectionUtil;

public class DefaultTranslate extends AbstractTranslate {
	private static final Logger log = LoggerFactory.getLogger(DefaultTranslate.class);
	
	public DefaultTranslate(List<MapHolder> list) {
		super(list);
	}
	
	private int insert(String source, String target,ResultSet rs, MapHolder item
			, PreparedStatement targetPst, Connection targetConn, int count, SQLHolder sqls) {
		try {
			while (rs.next()) {
				count++;
				if(targetPst != null)
					targetPst.close();
				targetPst = targetConn.prepareStatement(sqls.getTargetSql());
				//从rs中获取结果设置到targetPst中
				List<ColumnHolder> list = item.getColumns();
				for (ColumnHolder columnHolder : list) {
					columnHolder.setParameter(targetPst, rs);
				}
				targetPst.executeUpdate();
				targetPst.close();
				log.info("从{}到{}执行第{}条。\n", new Object[]{source, target, count});
			}
			return 0;
		} catch (Exception e) {
			log.error("从{}到{}执行第{}条,发生异常：\n", new Object[]{source, target, count});
			log.error(e.getMessage());
			e.printStackTrace();
			return count;
		}
	}
	
	@Override
	protected void translate(List<MapHolder> holders) {
		//获取数据库连接
		Connection sourceConn = null;
		PreparedStatement sourcePst = null;
		ResultSet rs = null;
		Connection targetConn = null;
		PreparedStatement targetPst = null;
		try {
			for(MapHolder item : holders) {
				SQLHolder sqls = item.getGenerator().gernerateSQl(item);
				sourceConn = ConnectionUtil.getConn("source");
				sourcePst = sourceConn.prepareStatement(sqls.getSourceSql());
				rs = sourcePst.executeQuery();//查询出结果
				targetConn = ConnectionUtil.getConn("target");
				if (rs != null) {
					int result = insert(item.getSourceName(), item.getTargetName(), rs
							,  item,  targetPst,  targetConn, 0,  sqls);
					while(result > 0){
						result = insert(item.getSourceName(), item.getTargetName(), rs
								,  item,  targetPst,  targetConn, result,  sqls);
					}
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			//close resource
			ConnectionUtil.close(null, targetPst, targetConn);
			ConnectionUtil.close(rs, sourcePst, sourceConn);
		}
		
	}
	
}
