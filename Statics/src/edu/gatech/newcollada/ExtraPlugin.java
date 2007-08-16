package edu.gatech.newcollada;

import com.jmex.model.collada.schema.extraType;

public interface ExtraPlugin {
	
	public Object processExtra(String profile, Object target, extraType extra);
}
