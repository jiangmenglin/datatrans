package com.hanke.clobrw.translate;

import java.util.List;

import com.hanke.clobrw.holder.MapHolder;


public abstract class AbstractTranslate implements Translatable {
	
	private List<MapHolder> list = null;
	
	protected AbstractTranslate(List<MapHolder> list) {
		this.list = list;
	}
	
	protected abstract void translate(List<MapHolder> list);

	public void translate() {
		translate(list);
	} 
	

}
