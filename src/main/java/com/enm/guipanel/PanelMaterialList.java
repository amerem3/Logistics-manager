package com.enm.guipanel;

import java.util.List;

import com.enm.api.network.MachineNetWork;
import com.enm.api.network.Machine_Counter;
import com.enm.api.network.Machine_FluxMeter;
import com.enm.api.network.Machine_StorageInfo;
import com.enm.api.network.Machine_Switch;
import com.enm.api.network.NetWorkUtil;
import com.enm.guicontainer.GuiContainerTerminal;
import com.enm.guiutil.ActionPerformed;
import com.enm.guiutil.GuiButtonNB;
import com.enm.guiutil.ResourceLocationRegister;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.tileentity.TileEntity;

public class PanelMaterialList implements GuiPanel, ActionPerformed
{
	FontRenderer fontRendererObj = Minecraft.getMinecraft().fontRenderer;
	int page = 0, mpage = 0, epage = 0;
	List<TileEntity> machines;
	GuiContainerTerminal gui_terminal;
	GuiButtonNB button_next, button_back;
	
	@Override
	public void init(GuiContainer terminal)
	{
		gui_terminal = (GuiContainerTerminal) terminal;
		setupmaterial();
	}
	
	public void setupmaterial()
	{
		gui_terminal.getlistbutton().clear();
		page = 0;
		machines = NetWorkUtil.GetAllMachines(gui_terminal.tertminal_te.getWorldObj(), gui_terminal.tertminal_te.xCoord, gui_terminal.tertminal_te.yCoord, gui_terminal.tertminal_te.zCoord);
		//50 160
		float float_maxX = (((float)gui_terminal.width)-50f)/160f;
		float float_maxY = (((float)gui_terminal.height)-100f)/50f;
		
		if((float_maxY - (int)float_maxY) > 0.1)float_maxY = (int)float_maxY +1;
		epage = ((int)float_maxX)*((int)float_maxY);
		if(epage <= 0)epage = 1;
		float float_mpage = machines.size()/epage;
		
		if(float_mpage > ((int)float_mpage))mpage = ((int)float_mpage)+1; else mpage = ((int)float_mpage);
		if(mpage > 0)
		{
			button_next = new GuiButtonNB(1, ((gui_terminal.width)/2) +100, gui_terminal.height -58, 0);
			button_back = new GuiButtonNB(0, ((gui_terminal.width)/2) -140, gui_terminal.height -58, 1);
			gui_terminal.addbutton(button_next);
			gui_terminal.addbutton(button_back);
		}
	}

	@Override
	public void draw()
	{
		if(mpage > 0)
		{
			gui_terminal.drawString(fontRendererObj, "Page: "+(page+1)+"/"+(mpage+1), (gui_terminal.width)/2 - 20, gui_terminal.height - 40, 0xffffff);
		}
		if(machines != null)
		{
			int x2 = 10, y2 = 10, id = 0;
			for(TileEntity tem : machines)
			{
				if(id/epage == page)
				{
					boolean enforthis = true;
					if(tem instanceof Machine_Switch)
					{
						gui_terminal.mc.renderEngine.bindTexture(ResourceLocationRegister.texture_pixel255);
						gui_terminal.setColorRGBA(128, 0, 0, 255);
						gui_terminal.drawTexturedModalRect(x2, 25+y2, 0, 0, 150, 40);
						gui_terminal.drawString(fontRendererObj, "Type: "+((Machine_Switch)tem).name(), x2+1, y2+26, 0xffffff);
						gui_terminal.drawString(fontRendererObj, "CustomName: "+((Machine_Switch)tem).CustomName(), x2+1, y2+36, 0xffffff);
						gui_terminal.drawString(fontRendererObj, "Pos: ["+tem.xCoord+","+tem.yCoord+","+tem.zCoord+"]", x2+1, y2+46, 0xffffff);
						gui_terminal.drawString(fontRendererObj, ((Machine_Switch)tem).GetSyntaxe(), x2+1, y2+56, 0xffffff);
						
						if(y2 + 50 > gui_terminal.height - 100)
						{
							y2 = 10;
							x2 += 160;
						}
						else y2 += 50;
						enforthis = false;
					}
					
					if(tem instanceof Machine_Counter)
					{
						gui_terminal.mc.renderEngine.bindTexture(ResourceLocationRegister.texture_pixel255);
						gui_terminal.setColorRGBA(0, 128, 0, 255);
						gui_terminal.drawTexturedModalRect(x2, 25+y2, 0, 0, 150, 40);
						gui_terminal.drawString(fontRendererObj, "Type: "+((MachineNetWork)tem).name(), x2+1, y2+26, 0xffffff);
						gui_terminal.drawString(fontRendererObj, "CustomName: "+((MachineNetWork)tem).CustomName(), x2+1, y2+36, 0xffffff);
						gui_terminal.drawString(fontRendererObj, "Pos: ["+tem.xCoord+","+tem.yCoord+","+tem.zCoord+"]", x2+1, y2+46, 0xffffff);
						gui_terminal.drawString(fontRendererObj, ((Machine_Counter)tem).GetSyntaxe(), x2+1, y2+56, 0xffffff);
						
						if(y2 + 50 > gui_terminal.height - 100)
						{
							y2 = 10;
							x2 += 160;
						}
						else y2 += 50;
						enforthis = false;
					}
					 
					if(tem instanceof Machine_FluxMeter)
					{
						gui_terminal.mc.renderEngine.bindTexture(ResourceLocationRegister.texture_pixel255);
						gui_terminal.setColorRGBA(0, 0, 128, 255);
						gui_terminal.drawTexturedModalRect(x2, 25+y2, 0, 0, 150, 40);
						gui_terminal.drawString(fontRendererObj, "Type: "+((MachineNetWork)tem).name(), x2+1, y2+26, 0xffffff);
						gui_terminal.drawString(fontRendererObj, "CustomName: "+((MachineNetWork)tem).CustomName(), x2+1, y2+36, 0xffffff);
						gui_terminal.drawString(fontRendererObj, "Pos: ["+tem.xCoord+","+tem.yCoord+","+tem.zCoord+"]", x2+1, y2+46, 0xffffff);
						gui_terminal.drawString(fontRendererObj, ((Machine_FluxMeter)tem).GetSyntaxe(), x2+1, y2+56, 0xffffff);
						
						if(y2 + 50 > gui_terminal.height - 100)
						{
							y2 = 10;
							x2 += 160;
						}
						else y2 += 50;
						enforthis = false;
					}
					
					if(tem instanceof Machine_StorageInfo)
					{
						gui_terminal.mc.renderEngine.bindTexture(ResourceLocationRegister.texture_pixel255);
						gui_terminal.setColorRGBA(128, 0, 128, 255);
						gui_terminal.drawTexturedModalRect(x2, 25+y2, 0, 0, 150, 40);
						gui_terminal.drawString(fontRendererObj, "Type: "+((MachineNetWork)tem).name(), x2+1, y2+26, 0xffffff);
						gui_terminal.drawString(fontRendererObj, "CustomName: "+((MachineNetWork)tem).CustomName(), x2+1, y2+36, 0xffffff);
						gui_terminal.drawString(fontRendererObj, "Pos: ["+tem.xCoord+","+tem.yCoord+","+tem.zCoord+"]", x2+1, y2+46, 0xffffff);
						gui_terminal.drawString(fontRendererObj, ((Machine_StorageInfo)tem).GetSyntaxe(), x2+1, y2+56, 0xffffff);
						
						if(y2 + 50 > gui_terminal.height - 100)
						{
							y2 = 10;
							x2 += 160;
						}
						else y2 += 50;
						enforthis = false;
					}
					
					if(tem instanceof MachineNetWork && enforthis)
					{
						gui_terminal.mc.renderEngine.bindTexture(ResourceLocationRegister.texture_pixel255);
						gui_terminal.setColorRGBA(128, 128, 0, 255);
						gui_terminal.drawTexturedModalRect(x2, 25+y2, 0, 0, 150, 40);
						gui_terminal.drawString(fontRendererObj, "Type: "+((MachineNetWork)tem).name(), x2+1, y2+26, 0xffffff);
						gui_terminal.drawString(fontRendererObj, "CustomName: "+((MachineNetWork)tem).CustomName(), x2+1, y2+36, 0xffffff);
						gui_terminal.drawString(fontRendererObj, "Pos: ["+tem.xCoord+","+tem.yCoord+","+tem.zCoord+"]", x2+1, y2+46, 0xffffff);
						
						if(y2 + 50 > gui_terminal.height - 100)
						{
							y2 = 10;
							x2 += 160;
						}
						else y2 += 50;
					}
					//++id;
				}
				++id;
			}
		}
	}

	@Override
	public void eventMouse(int x, int y, int click)
	{
		
	}

	@Override
	public void eventKeyBoard(char c, int cid)
	{
		
	}

	@Override
	public void screenResized(int width, int height)
	{
		setupmaterial();
	}

	@Override
	public void update()
	{
		
	}
	
	@Override
	public void panelClose()
	{
		gui_terminal.getlistbutton().clear();
	}
	
	/*public String ONOFF(boolean state)
	{
		return state ? "ON" : "OFF";
	}*/

	@Override
	public void actionPerformed(GuiButton button)
	{
		if(button.id == 1)
		{
			if(page < mpage)
			{
				++page;
			}
			else
			{
				page = 0;
			}
		}
		else if(button.id == 0)
		{
			if(page > 0)
			{
				--page;
			}
			else
			{
				page = mpage;
			}
		}
	}
	
}
