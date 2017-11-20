package com.enm.info;

import java.util.List;

import com.enm.core.CoreMod;
import com.enm.core.FileResource;
import com.enm.util.FileUtil;

public class InfoFile
{
	public List<String> content;
	
	public InfoFile(String infofile)
	{
		if(CoreMod.classLoader == null)return;
		content = FileUtil.ReadFileLine(FileResource.GetFile(CoreMod.classLoader, infofile));
	}
}
