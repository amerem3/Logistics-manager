package com.enm.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotView extends Slot
{

	public SlotView(IInventory inv, int id, int x, int y)
	{
		super(inv, id, x, y);
	}
	
	@Override
	public void putStack(ItemStack arg0)
	{
		
	}
	
	@Override
	public ItemStack getStack()
	{
		return null;
	}
	
	@Override
	public int getSlotStackLimit()
	{
		return 0;
	}
	
	@Override
	public boolean canTakeStack(EntityPlayer arg0)
	{
		return false;
	}
	
	@Override
	public boolean getHasStack()
	{
		return false;
	}
	
	@Override
	public boolean isItemValid(ItemStack arg0)
	{
		return false;
	}

}
