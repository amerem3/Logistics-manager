package com.enm.guicontainer;

import org.lwjgl.opengl.GL11;

import com.enm.api.util.Vector2;
import com.enm.container.ENMContainer;
import com.enm.core.CoreMod;
import com.enm.guiutil.ResourceLocationRegister;
import com.enm.network.NetWorkTileEntityNBT;
import com.enm.tileEntity.TileEntityRedstoneSwitch;
import com.enm.tileEntity.TileEntitySwitch;
import com.enm.util.Tools;
import com.enm.util.Zone2D;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class GuiContainerRedstoneSwitch extends GuiContainer {

	public TileEntityRedstoneSwitch te_switch;
	GuiTextField customName;
	
	Vector2 mouseposition = new Vector2(0, 0);
	Zone2D zone_buttonOn, zone_buttonOff;
	int swp = 0;
	
	public GuiContainerRedstoneSwitch(InventoryPlayer pl, TileEntityRedstoneSwitch te)
	{
		super(new ENMContainer());
		te_switch = te;
		zone_buttonOn = new Zone2D(7, 31, 42, 48);
		zone_buttonOff = new Zone2D(7, 53, 42, 70);
		fontRendererObj = Minecraft.getMinecraft().fontRenderer;
		
		customName = new GuiTextField(fontRendererObj, 6, 83, 69, 12);
		customName.setMaxStringLength(13);
		customName.setText(te_switch.Custom_Name);
		customName.setEnableBackgroundDrawing(false);
	}
	
	@Override
	public void initGui()
	{
		NBTTagCompound data_nbt = new NBTTagCompound();
		data_nbt.setInteger("bk01", 6);
		data_nbt.setString("player", mc.thePlayer.getDisplayName());
		CoreMod.network_TileEntityNBT.sendToServer(new NetWorkTileEntityNBT(te_switch.xCoord, te_switch.yCoord, te_switch.zCoord, data_nbt));	
		
		super.initGui();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float arg1, int arg2, int arg3)
	{
		xSize = 150;
		ySize = 100;
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 10.F);
        this.mc.renderEngine.bindTexture(ResourceLocationRegister.texture_rsswitch_gui);
        int x = width / 2 - 40;
        int y = height / 2 - 40;
        this.drawTexturedModalRect(x, y, 0, 0, 80, 112);
       
        if(te_switch.switch_position)
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
		te_switch.Custom_Name = customName.getText();
		te_switch.updateInventory();
		
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
				te_switch.Custom_Name = customName.getText();
				te_switch.updateInventory();
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
			te_switch.SwitchONOFF(true);
		}
		else if(swp < 0)
		{
			++swp;
			te_switch.SwitchONOFF(false);
		}
		super.updateScreen();
	}

}
