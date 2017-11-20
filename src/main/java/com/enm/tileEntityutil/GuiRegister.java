package com.enm.tileEntityutil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;

public interface GuiRegister
{
	public Container GetContainer(InventoryPlayer pl, TileEntity te);
	public GuiContainer GetGuiContainer(InventoryPlayer pl, TileEntity te);
}
