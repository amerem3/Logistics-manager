package com.enm.api.util;

import net.minecraft.nbt.NBTTagCompound;

public class Vector2
{
	public int x,y;
	
	public Vector2(int _x, int _y)
	{
		x = _x;
		y = _y;
	}
	
	public Vector2()
	{
		x = 0;
		y = 0;
	}
	
	public Vector2 copy()
	{
		return new Vector2(x, y);
	}
	
	public void SaveToNBT(NBTTagCompound nbt, String name)
	{
		nbt.setInteger(name+"X", x);
		nbt.setInteger(name+"Y", y);
	}
	
	public void ReadFromNBT(NBTTagCompound nbt, String name)
	{
		x = nbt.getInteger(name+"X");
		y = nbt.getInteger(name+"Y");
	}
	
	public String GetOutPutConsole()
	{
		return "X:"+x+" Y:"+y;
	}
}
