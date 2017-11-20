package com.enm.block.util;

import com.enm.core.ENMResource;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;

public abstract class BlockENM extends BlockContainer implements BlockIdentity
{
	public BlockENM(Material material)
	{
		super(material);
		setCreativeTab(ENMResource.ENMCreativeTab);
		setHardness(2.5f);
		setResistance(2.3f);
		setBlockName(BlockClassName());
	}
	
	public void Register(BlockENM b)
	{
		GameRegistry.registerBlock(b, b.BlockClassName());
		LanguageRegistry.addName(b, b.DisplayBlockName());
	}

}
