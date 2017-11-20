package com.enm.math;

import com.enm.api.util.Vector3;

public class math1
{
	public static int getGrap(int a, int b)
	{
		int c = a - b;
		if(c <0) c -= (c*2);
		return c;
	}
	
	public static int getAexponentB(int a, int b)
	{
		if(b <= 0)return 0;
		int out = a;
		for(int c = 0; c < b-1; ++c) out *= a;
		return out;
	}
	
	public static int getVolume(int x, int y, int z)
	{
		return x*y*z;
	}
	
	public static int getAir(int x, int y)
	{
		return x*y;
	}
	
	public static Vector3 getModifer(Vector3 a, Vector3 b)
	{
		if(a == null || b == null) return new Vector3();
		return new Vector3(getGrap(a.x, b.x) > 0 ? 1 : 0, getGrap(a.y, b.y) > 0 ? 1 : 0, getGrap(a.z, b.z) > 0 ? 1 : 0);
	}
	
	public static int getModiferNumber(Vector3 a)
	{
		if(a == null)return 0;
		return a.x+a.y+a.z;
	}
}
