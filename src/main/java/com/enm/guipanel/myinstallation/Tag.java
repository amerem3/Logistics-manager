package com.enm.guipanel.myinstallation;

import com.enm.api.util.Vector2;
import com.enm.api.util.Vector3;

public class Tag
{
	public Vector2 mappos;
	public Type type;
	public Vector3 args;
	
	public Tag(Vector2 _mappos, Type _type)
	{
		mappos = _mappos;
		type = _type;
		args = new Vector3(0, 0, 0);
	}
	
	public enum Type 
	{
		Switch,
		Counter,
		FluxMetter,
		StorageInfo,
		StringTag,
		FluidName
	}
}
