package com.enm.guipanel.myinstallation;

import java.util.ArrayList;
import java.util.List;

import com.enm.api.util.Vector2;

import net.minecraft.client.gui.GuiScreen;

public class ListStringTag
{
	public List<StringTag> strtag;
	
	public ListStringTag()
	{
		strtag = new ArrayList<StringTag>();
	}
	
	public void add(StringTag st)
	{
		strtag.add(st);
	}
	
	public boolean removeByPos(Vector2 pos)
	{
		for (int i = 0; i < strtag.size(); ++i)
		{
			if(strtag.get(i).posOnMap.x == pos.x && strtag.get(i).posOnMap.y == pos.y)
			{
				strtag.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public StringTag getByPos(Vector2 pos)
	{
		for (int i = 0; i < strtag.size(); ++i)
		{
			if(strtag.get(i).posOnMap.x == pos.x && strtag.get(i).posOnMap.y == pos.y)
			{
				return strtag.get(i);
			}
		}
		return null;
	}
	
	public void setByPos(Vector2 pos, StringTag element)
	{
		for (int i = 0; i < strtag.size(); ++i)
		{
			if(strtag.get(i).posOnMap.x == pos.x && strtag.get(i).posOnMap.y == pos.y)
			{
				strtag.set(i, element);
				return;
			}
		}
		strtag.add(element);
	}
	
	public void removeAll()
	{
		strtag = new ArrayList<StringTag>();
	}
	
	public void drawAtPos(GuiScreen gui, int decx, int decy, int color)
	{
		for (StringTag t : strtag)
		{
			gui.drawString(gui.mc.fontRenderer, t.text, t.posOnMap.x*20 + decx, t.posOnMap.y*20 + decy, color);
		}
	}
}
