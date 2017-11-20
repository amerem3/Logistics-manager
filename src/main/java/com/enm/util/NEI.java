package com.enm.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NEI
{
	static Class class_NEIClientConfig;
	static Object instance_NEIClientConfig;
	public static void Load()
	{
		try
		{
			class_NEIClientConfig = Class.forName("codechicken.nei.NEIClientConfig");
			try
			{
				instance_NEIClientConfig = class_NEIClientConfig.newInstance();
			}
			catch (InstantiationException e)
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void setInternalEnabled(boolean flag)
	{
		if(class_NEIClientConfig != null && instance_NEIClientConfig != null)
		{
			try
			{
				Method met = class_NEIClientConfig.getDeclaredMethod("setInternalEnabled", boolean.class);
				met.setAccessible(true);
				try
				{
					met.invoke(instance_NEIClientConfig, flag);
				}
				catch (IllegalAccessException e)
				{
					e.printStackTrace();
				}
				catch (IllegalArgumentException e)
				{
					e.printStackTrace();
				}
				catch (InvocationTargetException e)
				{
					e.printStackTrace();
				}
			}
			catch (NoSuchMethodException e)
			{
				e.printStackTrace();
			}
			catch (SecurityException e)
			{
				e.printStackTrace();
			}
		}
	}
}
	
