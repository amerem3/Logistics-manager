package com.enm.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotFilter extends Slot
{
	ItemsFilter filter;
	
	public SlotFilter(ItemsFilter _filter, IInventory inv, int id, int x, int y)
	{
		super(inv, id, x, y);
		filter = _filter;
	}
	
	@Override
	public boolean isItemValid(ItemStack it)
	{
		if(it != null)
		for(Item i : filter.GetWhiteList())
		{
			if(i.equals(it.getItem()))return true;
		}
		return false;
	}

}
