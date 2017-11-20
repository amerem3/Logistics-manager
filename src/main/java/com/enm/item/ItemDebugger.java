package com.enm.item;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.MessageFormat;

import com.enm.item.util.ItemENM;

import mekanism.api.gas.GasTank;
import mekanism.api.gas.IGasHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemDebugger extends ItemENM
{

	@Override
	public String DisplayItemName()
	{
		return "Debugger";
	}

	@Override
	public String ItemClassName()
	{
		return getClass().getSimpleName();
	}
	
	@Override
	public boolean onItemUse(ItemStack item, EntityPlayer player, World world, int x, int y, int z, int meta, float delatx, float deltay, float deltaz)
	{
		player.addChatMessage(new ChatComponentText("Block: "+world.getBlock(x, y, z)));
		
		TileEntity te = world.getTileEntity(x, y, z);
		if(te != null)
		{
			if(te.getClass().getSimpleName().contains("TileEntityGasTank"))
			try
			{
				Field f = te.getClass().getField("gasTank");
				f.setAccessible(true);
				Object instance = f.getType().newInstance();
				System.out.println("l");
				//te.getClass().getField("gasTank");
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//if(te instanceof IGasHandler)
			/*
			//Method[] ms = te.getClass().getMethods();
			Class[] cs = te.getClass().getInterfaces();
			player.addChatMessage(new ChatComponentText("Class: "+te.getClass().getName()));
			player.addChatMessage(new ChatComponentText("methods:"));
			for(Method m : ms)
			{
				player.addChatMessage(new ChatComponentText(" -"+m.getName()));
			}
			player.addChatMessage(new ChatComponentText("declared class:"));
			for(Class c : cs)
			{
				if(c.isInterface())
				player.addChatMessage(new ChatComponentText(" -[I]"+c.getName()));
				else player.addChatMessage(new ChatComponentText(" -[C]"+c.getName()));
			}*/
			
			
		}
		return true;
	}

}
