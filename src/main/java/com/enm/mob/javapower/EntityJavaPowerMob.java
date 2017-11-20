package com.enm.mob.javapower;

import java.util.List;

import com.enm.register.RBlocks;
import com.enm.register.RItems;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class EntityJavaPowerMob extends EntityAnimal{

	public EntityJavaPowerMob(World par1World)
	{
		super(par1World);
		this.setSize(0.6F, 1.8F);
		this.tasks.addTask(0, new EntityAIWander(this, 0.5D));
		this.tasks.addTask(1, new EntityAIPanic(this, 1.2D));
		this.tasks.addTask(2, new EntityAITempt(this, 2.0D, Item.getItemFromBlock(RBlocks.Block_RFTerminal), false));
		
		
	}
	
	public boolean isAIEnabled()
	{
		return true;
	}
	
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(16.0F);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
	}
	
	@Override
	protected Item getDropItem()
	{
		int cha = rand.nextInt(OreDictionary.getOreNames().length);
		List<ItemStack> items = OreDictionary.getOres(OreDictionary.getOreNames()[cha]);
		if(items != null && items.size() > 0)
		{
			return items.get(0).getItem();
		}
		
		return null;
	}

	@Override
	public EntityAgeable createChild(EntityAgeable p_90011_1_)
	{
		
		return new EntityJavaPowerMob(worldObj);
	}

	

}