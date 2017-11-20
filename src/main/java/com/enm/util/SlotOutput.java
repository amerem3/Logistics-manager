package com.enm.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotOutput extends Slot
{

	public SlotOutput(IInventory inv, int id, int x, int y)
	{
		super(inv, id, x, y);
	}
	
	@Override
	public void putStack(ItemStack arg0)
	{
		
	}
	
	@Override
	public boolean isItemValid(ItemStack arg0)
	{
		return false;
	}

}
