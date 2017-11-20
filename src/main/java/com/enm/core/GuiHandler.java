package com.enm.core;

import com.enm.tileEntityutil.GuiRegister;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler
{

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		Block block = world.getBlock(x, y, z);
		
		if(block instanceof GuiRegister)
		{
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			return ((GuiRegister)block).GetGuiContainer(player.inventory, tileEntity);
		}
		/*if(tileEntity instanceof TileEntitySolderingStation) return new GuiContainerSolderingStation(player.inventory, (TileEntitySolderingStation)tileEntity);
		if(tileEntity instanceof TileEntityMaterialFormater) return new GuiContainerMaterialFormater(player.inventory, (TileEntityMaterialFormater)tileEntity);
		*/
		return null;
	}

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		Block block = world.getBlock(x, y, z);
		
		if(block instanceof GuiRegister)
		{
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			return ((GuiRegister)block).GetContainer(player.inventory, tileEntity);
		}
		/*if(tileEntity instanceof TileEntitySolderingStation) return new ContainerSolderingStation(player.inventory, (TileEntitySolderingStation)tileEntity);
		if(tileEntity instanceof TileEntityMaterialFormater) return new ContainerMaterialFormater(player.inventory, (TileEntityMaterialFormater)tileEntity);
		*/
		return null;
	}

}
