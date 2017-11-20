package com.enm.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import com.google.common.reflect.ClassPath;

public class classutil
{
	final static ClassLoader loader = Thread.currentThread().getContextClassLoader();
	
	public static Class[] getClasses(String packageDir) throws IOException
	{
		List<Class> AllClass = new ArrayList<Class>();
		for (final ClassPath.ClassInfo info : ClassPath.from(loader).getTopLevelClasses())
		{
			  if (info.getName().startsWith(packageDir))
			  {
				  AllClass.add(info.load());
			  }
		}
		return ClassListToClassTable(AllClass);
	}
	
	public static Class[] ClassListToClassTable(List<Class> cls)
	{
		Class[] clsT = new Class[cls.size()];
		int id = 0;
		for(Class c : cls)
		{
			clsT[id] = c;
			++id;
		}
		return clsT;
	}
}
