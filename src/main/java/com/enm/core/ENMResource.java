package com.enm.core;

import java.lang.reflect.Field;

import com.enm.block.util.BlockENM;
import com.enm.item.util.ItemENM;
import com.enm.item.util.ItemIdentity;
import com.enm.register.RBlocks;
import com.enm.tileEntityutil.ISR;
import com.enm.tileEntityutil.ModDependence;
import com.enm.tileEntityutil.TSR;
import com.enm.util.ItemsFilter;
import com.enm.util.Tools;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;

public class ENMResource
{
	public static ItemsFilter upgrade_item_filter = new ItemsFilter();
	
	public static CreativeTabs ENMCreativeTab = new CreativeTabs("Networks Manager")
	{

	    @Override
	    @SideOnly(Side.CLIENT)
	    public Item getTabIconItem(){
	        ItemStack iStack = new ItemStack(RBlocks.Block_RFTerminal);
	        return iStack.getItem();
	    }
	    
	    @Override
	    public String getTabLabel()
	    {
	    	return "ENM";
	    }
	};
	
	public ENMResource()
	{
		Registry();
	}
	
	public void Registry()
	{
		Class blocksregister = com.enm.register.RBlocks.class;
		Class itemsregister = com.enm.register.RItems.class;
		
		//Blocks register
		for(Field var_block : blocksregister.getFields())
		{
			if(var_block.getType() == BlockENM.class)
			{
				try
				{
					BlockENM block = (BlockENM) var_block.get(null);
					boolean lo = true;
					if(block instanceof ModDependence)
					{
						if(((ModDependence)block).ModsDependence() != null)
						for(String s :((ModDependence)block).ModsDependence())
						{
							if(!Loader.isModLoaded(s))lo = false;
						}
					}
					if(lo)
					{
						block.Register(block);
						TileEntity te = block.createNewTileEntity(null, 0);
						if(te != null)
						{
							if(block instanceof TSR && Tools.isClient())
							{
								TSR sr = (TSR) block;
								ClientRegistry.registerTileEntity(te.getClass(), te.getClass().getSimpleName(), sr.TESpecialRenderer());
							}
							else
							{
								GameRegistry.registerTileEntity(te.getClass(), te.getClass().getSimpleName());
							}
							
							if(block instanceof ISR && Tools.isClient())
							{
								ISR ir = (ISR) block;
								MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(block), ir.ItemRender());
							}
						}
					}
				}
				catch (IllegalArgumentException e)
				{
					e.printStackTrace();
				}
				catch (IllegalAccessException e)
				{
					e.printStackTrace();
				}
				
			}
			/*else
			{
				if(var_block.getType() == Block.class)
				{
					try
					{
						Block block = (Block) var_block.get(null);
					}
					catch (IllegalArgumentException | IllegalAccessException e)
					{
						e.printStackTrace();
					}
				}
			}*/
		}
		
		//Items register
		for(Field var_item : itemsregister.getFields())
		{
			if(var_item.getType() == ItemENM.class)
			{
				try
				{
					ItemENM item = (ItemENM) var_item.get(null);
					boolean lo = true;
					if(item instanceof ModDependence)
					{
						if(((ModDependence)item).ModsDependence() != null)
						for(String s :((ModDependence)item).ModsDependence())
						{
							if(!Loader.isModLoaded(s))lo = false;
						}
					}
					if(lo)
					{
						item.Register(item);
						
						if(Tools.isClient() && item instanceof ISR)
						{
							MinecraftForgeClient.registerItemRenderer(item, ((ISR)item).ItemRender());
						}
					}
				}
				catch (IllegalAccessException e)
				{
					e.printStackTrace();
				}
				catch (IllegalArgumentException e)
				{
					e.printStackTrace();
				}
				
			}
			else if(var_item.getType() == ItemBlock.class || var_item.getType() == Item.class)
			{
				try
				{
					Item item = (Item) var_item.get(null);
					boolean lo = true;
					if(item instanceof ModDependence)
					{
						if(((ModDependence)item).ModsDependence() != null)
						for(String s :((ModDependence)item).ModsDependence())
						{
							if(!Loader.isModLoaded(s))lo = false;
						}
					}
					if(lo)
					{
						if(item instanceof ItemIdentity)
						{
							GameRegistry.registerItem(item, ((ItemIdentity)item).ItemClassName());
							LanguageRegistry.addName(item, ((ItemIdentity)item).DisplayItemName());
						}
						if(Tools.isClient() && item instanceof ISR)
						{
							MinecraftForgeClient.registerItemRenderer(item, ((ISR)item).ItemRender());
						}
					}
				}
				catch (IllegalArgumentException e)
				{
					e.printStackTrace();
				}
				catch (IllegalAccessException e)
				{
					e.printStackTrace();
				}
				
			}
		}
		
		//upgrade_item_filter.addItemOnWhiteList(RItems.Item_AccelerationCard);
	}
}
