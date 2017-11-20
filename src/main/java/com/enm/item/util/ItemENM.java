package com.enm.item.util;

import com.enm.block.util.BlockENM;
import com.enm.core.ENMResource;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public abstract class ItemENM extends Item implements ItemIdentity
{
	public ItemENM()
	{
		super();
		setCreativeTab(ENMResource.ENMCreativeTab);
		setUnlocalizedName(ItemClassName());
	}
	
	public void Register(ItemENM i)
	{
		GameRegistry.registerItem(i, i.ItemClassName());
		LanguageRegistry.addName(i, i.DisplayItemName());
	}
	
}
