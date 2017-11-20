package com.enm.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

public class NBTUtil
{
	public static void ItemStackToNBT(NBTTagList itemlist, ItemStack itemstack, int id)
	{
	    if (itemstack != null)
	    {
	    	NBTTagCompound tag = new NBTTagCompound();

	        tag.setByte("Slot", (byte)id);
	        itemstack.writeToNBT(tag);
	        itemlist.appendTag(tag);
	    }
	}
	
	public static ItemStack ItemStackFromNBT(NBTTagList taglist, int id)
	{
		for (int i = 0; i < taglist.tagCount(); i++)
		{
			NBTTagCompound tag = (NBTTagCompound)taglist.getCompoundTagAt(i);

		    byte slot = tag.getByte("Slot");

		    if (slot == id)
		    {
		    	return ItemStack.loadItemStackFromNBT(tag);
		    }
		}
		return null;
	}
	
	public static NBTTagCompound GetNBTOnDirectory(NBTTagCompound nbt, String dir)
	{
		for(String d : dir.split("/"))
		{
			nbt = nbt.getCompoundTag(d);
		}
		return nbt;
	}
	
	public static NBTTagCompound SetNBTOnDirectory(NBTTagCompound nbt, NBTTagCompound mynbt, String dir)
	{
		String[] comp = dir.split("/");
		for(int id = 0; id < comp.length; id++)
		{
			if(id == comp.length-1){nbt.setTag(comp[id], mynbt); break;}
			nbt = nbt.getCompoundTag(comp[id]);
		}
		return nbt;
	}
	
	public static int GetNumberOfNBTInNBT(NBTTagCompound nbt)
	{
		if(nbt == null) return 0;
		String nbts = ""+nbt;
		int length = 0;
		for(int idc = 0; idc < nbts.length(); idc++)
		{
			if(':' == nbts.charAt(idc) && '{' == nbts.charAt(idc +1))
			{
				//length ++;
				idc++;
				for(char c : nbts.substring(idc).toCharArray())
				{
					if(c == '}')
					{
						length ++;
						break;
					}
					else idc++;
				}
			}
		}
		return length;
	}
	
	public static List<String> GetListNameOfNBTInNBT(NBTTagCompound nbt)
	{
		List<String> varsname = new ArrayList<String>();
		if(nbt == null) return new ArrayList<String>();
		String nbts = ""+nbt;
		for(int idc = 0; idc < nbts.length(); idc++)
		{
			if(':' == nbts.charAt(idc) && '{' == nbts.charAt(idc +1))
			{
				int iid = 0;
				for(int id = 0; (nbts.charAt(idc)-1)-id == '{'; id--)
				{
					iid = id;
				}
				String varName = nbts.substring((idc-1)-iid, idc);
				varsname.add(varName);
				idc++;
				for(char c : nbts.substring(idc).toCharArray())
				{
					if(c != '}')idc++;
				}
			}
		}
		return varsname;
	}
	
	public static int GetNumberOfStringInNBT(NBTTagCompound nbt)
	{
		if(nbt == null) return 0;
		char strg = "\"".charAt(0);
		String nbts = ""+nbt;
		int length = 0;
		for(int idc = 0; idc < nbts.length(); idc++)
		{
			if(':' == nbts.charAt(idc) && '{' == nbts.charAt(idc +1))//NBT
			{
				idc++;
				for(char c : nbts.substring(idc).toCharArray())
				{
					if(c == '}')
					{
						break;
					}
					else idc++;
				}
			}
			else if(':' == nbts.charAt(idc) && '"' == nbts.charAt(idc +1))
			{
				idc+=2;
				for(char c : nbts.substring(idc).toCharArray())
				{
					if(c == '"')
					{
						length++;
						break;
					}
					else idc++;
				}
			}
		}
		return length;
	}
}
