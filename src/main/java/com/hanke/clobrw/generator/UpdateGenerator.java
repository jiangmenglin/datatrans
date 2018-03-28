package com.hanke.clobrw.generator;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hanke.clobrw.holder.ColumnHolder;
import com.hanke.clobrw.holder.MapHolder;
import com.hanke.clobrw.holder.SQLHolder;
import com.hanke.clobrw.util.StringUtil;

public class UpdateGenerator implements ISqlGenerator {
	private static final Logger log = LoggerFactory.getLogger(UpdateGenerator.class);

	public SQLHolder gernerateSQl(MapHolder holder) {
		if (holder == null) 
			return null;
		
		StringBuilder sourceSQL = new StringBuilder("select ");
		StringBuilder targetSQL = new StringBuilder("update ");
		String targetName = holder.getTargetName();
		String sourceName = holder.getSourceName();
		
		if (StringUtil.isBlank(sourceName) || StringUtil.isBlank(targetName))
			throw new RuntimeException(holder.getClass().getName() + "对象的sourceName 或 targetName 为空！");
		targetSQL.append(targetName);
		
		List<ColumnHolder> columns = holder.getColumns();
		if (columns == null || columns.isEmpty())
			throw new RuntimeException(holder.getClass().getName() + "'s columnsMap is null or empty");
		
		if (StringUtil.isBlank(holder.getSourceId().getName()) || StringUtil.isBlank(holder.getTargetId().getName()))
			throw new RuntimeException(holder.getClass().getName() + "'s sourceId or targetId is null or empty");
		
		StringBuilder setSQL = new StringBuilder(" set ");
		for (ColumnHolder item : columns) {
			sourceSQL.append(item.getSource().getName()).append(",");
			setSQL.append(item.getTarget().getName()).append("=?,");
		}
		sourceSQL.append(holder.getSourceId().getName()).append(" from ").append(sourceName);
		setSQL.deleteCharAt(setSQL.length() - 1);
		targetSQL.append(setSQL);
		targetSQL.append(" where ").append(holder.getTargetId().getName()).append("=?");
		columns.add(new ColumnHolder(holder.getSourceId(), holder.getTargetId(), columns.size() + 1));
		log.info("sourceSQL:{}\n", sourceSQL);
		log.info("targetSQL:{}\n", targetSQL);
		return new SQLHolder(sourceSQL.toString(), targetSQL.toString());
	}

}
