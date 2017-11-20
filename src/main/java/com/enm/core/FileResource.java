package com.enm.core;

import java.io.InputStream;
import java.io.InputStreamReader;

public class FileResource
{
	public static InputStreamReader GetFile(ClassLoader loader, String file)
	{
    	return new InputStreamReader(loader.getResourceAsStream("assets/networksmanager/"+file));
	}
	
	public static boolean AsFile(ClassLoader loader, String file)
	{
    	try
    	{
    		loader.getResourceAsStream("assets/networksmanager/"+file).read();
    		return true;
    	}
    	catch (Exception e)
    	{
    		return false;
    	}
	}
}
