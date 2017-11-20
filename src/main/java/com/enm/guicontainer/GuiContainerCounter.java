package com.enm.guicontainer;

import org.lwjgl.opengl.GL11;

import com.enm.container.ENMContainer;
import com.enm.core.CoreMod;
import com.enm.guiutil.ResourceLocationRegister;
import com.enm.network.NetWorkTileEntityNBT;
import com.enm.tileEntity.TileEntityCounter;
import com.enm.util.Tools;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class GuiContainerCounter extends GuiContainer
{
	TileEntityCounter te_Counter;
	GuiTextField customName;
	GuiButton reset;
	boolean isSingleplayer = Minecraft.getMinecraft().isSingleplayer();

	public GuiContainerCounter(InventoryPlayer pl, TileEntityCounter te)
	{
		super(new ENMContainer());
		te_Counter = te;
		fontRendererObj = Minecraft.getMinecraft().fontRenderer;
		
		customName = new GuiTextField(fontRendererObj, 6, 83, 69, 12);
		customName.setMaxStringLength(13);
		customName.setText(te_Counter.Custom_Name);
		customName.setEnableBackgroundDrawing(false);
		
		reset = new GuiButton(0, 0, 0, 60, 20, "Reset");
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 10.F);
        this.mc.renderEngine.bindTexture(ResourceLocationRegister.texture_fluxmetter_gui);
        int x = width / 2 - 50;
        int y = height / 2 - 33;
        this.drawTexturedModalRect(x, y, 80, 0, 100, 66);
        drawString(fontRendererObj, te_Counter.GetSyntaxe(), x + 8, y + 10, 0xaaaaaa);
        
        reset.xPosition = x + 8;
        reset.yPosition = y + 23;
        buttonList.clear();
        buttonList.add(reset);
        
        customName.xPosition = x + 8;
        customName.yPosition = y + 48;
        customName.drawTextBox();
	}
	
	@Override
	protected void mouseClicked(int x, int y, int click)
	{
		customName.mouseClicked(x, y, click);
		te_Counter.Custom_Name = customName.getText();
		te_Counter.updateInventory();
		
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
				te_Counter.Custom_Name = customName.getText();
				te_Counter.updateInventory();
			}
		}
		
		if(id == 1 || !customName.isFocused())super.keyTyped(c, id);
	}
	
	@Override
	protected void actionPerformed(GuiButton b)
	{
		if(b.id == 0)
		{
			te_Counter.Reset();
		}
	}
	
	@Override
	public void updateScreen()
	{
		customName.updateCursorCounter();
		super.updateScreen();
	}
}