package com.enm.guicontainer;

import com.enm.container.ENMContainer;
import com.enm.tileEntity.TileEntityControlPanel;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class GuiContainerControlPanel extends GuiContainer
{

	public GuiContainerControlPanel(InventoryPlayer pl, TileEntityControlPanel te)
	{
		super(new ENMContainer());
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		// TODO Auto-generated method stub

	}

}
