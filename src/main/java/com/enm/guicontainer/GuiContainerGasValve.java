package com.enm.guicontainer;

import org.lwjgl.opengl.GL11;

import com.enm.api.util.Vector2;
import com.enm.container.ENMContainer;
import com.enm.guiutil.ResourceLocationRegister;
import com.enm.tileEntity.TileEntityGasValve;
import com.enm.util.Tools;
import com.enm.util.Zone2D;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiContainerGasValve extends GuiContainer
{
	TileEntityGasValve te_valve;
	GuiTextField customName;
	
	Vector2 mouseposition = new Vector2(0, 0);
	Zone2D zone_buttonOn, zone_buttonOff;
	int swp = 0;
	
	public GuiContainerGasValve(InventoryPlayer pl, TileEntityGasValve te)
	{
		super(new ENMContainer());
		te_valve = te;
		zone_buttonOn = new Zone2D(7, 31, 42, 48);
		zone_buttonOff = new Zone2D(7, 53, 42, 70);
		fontRendererObj = Minecraft.getMinecraft().fontRenderer;
		
		customName = new GuiTextField(fontRendererObj, 6, 83, 69, 12);
		customName.setMaxStringLength(13);
		customName.setText(te_valve.Custom_Name);
		customName.setEnableBackgroundDrawing(false);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float arg1, int arg2, int arg3)
	{
		xSize = 150;
		ySize = 100;
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 10.F);
        this.mc.renderEngine.bindTexture(ResourceLocationRegister.texture_gas_valve_gui);
        int x = width / 2 - 40;
        int y = height / 2 - 40;
        this.drawTexturedModalRect(x, y, 0, 0, 80, 112);
       
        if(te_valve.valve_position)
        {
        	//ON Close
        	this.drawTexturedModalRect(x + 8, y + 8, 80, 0, 10, 5);
        	
        	this.drawTexturedModalRect(x + 7, y + 31, 80, 10, 36, 18);
        	if(zone_buttonOff.MouseIsOnArea(mouseposition))
        	{
        		this.drawTexturedModalRect(x + 7, y + 53, 152, 28, 36, 18);
        	}
        	else
        	{
        		this.drawTexturedModalRect(x + 7, y + 53, 116, 28, 36, 18);
        	}
        }
        else
        {
        	//OFF Open
        	this.drawTexturedModalRect(x + 8, y + 8, 80, 5, 10, 5);
        	
        	this.drawTexturedModalRect(x + 7, y + 53, 80, 28, 36, 18);
        	if(zone_buttonOn.MouseIsOnArea(mouseposition))
        	{
        		this.drawTexturedModalRect(x + 7, y + 31, 152, 10, 36, 18);
        	}
        	else
        	{
        		this.drawTexturedModalRect(x + 7, y + 31, 116, 10, 36, 18);
        	}
        }
        
        customName.xPosition = x + 8;
        customName.yPosition = y + 85;
        customName.drawTextBox();
        
	}
	
	@Override
	protected void mouseClicked(int x, int y, int click)
	{
		int _x = x - (width/2 - 40);
		int _y = y - (height/2 - 40);
		if(zone_buttonOff.MouseIsOnArea(new Vector2(_x, _y)))
		{
			swp = -5;
		}
		if(zone_buttonOn.MouseIsOnArea(new Vector2(_x, _y)))
		{
			swp = 5;
		}
		
		customName.mouseClicked(x, y, click);
		te_valve.Custom_Name = customName.getText();
		te_valve.updateInventory();
		
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
				te_valve.Custom_Name = customName.getText();
				te_valve.updateInventory();
			}
		}
		
		if(id == 1 || !customName.isFocused())super.keyTyped(c, id);
	}
	
	@Override
	public void updateScreen()
	{
		customName.updateCursorCounter();
		Tools.GetMouseLocalisation(mouseposition, width, height, 80, 80);
		if(swp > 0)
		{
			--swp;
			te_valve.ValveOpenClose(true);
		}
		else if(swp < 0)
		{
			++swp;
			te_valve.ValveOpenClose(false);
		}
		super.updateScreen();
	}

}
