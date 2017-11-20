package com.enm.item;

import com.enm.core.CoreMod;
import com.enm.item.util.ItemENM;
import com.enm.register.RItems;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemScrewdriver extends ItemENM
{
	public ItemScrewdriver()
	{
		setTextureName(CoreMod.MODID+":screwdriver");
	}
	
	@Override
	public String ItemClassName()
	{
		return getClass().getName();
	}
	
	@Override
	public String DisplayItemName()
	{
		return "Screwdriver";
	}
	
	@Override
	public boolean onItemUse(ItemStack item, EntityPlayer player, World wolrd, int x, int y, int z, int meta, float deltaX, float deltaY, float deltaZ)
	{
		return true;
	}

}
