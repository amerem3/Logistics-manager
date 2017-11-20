package com.enm.util;

import com.enm.api.util.Vector2;

public class Zone2D
{
	public int XStart, YStart, XEnd, YEnd;
	
	public Zone2D(int _XStart, int _YStart, int _XEnd, int _YEnd)
	{
		XStart = _XStart;
		YStart = _YStart;
		XEnd = _XEnd;
		YEnd = _YEnd;
	}
	
	public boolean MouseIsOnArea(Vector2 mousepos)
	{
		if(mousepos.x >= XStart && mousepos.x <= XEnd && mousepos.y >= YStart && mousepos.y <= YEnd)return true;
		return false;
	}
}
