package com.enm.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemsFilter
{
	private List<Item> filteritem;
	
	public ItemsFilter()
	{
		filteritem = new ArrayList<Item>();
	}
	
	public void addItemOnWhiteList(Item i)
	{
		filteritem.add(i);
	}
	
	public void addItemOnWhiteList(Block i)
	{
		filteritem.add(Item.getItemFromBlock(i));
	}
	
	public void addItemOnWhiteList(ItemStack i)
	{
		filteritem.add(i.getItem());
	}
	
	public void RemoveItemOnWhiteList(Item i)
	{
		int index_id = 0;
		for(Item ii : filteritem)
		{
			if(ii.equals(i)) filteritem.remove(index_id);
			
			++index_id;
		}
	}
	
	public void RemoveItemOnWhiteList(Block i)
	{
		int index_id = 0;
		for(Item ii : filteritem)
		{
			if(ii.equals(Item.getItemFromBlock(i))) filteritem.remove(index_id);
			
			++index_id;
		}
	}
	
	public void RemoveItemOnWhiteList(ItemStack i)
	{
		int index_id = 0;
		for(Item ii : filteritem)
		{
			if(ii.equals(i.getItem())) filteritem.remove(index_id);
			
			++index_id;
		}
	}
	
	public List<Item> GetWhiteList()
	{
		return filteritem;
	}
	
}
