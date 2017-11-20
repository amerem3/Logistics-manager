package com.enm.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraftforge.common.util.Constants;

public abstract class Inventory implements IInventory
{
	public String name;
	public ItemStack[] items;
	public int StackMaxSize;
	
	public Inventory(String _name, int _StackMaxSize, int size)
	{
		name = _name;
		items = new ItemStack[size];
		StackMaxSize = _StackMaxSize;
	}
	
	public String GetNBTName()
	{
		return "inventory_"+name;
	}
	
	public void writeToNBT(NBTTagCompound nbt)
	{
		NBTTagList itemlist = new NBTTagList();

		for (int i = 0; i < items.length; i++)
		{
			ItemStack itemstack = items[i];

		    if (itemstack != null)
		    {
		    	NBTTagCompound tag = new NBTTagCompound();

		        tag.setByte("Slot", (byte)i);
		        itemstack.writeToNBT(tag);
		        itemlist.appendTag(tag);
		    }
		}
		nbt.setTag("Inventory", itemlist);
	}
	
	public void readFromNBT(NBTTagCompound nbt)
	{
		NBTTagList taglist = nbt.getTagList("Inventory", Constants.NBT.TAG_COMPOUND);

		for (int i = 0; i < taglist.tagCount(); i++)
		{
			NBTTagCompound tag = (NBTTagCompound)taglist.getCompoundTagAt(i);

		    byte slot = tag.getByte("Slot");

		    if (slot >= 0 && slot < items.length)
		    {
		    	items[slot] = ItemStack.loadItemStackFromNBT(tag);
		    }
		}
	}

	/*@Override
	public void closeInventory()
	{
		
	}*/

	@Override
	public ItemStack decrStackSize(int id, int size)
	{
		if(items[id] != null)
		{
			ItemStack out = items[id].copy();
			
			if(out.stackSize <= size)
			{
				//out.stackSize;
				items[id] = null;
			}
			else
			{
				out.stackSize = size;
				items[id].stackSize -= size;
			}
			
			return out;
		}
		return null;
	}

	@Override
	public String getInventoryName()
	{
		return name;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return StackMaxSize;
	}

	@Override
	public int getSizeInventory()
	{
		return items.length;
	}

	@Override
	public ItemStack getStackInSlot(int id)
	{
		return items[id];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int id)
	{
		return items[id];
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return true;
	}

	@Override
	public boolean isItemValidForSlot(int id, ItemStack input) 
	{
		if(items[id] != null)if(items[id].getItem().equals(input.getItem()))return true;
		return false;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return true;
	}

	/*@Override
	public void markDirty()
	{
		
	}

	@Override
	public void openInventory()
	{
		
	}*/

	@Override
	public void setInventorySlotContents(int id, ItemStack itemstack)
	{
		items[id] = itemstack;
	}

}
