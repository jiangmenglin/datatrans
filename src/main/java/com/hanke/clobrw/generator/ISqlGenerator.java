package com.hanke.clobrw.generator;

import com.hanke.clobrw.holder.MapHolder;
import com.hanke.clobrw.holder.SQLHolder;

public interface ISqlGenerator {
	
	SQLHolder gernerateSQl(MapHolder holder);

}
