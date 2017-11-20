package com.enm.info;

import com.enm.core.CoreMod;
import com.enm.core.FileResource;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;

public class InfoStringGui
{
	String target = "";
	InfoFile inffile = null;
	public InfoStringGui()
	{
		
	}
	
	public void draw(String _target, int x, int y, int color, GuiContainer c, FontRenderer f)
	{
		if(!_target.equals(target))
		{
			target = _target;
			inffile = null;
			//String[] tag = target.split(":");
			if(FileResource.AsFile(CoreMod.classLoader, "doc/"+target+".inf"))
			{
				inffile = new InfoFile("doc/"+target+".inf");
			}
			else
			{
				System.out.println("[doc file not found] doc/"+target+".inf");
			}
		}
		if(inffile != null)
		{
			int yp = 0;
			for(String ln: inffile.content)
			{
				c.drawString(f, ln, x, y + (yp*10), color);
				++yp;
			}
		}
	}
}
