package com.hanke.clobrw.generator;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hanke.clobrw.holder.ColumnHolder;
import com.hanke.clobrw.holder.MapHolder;
import com.hanke.clobrw.holder.SQLHolder;
import com.hanke.clobrw.util.StringUtil;

public class InsertGenerator implements ISqlGenerator {
	private static final Logger log = LoggerFactory.getLogger(InsertGenerator.class);

	public SQLHolder gernerateSQl(MapHolder holder) {
		if (holder == null) 
			return null;
		
		StringBuilder sourceSQL = new StringBuilder("select ");
		StringBuilder targetSQL = new StringBuilder("insert into ");
		String targetName = holder.getTargetName();
		String sourceName = holder.getSourceName();
		
		if (StringUtil.isBlank(sourceName) || StringUtil.isBlank(targetName))
			throw new RuntimeException(holder.getClass().getName() + "对象的targetName 或 sourceName 为空！");
		targetSQL.append(targetName).append(" (");
		
		List<ColumnHolder> columns = holder.getColumns();
		if (columns == null || columns.isEmpty())
			throw new RuntimeException(holder.getClass().getName() + "'s columnsMap is null or empty");
		
		StringBuilder valuesSQL = new StringBuilder("values("); 
		for (ColumnHolder item : columns) {
			sourceSQL.append(item.getSource().getName()).append(",");
			targetSQL.append(item.getTarget().getName()).append(",");
			valuesSQL.append("?,");
		}
		sourceSQL.deleteCharAt(sourceSQL.length() - 1).append(" from ").append(sourceName);
		valuesSQL.deleteCharAt(valuesSQL.length() - 1).append(")");
		targetSQL.deleteCharAt(targetSQL.length() - 1).append(") ").append(valuesSQL);
		log.info("sourceSQL:{}\n", sourceSQL);
		log.info("targetSQL:{}\n", targetSQL);
		return new SQLHolder(sourceSQL.toString(), targetSQL.toString());
	}

}
