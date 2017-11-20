package com.enm.guicontainer;

import org.lwjgl.opengl.GL11;

import com.enm.container.ENMContainer;
import com.enm.guiutil.ResourceLocationRegister;
import com.enm.tileEntity.TileEntityFluxMeter;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiContainerFluxMeter extends GuiContainer
{
	TileEntityFluxMeter te_fluxmeter;
	GuiTextField customName;

	public GuiContainerFluxMeter(InventoryPlayer pl, TileEntityFluxMeter te)
	{
		super(new ENMContainer());
		te_fluxmeter = te;
		fontRendererObj = Minecraft.getMinecraft().fontRenderer;
		
		customName = new GuiTextField(fontRendererObj, 6, 83, 69, 12);
		customName.setMaxStringLength(13);
		customName.setText(te_fluxmeter.Custom_Name);
		customName.setEnableBackgroundDrawing(false);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 10.F);
        this.mc.renderEngine.bindTexture(ResourceLocationRegister.texture_fluxmetter_gui);
        int x = width / 2 - 40;
        int y = height / 2 - 26;
        this.drawTexturedModalRect(x, y, 0, 0, 80, 52);
        drawString(fontRendererObj, te_fluxmeter.GetFlux()+" RF/t", x + 8, y + 10, 0xaaaaaa);
        
        customName.xPosition = x + 8;
        customName.yPosition = y + 34;
        customName.drawTextBox();
	}
	
	@Override
	protected void mouseClicked(int x, int y, int click)
	{
		customName.mouseClicked(x, y, click);
		te_fluxmeter.Custom_Name = customName.getText();
		te_fluxmeter.updateInventory();
		
		super.mouseClicked(x, y, click);
	}
	
	@Override
	protected void keyTyped(char c, int id)
	{
		customName.textboxKeyTyped(c, id);
		if(id == 28)
		{
			if(customName.isFocused())
			{
				customName.setFocused(false);
				te_fluxmeter.Custom_Name = customName.getText();
				te_fluxmeter.updateInventory();
			}
		}
		
		if(id == 1 || !customName.isFocused())super.keyTyped(c, id);
	}
	
	@Override
	public void updateScreen()
	{
		customName.updateCursorCounter();
		super.updateScreen();
	}

}
