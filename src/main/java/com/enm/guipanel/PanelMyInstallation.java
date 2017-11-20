package com.enm.guipanel;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;

import com.enm.api.network.MachineNetWork;
import com.enm.api.network.Machine_Counter;
import com.enm.api.network.Machine_FluxMeter;
import com.enm.api.network.Machine_StorageInfo;
import com.enm.api.network.Machine_Switch;
import com.enm.api.network.NetWorkUtil;
import com.enm.api.util.Vector2;
import com.enm.api.util.Vector3;
import com.enm.core.Config;
import com.enm.guicontainer.GuiContainerTerminal;
import com.enm.guipanel.myinstallation.Map;
import com.enm.guipanel.myinstallation.StringTag;
import com.enm.guipanel.myinstallation.Tag;
import com.enm.guipanel.myinstallation.Tag.Type;
import com.enm.guiutil.GuiButtonTerminal;
import com.enm.guiutil.ResourceLocationRegister;
import com.enm.util.Tools;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

public class PanelMyInstallation implements GuiPanel
{
	FontRenderer fontRendererObj = Minecraft.getMinecraft().fontRenderer;
	GuiContainerTerminal gui_terminal;
	public Map map;
	List<TileEntity> machines;
	public Tag editlink;
	
	boolean fluxmAV, counterAV, switchAV, storageAV;
	int fluxmC, counterC, switchC, storageC, comentC;
	
	@Override
	public void init(GuiContainer terminal)
	{
		gui_terminal = (GuiContainerTerminal) terminal;
		map = new Map(false);
		
		machines = NetWorkUtil.GetAllMachines(gui_terminal.tertminal_te.getWorldObj(), gui_terminal.tertminal_te.xCoord, gui_terminal.tertminal_te.yCoord, gui_terminal.tertminal_te.zCoord);
		
		//gui_terminal.tertminal_te.guiinfo.map_pos.x = 51;
		//gui_terminal.tertminal_te.guiinfo.map_pos.y = 51;
		
		map.loadconf(gui_terminal.tertminal_te);
		
		fluxmAV = Config.conf.getBoolean("FluxMetter info label is always visible", "general", false, "");
		counterAV = Config.conf.getBoolean("Counter info label is always visible", "general", false, "");
		switchAV = Config.conf.getBoolean("Switch info label is always visible", "general", false, "");
		storageAV = Config.conf.getBoolean("Storage Info info label is always visible", "general", false, "");
		//onPourcent = Config.conf.getBoolean("storage info on percent", "general", false, "");
		
		fluxmC = Tools.HTMLColorStringToInt(Config.conf.getString("FluxMetter info label Color", "general", "0xFFFF00", ""));
		counterC = Tools.HTMLColorStringToInt(Config.conf.getString("Counter info label Color", "general", "0xFFFF00", ""));
		switchC = Tools.HTMLColorStringToInt(Config.conf.getString("Switch info label Color", "general", "0xFFFF00", ""));
		storageC = Tools.HTMLColorStringToInt(Config.conf.getString("Storage Info info label Color", "general", "0xFFFF00", ""));
		comentC = Tools.HTMLColorStringToInt(Config.conf.getString("Text label Color", "general", "0xFFFF00", ""));
		//map.textTags.removeAll();
		//map.textTags.add(new StringTag("coucou", new Vector2(0, 0)));
		//GuiButtonTerminal button_conf1 = new GuiButtonTerminal(10, 0, 0, GuiButtonTerminal.Icon.Down);
		//GuiButtonTerminal button_conf2 = new GuiButtonTerminal(11, 0, 0, GuiButtonTerminal.Icon.Up);
		//GuiButtonTerminal button_confOK = new GuiButtonTerminal(12, 0, 0, GuiButtonTerminal.Icon.Ok);
		
	}

	@Override
	public void draw()
	{
		gui_terminal.mc.renderEngine.bindTexture(ResourceLocationRegister.texture_guitool2);
		for(int m_x = 0; m_x < map.map.length; ++m_x)
		{
			for(int m_y = 0; m_y < map.map[0].length; ++m_y)
			{	
				if(map.map[m_x][m_y] > 0 || gui_terminal.tertminal_te.guiinfo.editmode)
				{
					if(m_x == mouse_mappos.x && m_y == mouse_mappos.y)//select item
					{
						gui_terminal.setColorRGBA(255, 0, 0, 255);
					}
					else
					{
						gui_terminal.setColorRGBA(255, 255, 255, 255);
					}
					
					if(editlink != null && m_x ==  editlink.mappos.x && m_y == editlink.mappos.y)
					{
						gui_terminal.setColorRGBA(255, 200, 0, 255);
					}
						Map.Icon icon = Map.Icon.values()[map.map[m_x][m_y]];
						int x = icon.x, y = icon.y, startx = icon.startx, starty = icon.starty, endx = icon.endx, endy = icon.endy;
						
						if(startx > 0 && IsOnMap(m_x -1, m_y) && getIcon(m_x -1, m_y).endx < 20) startx = 0;
						if(starty > 0 && IsOnMap(m_x, m_y -1) && getIcon(m_x, m_y -1).endy < 20) starty = 0;
						if(endx < 20 && IsOnMap(m_x +1, m_y) && getIcon(m_x +1, m_y).startx > 0) endx = 20;
						if(endy < 20 && IsOnMap(m_x, m_y +1) && getIcon(m_x, m_y +1).starty > 0) endy = 20;
						
						gui_terminal.drawTexturedModalRect(m_x*20 + gui_terminal.tertminal_te.guiinfo.map_pos.x + startx, m_y*20 + gui_terminal.tertminal_te.guiinfo.map_pos.y + starty, (20*x) + startx, (20*y) + starty, endx - startx, endy - starty);
				}
			}
		}
		//draw label
		
		for(int m_x = 0; m_x < map.map.length; ++m_x)
		{
			for(int m_y = 0; m_y < map.map[0].length; ++m_y)
			{	
				if(map.map[m_x][m_y] > 0 && !gui_terminal.tertminal_te.guiinfo.editmode)
				{
					if(map.map[m_x][m_y] >= 6 && map.map[m_x][m_y] <= 11)//Switch
					{
						if(!switchAV)
						{
							if( m_x == mouse_mappos.x && m_y == mouse_mappos.y)//select item
							{
								if(map.maplink[m_x][m_y] != null)
								{
									TileEntity te_r = null;
									for(TileEntity te : machines)
									{
										if(te.xCoord == map.maplink[m_x][m_y].x && te.yCoord == map.maplink[m_x][m_y].y && te.zCoord == map.maplink[m_x][m_y].z)
										te_r = te;
									}
									if(te_r != null && te_r instanceof Machine_Switch)
									{
										gui_terminal.drawString(fontRendererObj, ((Machine_Switch)te_r).GetSyntaxe(), m_x*20 + gui_terminal.tertminal_te.guiinfo.map_pos.x+ 20, m_y*20 + gui_terminal.tertminal_te.guiinfo.map_pos.y + 20, switchC);
									}
								}
							}
						}
						else
						{
							if(map.maplink[m_x][m_y] != null)
							{
								TileEntity te_r = null;
								for(TileEntity te : machines)
								{
									if(te.xCoord == map.maplink[m_x][m_y].x && te.yCoord == map.maplink[m_x][m_y].y && te.zCoord == map.maplink[m_x][m_y].z)
									te_r = te;
								}
								if(te_r != null && te_r instanceof Machine_Switch)
								{
									gui_terminal.drawString(fontRendererObj,((Machine_Switch)te_r).GetSyntaxe(), m_x*20 + gui_terminal.tertminal_te.guiinfo.map_pos.x+ 20, m_y*20 + gui_terminal.tertminal_te.guiinfo.map_pos.y + 20, switchC);
								}
							}
						}
					}
					else if(map.map[m_x][m_y] == 5)// flux meter
					{
						if(!fluxmAV)
						{
							if(m_x == mouse_mappos.x && m_y == mouse_mappos.y)//select item
							{
								if(map.maplink[m_x][m_y] != null)
								{
									TileEntity te_r = null;
									for(TileEntity te : machines)
									{
										if(te.xCoord == map.maplink[m_x][m_y].x && te.yCoord == map.maplink[m_x][m_y].y && te.zCoord == map.maplink[m_x][m_y].z)
										te_r = te;
									}
									if(te_r != null && te_r instanceof Machine_FluxMeter)
									{
										gui_terminal.drawString(fontRendererObj, ((Machine_FluxMeter)te_r).GetSyntaxe(), m_x*20 + gui_terminal.tertminal_te.guiinfo.map_pos.x+ 20, m_y*20 + gui_terminal.tertminal_te.guiinfo.map_pos.y + 20, fluxmC);
									}
								}
							}
						}
						else
						{
							if(map.maplink[m_x][m_y] != null)
							{
								TileEntity te_r = null;
								for(TileEntity te : machines)
								{
									if(te.xCoord == map.maplink[m_x][m_y].x && te.yCoord == map.maplink[m_x][m_y].y && te.zCoord == map.maplink[m_x][m_y].z)
									te_r = te;
								}
								if(te_r != null && te_r instanceof Machine_FluxMeter)
								{
									gui_terminal.drawString(fontRendererObj, ((Machine_FluxMeter)te_r).GetSyntaxe(), m_x*20 + gui_terminal.tertminal_te.guiinfo.map_pos.x+ 20, m_y*20 + gui_terminal.tertminal_te.guiinfo.map_pos.y + 20, fluxmC);
								}
							}
						}
					}
					else if(map.map[m_x][m_y] == 4)// Counter
					{
						if(!counterAV)
						{	
							if(m_x == mouse_mappos.x && m_y == mouse_mappos.y)//select item
							{
								if(map.maplink[m_x][m_y] != null)
								{
									TileEntity te_r = null;
									for(TileEntity te : machines)
									{
										if(te.xCoord == map.maplink[m_x][m_y].x && te.yCoord == map.maplink[m_x][m_y].y && te.zCoord == map.maplink[m_x][m_y].z)
										te_r = te;
									}
									if(te_r != null && te_r instanceof Machine_Counter)
									{
										gui_terminal.drawString(fontRendererObj, ((Machine_Counter)te_r).GetSyntaxe(), m_x*20 + gui_terminal.tertminal_te.guiinfo.map_pos.x+ 20, m_y*20 + gui_terminal.tertminal_te.guiinfo.map_pos.y + 20, counterC);
									}
								}
							}
						}
						else
						{
							if(map.maplink[m_x][m_y] != null)
							{
								TileEntity te_r = null;
								for(TileEntity te : machines)
								{
									if(te.xCoord == map.maplink[m_x][m_y].x && te.yCoord == map.maplink[m_x][m_y].y && te.zCoord == map.maplink[m_x][m_y].z)
									te_r = te;
								}
								if(te_r != null && te_r instanceof Machine_Counter)
								{
									gui_terminal.drawString(fontRendererObj, ((Machine_Counter)te_r).GetSyntaxe(), m_x*20 + gui_terminal.tertminal_te.guiinfo.map_pos.x+ 20, m_y*20 + gui_terminal.tertminal_te.guiinfo.map_pos.y + 20, counterC);
								}
							}
						}
					}
					else if(map.map[m_x][m_y] == 3)// Counter
					{
						if(!storageAV)
						{	
							if(m_x == mouse_mappos.x && m_y == mouse_mappos.y)//select item
							{
								if(map.maplink[m_x][m_y] != null)
								{
									TileEntity te_r = null;
									for(TileEntity te : machines)
									{
										if(te.xCoord == map.maplink[m_x][m_y].x && te.yCoord == map.maplink[m_x][m_y].y && te.zCoord == map.maplink[m_x][m_y].z)
										te_r = te;
									}
									if(te_r != null && te_r instanceof Machine_StorageInfo)
									{
										gui_terminal.drawString(fontRendererObj, ((Machine_StorageInfo)te_r).GetSyntaxe(), m_x*20 + gui_terminal.tertminal_te.guiinfo.map_pos.x+ 20, m_y*20 + gui_terminal.tertminal_te.guiinfo.map_pos.y + 20, storageC);
									}
								}
							}
						}
						else
						{
							if(map.maplink[m_x][m_y] != null)
							{
								TileEntity te_r = null;
								for(TileEntity te : machines)
								{
									if(te.xCoord == map.maplink[m_x][m_y].x && te.yCoord == map.maplink[m_x][m_y].y && te.zCoord == map.maplink[m_x][m_y].z)
									te_r = te;
								}
								if(te_r != null && te_r instanceof Machine_StorageInfo)
								{
									gui_terminal.drawString(fontRendererObj, ((Machine_StorageInfo)te_r).GetSyntaxe(), m_x*20 + gui_terminal.tertminal_te.guiinfo.map_pos.x+ 20, m_y*20 + gui_terminal.tertminal_te.guiinfo.map_pos.y + 20, storageC);
								}
							}
						}
					}
				}
			}
		}
		
		if(gui_terminal.tertminal_te.guiinfo.editmode)
		{
			//TODO edit draw
			gui_terminal.mc.renderEngine.bindTexture(ResourceLocationRegister.texture_terminal_icon);
			gui_terminal.setColorRGBA(255, 255, 255, 255);
			gui_terminal.drawTexturedModalRect(gui_terminal.width - 50, 28, 40, 200, 40, 20);
			gui_terminal.drawTexturedModalRect(gui_terminal.width - 50, 92, 0, 200, 40, 20);
			gui_terminal.mc.renderEngine.bindTexture(ResourceLocationRegister.texture_pixel255);
			gui_terminal.setColorRGBA(0, 148, 255, 200);
			gui_terminal.drawTexturedModalRect(gui_terminal.width - 50, 50, 0, 0, 40, 40);
			gui_terminal.mc.renderEngine.bindTexture(ResourceLocationRegister.texture_guitool2);
			gui_terminal.setColorRGBA(255, 255, 255, 255);
			gui_terminal.drawTexturedModalRect(gui_terminal.width - 40, 60, 20*Map.Icon.values()[gui_terminal.tertminal_te.guiinfo.itemselected].x, 20*Map.Icon.values()[gui_terminal.tertminal_te.guiinfo.itemselected].y, 20, 20);
		}
		
		if(editlink != null)
		{
			gui_terminal.mc.renderEngine.bindTexture(ResourceLocationRegister.texture_terminal_icon);
			gui_terminal.setColorRGBA(255, 255, 255, 255);
			gui_terminal.drawTexturedModalRect(gui_terminal.width/2 - 20, gui_terminal.height/2 - 62, 40, 200, 40, 20);
			gui_terminal.drawTexturedModalRect(gui_terminal.width/2 - 20, gui_terminal.height/2 + 42, 0, 200, 40, 20);
			gui_terminal.drawTexturedModalRect(gui_terminal.width/2 + 98, gui_terminal.height/2 - 10, 80, 200, 40, 20);
			
			
			
			TileEntity tem = null;
			int x2 = gui_terminal.width/2 - 75;
			int y2 = gui_terminal.height/2 - 20;
			
			for(TileEntity te : machines)
			{
				if(te.xCoord == editlink.args.x && te.yCoord == editlink.args.y && te.zCoord == editlink.args.z)
				{
					tem = te;
					break;
				}
			}
			gui_terminal.mc.renderEngine.bindTexture(ResourceLocationRegister.texture_pixel255);
			gui_terminal.setColorRGBA(0, 148, 255, 200);
			gui_terminal.drawTexturedModalRect(gui_terminal.width/2 - 95, gui_terminal.height/2 - 40, 0, 0, 190, 80);
			if(editlink.type == Type.StringTag && editlink.mappos != null)//TODO 2
			{
				StringTag t = map.textTags.getByPos(editlink.mappos);
				gui_terminal.mc.renderEngine.bindTexture(ResourceLocationRegister.texture_pixel255);
				gui_terminal.setColorRGBA(50, 50, 50, 255);
				gui_terminal.drawTexturedModalRect(x2, y2, 0, 0, 150, 40);
				gui_terminal.drawString(fontRendererObj, "Text:", x2+1, y2+1, 0xffffff);
				if(t != null)gui_terminal.drawString(fontRendererObj, t.text, x2+1, y2+11, 0xffffff);
			}
			else if(tem instanceof Machine_Switch)
			{
				gui_terminal.mc.renderEngine.bindTexture(ResourceLocationRegister.texture_pixel255);
				gui_terminal.setColorRGBA(128, 0, 0, 255);
				gui_terminal.drawTexturedModalRect(x2, y2, 0, 0, 150, 40);
				gui_terminal.drawString(fontRendererObj, "Type: "+((Machine_Switch)tem).name(), x2+1, y2+1, 0xffffff);
				gui_terminal.drawString(fontRendererObj, "CustomName: "+((Machine_Switch)tem).CustomName(), x2+1, y2+11, 0xffffff);
				gui_terminal.drawString(fontRendererObj, "Pos: ["+tem.xCoord+","+tem.yCoord+","+tem.zCoord+"]", x2+1, y2+21, 0xffffff);
				gui_terminal.drawString(fontRendererObj, ((Machine_Switch)tem).GetSyntaxe(), x2+1, y2+31, 0xffffff);
			}
			else if(tem instanceof Machine_Counter)
			{
				gui_terminal.mc.renderEngine.bindTexture(ResourceLocationRegister.texture_pixel255);
				gui_terminal.setColorRGBA(0, 128, 0, 255);
				gui_terminal.drawTexturedModalRect(x2, y2, 0, 0, 150, 40);
				gui_terminal.drawString(fontRendererObj, "Type: "+((MachineNetWork)tem).name(), x2+1, y2+1, 0xffffff);
				gui_terminal.drawString(fontRendererObj, "CustomName: "+((MachineNetWork)tem).CustomName(), x2+1, y2+11, 0xffffff);
				gui_terminal.drawString(fontRendererObj, "Pos: ["+tem.xCoord+","+tem.yCoord+","+tem.zCoord+"]", x2+1, y2+21, 0xffffff);
				gui_terminal.drawString(fontRendererObj, ((Machine_Counter)tem).GetSyntaxe(), x2+1, y2+31, 0xffffff);
			}
			else if(tem instanceof Machine_FluxMeter)
			{
				gui_terminal.mc.renderEngine.bindTexture(ResourceLocationRegister.texture_pixel255);
				gui_terminal.setColorRGBA(0, 0, 128, 255);
				gui_terminal.drawTexturedModalRect(x2, y2, 0, 0, 150, 40);
				gui_terminal.drawString(fontRendererObj, "Type: "+((MachineNetWork)tem).name(), x2+1, y2+1, 0xffffff);
				gui_terminal.drawString(fontRendererObj, "CustomName: "+((MachineNetWork)tem).CustomName(), x2+1, y2+11, 0xffffff);
				gui_terminal.drawString(fontRendererObj, "Pos: ["+tem.xCoord+","+tem.yCoord+","+tem.zCoord+"]", x2+1, y2+21, 0xffffff);
				gui_terminal.drawString(fontRendererObj, ((Machine_FluxMeter)tem).GetSyntaxe(), x2+1, y2+31, 0xffffff);
			}
			else if(tem instanceof Machine_StorageInfo)
			{
				gui_terminal.mc.renderEngine.bindTexture(ResourceLocationRegister.texture_pixel255);
				gui_terminal.setColorRGBA(128, 0, 128, 255);
				gui_terminal.drawTexturedModalRect(x2, y2, 0, 0, 150, 40);
				gui_terminal.drawString(fontRendererObj, "Type: "+((MachineNetWork)tem).name(), x2+1, y2+1, 0xffffff);
				gui_terminal.drawString(fontRendererObj, "CustomName: "+((MachineNetWork)tem).CustomName(), x2+1, y2+11, 0xffffff);
				gui_terminal.drawString(fontRendererObj, "Pos: ["+tem.xCoord+","+tem.yCoord+","+tem.zCoord+"]", x2+1, y2+21, 0xffffff);
				gui_terminal.drawString(fontRendererObj, ((Machine_StorageInfo)tem).GetSyntaxe(), x2+1, y2+31, 0xffffff);
			}
			else if(tem instanceof MachineNetWork)
			{
				gui_terminal.mc.renderEngine.bindTexture(ResourceLocationRegister.texture_pixel255);
				gui_terminal.setColorRGBA(128, 128, 0, 255);
				gui_terminal.drawTexturedModalRect(x2, y2, 0, 0, 150, 40);
				gui_terminal.drawString(fontRendererObj, "Type: "+((MachineNetWork)tem).name(), x2+1, y2+1, 0xffffff);
				gui_terminal.drawString(fontRendererObj, "CustomName: "+((MachineNetWork)tem).CustomName(), x2+1, y2+11, 0xffffff);
				gui_terminal.drawString(fontRendererObj, "Pos: ["+tem.xCoord+","+tem.yCoord+","+tem.zCoord+"]", x2+1, y2+21, 0xffffff);
			}
		}
		//TODO 2
		if(!gui_terminal.tertminal_te.guiinfo.editmode)map.textTags.drawAtPos(gui_terminal, gui_terminal.tertminal_te.guiinfo.map_pos.x+ 20, gui_terminal.tertminal_te.guiinfo.map_pos.y + 20, comentC);
		
		if(!map.is_charged)gui_terminal.drawString(fontRendererObj, "loading please wait", (gui_terminal.width/2) - (fontRendererObj.getStringWidth("loading please wait")/2), (gui_terminal.height/2) - (fontRendererObj.FONT_HEIGHT/2) - 10, 0xffffff);
		if(!map.is_charged)gui_terminal.drawString(fontRendererObj, "If your block has been passed for the", (gui_terminal.width/2) - (fontRendererObj.getStringWidth("If your block has been passed for the")/2), (gui_terminal.height/2) - (fontRendererObj.FONT_HEIGHT/2), 0xffffff);
		if(!map.is_charged)gui_terminal.drawString(fontRendererObj, "first time: press button new map", (gui_terminal.width/2) - (fontRendererObj.getStringWidth("first time: press button new map")/2), (gui_terminal.height/2) - (fontRendererObj.FONT_HEIGHT/2) + 10, 0xffffff);
		
	}
	
	public boolean DrawBlock(Block i, int x, int y, int mult, int side)
	{
		if(i != null)
		{
			IIcon ico = i.getIcon(side,3);
			if(ico != null)
			{
				ItemStack itstack = new ItemStack(Item.getItemFromBlock(i));
				if(itstack.getItem() != null)
				{
					gui_terminal.mc.entityRenderer.itemRenderer.renderItem(gui_terminal.mc.renderViewEntity, itstack, 0, ItemRenderType.INVENTORY);
					gui_terminal.drawTexturedModelRectFromIcon(x, y, ico, 16*mult, 16*mult);
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean DrawItem(Item i, int x, int y, int mult)
	{
		if(i != null)
		{
			IIcon ico = i.getIcon(null, 1);
			
			if(ico != null)
			{
				ItemStack itstack = new ItemStack(i);
				if(itstack.getItem() != null)
				{
					gui_terminal.mc.entityRenderer.itemRenderer.renderItem(gui_terminal.mc.renderViewEntity, itstack, 0, ItemRenderType.INVENTORY);
					gui_terminal.drawTexturedModelRectFromIcon(x, y, ico, 16*mult, 16*mult);
					return true;
				}
			}
		}
		return false;
	}
	
	/*public String ONOFF(boolean state)
	{
		return state ? "ON" : "OFF";
	}*/
	
	public Map.Icon getIcon(int x, int y)
	{
		if(map.map.length > x && x >= 0 && map.map[0].length > y && y >= 0)
		{
			return Map.Icon.values()[map.map[x][y]];
		}
		return null;
	}
	
	public boolean IsOnMap(int x, int y)
	{
		return (map.map.length > x && x >= 0 && map.map[0].length > y && y >= 0);
	}

	@Override
	public void eventMouse(int x, int y, int click)
	{
		if(!gui_terminal.tertminal_te.guiinfo.editmode)
		{
			
			if(click == 0 && mouse_mappos.x >= 0 && mouse_mappos.y >= 0 && mouse_mappos.x < map.map.length && mouse_mappos.y < map.map[0].length)
			{
				Vector3 vec = map.maplink[mouse_mappos.x][mouse_mappos.y];
				short id = map.map[mouse_mappos.x][mouse_mappos.y];
				
				if(vec != null && id >= 6 && id <= 11)
				{
					for(TileEntity te : machines)
					{
						if(vec != null && te instanceof Machine_Switch && te.xCoord == vec.x && te.yCoord == vec.y && te.zCoord == vec.z)
						{
							Machine_Switch sw = (Machine_Switch) te;
							sw.SwitchONOFF(!sw.SwitchGetPosition());
						}
					}
				}
				else if(vec != null && id == 4)
				{
					for(TileEntity te : machines)
					{
						if(vec != null && te instanceof Machine_Counter && te.xCoord == vec.x && te.yCoord == vec.y && te.zCoord == vec.z)
						{
							Machine_Counter cnt = (Machine_Counter) te;
							cnt.Reset();
						}
					}
				}
			}
		}
		else
		{
			if(click == 0 && x >= (gui_terminal.width - 50) && x <= (gui_terminal.width - 10) && y >= 28 && y <= 48)
			{
				//up
				if(idselected < truelist.length-1)
				{
					++idselected;
				}
				else
				{
					idselected = 0;
				}
				gui_terminal.tertminal_te.guiinfo.itemselected = truelist[idselected];
				
			}
			else if(click == 0 && x >= (gui_terminal.width - 50) && x <= (gui_terminal.width - 10) && y >= 92 && y <= 112)
			{
				//down
				if(idselected > 0)
				{
					--idselected;
				}
				else
				{
					idselected = truelist.length-1;
				}
				gui_terminal.tertminal_te.guiinfo.itemselected = truelist[idselected];
			}
			else if(editlink != null && click == 0 && x >= (gui_terminal.width/2 - 20) && x <= (gui_terminal.width/2 + 20) && y >= (gui_terminal.height/2 - 62) && y <= (gui_terminal.height/2 - 42))
			{
				eventKeyBoard((char)200, 200);
			}
			else if(editlink != null && click == 0 && x >= (gui_terminal.width/2 - 20) && x <= (gui_terminal.width/2 + 20) && y >= (gui_terminal.height/2 + 42) && y <= (gui_terminal.height/2 + 62))
			{
				eventKeyBoard((char)208, 208);
			}
			else if(editlink != null && click == 0 && x >= (gui_terminal.width/2 + 98) && x <= (gui_terminal.width/2 + 138) && y >= (gui_terminal.height/2 - 10) && y <= (gui_terminal.height/2 + 10))
			{
				eventKeyBoard((char)28, 28);
			}
			else if(click == 0 && mouse_mappos.x >= 0 && mouse_mappos.x < map.map.length && mouse_mappos.y >= 0 && mouse_mappos.y < map.map[0].length)
			{
				map.map[mouse_mappos.x][mouse_mappos.y] = (short) gui_terminal.tertminal_te.guiinfo.itemselected;
				map.maplink[mouse_mappos.x][mouse_mappos.y] = null;
				map.textTags.removeByPos(mouse_mappos);
				//TODO 2
				if(gui_terminal.tertminal_te.guiinfo.itemselected != 0)
					map.maplink[mouse_mappos.x][mouse_mappos.y] = new Vector3(0,0,0);
			}
		}
	}
	
	int id_machine = 0;

	@Override
	public void eventKeyBoard(char c, int cid)
	{
		if(editlink != null && editlink.type == Type.StringTag)//TODO 2
		{
			StringTag text = map.textTags.getByPos(editlink.mappos);
			if(text == null) text = new StringTag("", editlink.mappos);
			if("azertyuiopqsdfghjklmwxcvbnAZERTYUIOPQSDFGHJKLMWXCVBN&饅'(-鐇鈞)=^$�*,;:!?./ｧ%ｵｨ｣+ｰ0987654321ｲ~#{[|`\\^@]}､ ><".indexOf(c) != -1)
			{
				text.text += c;
			}
			else if(cid == 28)
			{
				editlink = null;
			}
			else if(cid == 14)
			{
				if(text.text.length() > 0)
				{
					text.text = text.text.substring(0, text.text.length()-1);
				}
			}
			
			if(editlink != null)
			{
				map.textTags.setByPos(editlink.mappos, text);
			}
			return;
		}
		/*if(c == 's' || c == 'S')
		{
			map.saveconf(gui_terminal.tertminal_te);
		}
		else if(c == 'l' || c == 'L')
		{
			map.loadconf(gui_terminal.tertminal_te);
		}
		else if(c == 'c' || c == 'C')
		{
			map = new Map();
		}*/
		
		/*if(cid == 29 && editlink == null)//edit mode
		{
			gui_terminal.tertminal_te.guiinfo.editmode = !gui_terminal.tertminal_te.guiinfo.editmode;
		}*/
		
		if(editlink != null)
		{
			if(cid == 200)//up
			{
				if(editlink.type == Type.Switch)
				{
					List<TileEntity> machines_sp = new ArrayList<TileEntity>();
					for(TileEntity te : machines)
					{
						if(te instanceof Machine_Switch)machines_sp.add(te);
					}
					
					if(id_machine > 0)--id_machine;
					else id_machine = machines_sp.size()-1;
					
					if(machines_sp.size() > 0)
					{
						TileEntity mt = machines_sp.get(id_machine);
						editlink.args = new Vector3(mt.xCoord, mt.yCoord, mt.zCoord);
					}
				}
				else if(editlink.type == Type.FluxMetter)
				{
					List<TileEntity> machines_sp = new ArrayList<TileEntity>();
					for(TileEntity te : machines)
					{
						if(te instanceof Machine_FluxMeter)machines_sp.add(te);
					}
					
					if(id_machine > 0)--id_machine;
					else id_machine = machines_sp.size()-1;
					
					if(machines_sp.size() > 0)
					{
						TileEntity mt = machines_sp.get(id_machine);
						editlink.args = new Vector3(mt.xCoord, mt.yCoord, mt.zCoord);
					}
				}
				else if(editlink.type == Type.Counter)
				{
					List<TileEntity> machines_sp = new ArrayList<TileEntity>();
					for(TileEntity te : machines)
					{
						if(te instanceof Machine_Counter)machines_sp.add(te);
					}
					
					if(id_machine > 0)--id_machine;
					else id_machine = machines_sp.size()-1;
					
					if(machines_sp.size() > 0)
					{
						TileEntity mt = machines_sp.get(id_machine);
						editlink.args = new Vector3(mt.xCoord, mt.yCoord, mt.zCoord);
					}
				}
				else if(editlink.type == Type.StorageInfo)
				{
					List<TileEntity> machines_sp = new ArrayList<TileEntity>();
					for(TileEntity te : machines)
					{
						if(te instanceof Machine_StorageInfo)machines_sp.add(te);
					}
					
					if(id_machine > 0)--id_machine;
					else id_machine = machines_sp.size()-1;
					
					if(machines_sp.size() > 0)
					{
						TileEntity mt = machines_sp.get(id_machine);
						editlink.args = new Vector3(mt.xCoord, mt.yCoord, mt.zCoord);
					}
				}
			}
			else if(cid == 208)//down
			{
				if(editlink.type == Type.Switch)
				{
					List<TileEntity> machines_sp = new ArrayList<TileEntity>();
					for(TileEntity te : machines)
					{
						if(te instanceof Machine_Switch)machines_sp.add(te);
					}
					
					if(id_machine < machines_sp.size()-1)++id_machine;
					else id_machine = 0;
					
					if(machines_sp.size() > 0)
					{
						TileEntity mt = machines_sp.get(id_machine);
						editlink.args = new Vector3(mt.xCoord, mt.yCoord, mt.zCoord);
					}
				}
				else if(editlink.type == Type.FluxMetter)
				{
					List<TileEntity> machines_sp = new ArrayList<TileEntity>();
					for(TileEntity te : machines)
					{
						if(te instanceof Machine_FluxMeter)machines_sp.add(te);
					}
					
					if(id_machine < machines_sp.size()-1)++id_machine;
					else id_machine = 0;
					
					if(machines_sp.size() > 0)
					{
						TileEntity mt = machines_sp.get(id_machine);
						editlink.args = new Vector3(mt.xCoord, mt.yCoord, mt.zCoord);
					}
				}
				else if(editlink.type == Type.Counter)
				{
					List<TileEntity> machines_sp = new ArrayList<TileEntity>();
					for(TileEntity te : machines)
					{
						if(te instanceof Machine_Counter)machines_sp.add(te);
					}
					
					if(id_machine < machines_sp.size()-1)++id_machine;
					else id_machine = 0;
					
					if(machines_sp.size() > 0)
					{
						TileEntity mt = machines_sp.get(id_machine);
						editlink.args = new Vector3(mt.xCoord, mt.yCoord, mt.zCoord);
					}
				}
				else if(editlink.type == Type.StorageInfo)
				{
					List<TileEntity> machines_sp = new ArrayList<TileEntity>();
					for(TileEntity te : machines)
					{
						if(te instanceof Machine_StorageInfo)machines_sp.add(te);
					}
					
					if(id_machine < machines_sp.size()-1)++id_machine;
					else id_machine = 0;
					
					if(machines_sp.size() > 0)
					{
						TileEntity mt = machines_sp.get(id_machine);
						editlink.args = new Vector3(mt.xCoord, mt.yCoord, mt.zCoord);
					}
				}
			}
				
			if(cid == 28)//enter
			{
				if( map.maplink[editlink.mappos.x][editlink.mappos.y] != null)
				map.maplink[editlink.mappos.x][editlink.mappos.y] = editlink.args.copy();
				editlink = null;
			}
		}
	}

	@Override
	public void screenResized(int width, int height)
	{
		
	}
	
	Vector2 mouse_mappos = new Vector2(0,0);
	short[] truelist = {0,1,2,3,4,5,8,9,12,13,14,15,16,17,18};
	int idselected = 0;

	@Override
	public void update()
	{
		if( Mouse.isButtonDown(1))
		{
			gui_terminal.tertminal_te.guiinfo.map_pos.x += Mouse.getDX()*2;
			gui_terminal.tertminal_te.guiinfo.map_pos.y -= Mouse.getDY()*2;
			
			if(gui_terminal.tertminal_te.guiinfo.map_pos.x + map.map.length*20 -40 < gui_terminal.width-map.map.length)
				gui_terminal.tertminal_te.guiinfo.map_pos.x = gui_terminal.width-map.map.length*20 - 60;
			else if(gui_terminal.tertminal_te.guiinfo.map_pos.x > 50)
				gui_terminal.tertminal_te.guiinfo.map_pos.x = 50;
			
			if(gui_terminal.tertminal_te.guiinfo.map_pos.y - gui_terminal.height + map.map[0].length*20 +50 < 0)
				gui_terminal.tertminal_te.guiinfo.map_pos.y =  0-map.map[0].length*20 + gui_terminal.height -50;
			else if(gui_terminal.tertminal_te.guiinfo.map_pos.y > 50)
				gui_terminal.tertminal_te.guiinfo.map_pos.y = 50;
		}
		
		mouse_mappos.x = (gui_terminal.mousepos.x - gui_terminal.tertminal_te.guiinfo.map_pos.x)/20;
		mouse_mappos.y = (gui_terminal.mousepos.y - gui_terminal.tertminal_te.guiinfo.map_pos.y)/20;
		
		if(gui_terminal.tertminal_te.guiinfo.editmode)
		{
			if(Mouse.isButtonDown(2))//select ent (edit ent)
			{
				id_machine = 0;
				if(mouse_mappos.x >= 0 && mouse_mappos.x < map.map.length && mouse_mappos.y >= 0 && mouse_mappos.y < map.map[0].length)
				{
					int id = map.map[mouse_mappos.x][mouse_mappos.y];
					if(id >= 6 && id <= 11)//switch
					{
						editlink = new Tag(new Vector2(mouse_mappos.x, mouse_mappos.y), Tag.Type.Switch);
						
						Vector3 l = map.maplink[mouse_mappos.x][mouse_mappos.y];
						
						if(l != null)
						{
							editlink.args = new Vector3(l.x, l.y, l.z);
						}
					}
					else if(id == 5)//flux metter
					{
						editlink = new Tag(new Vector2(mouse_mappos.x, mouse_mappos.y), Tag.Type.FluxMetter);
						
						Vector3 l = map.maplink[mouse_mappos.x][mouse_mappos.y];
						
						if(l != null)
						{
							editlink.args = new Vector3(l.x, l.y, l.z);
						}
					}
					else if(id == 4)//counter
					{
						editlink = new Tag(new Vector2(mouse_mappos.x, mouse_mappos.y), Tag.Type.Counter);
						
						Vector3 l = map.maplink[mouse_mappos.x][mouse_mappos.y];
						
						if(l != null)
						{
							editlink.args = new Vector3(l.x, l.y, l.z);
						}
					}
					else if(id == 3)//battery info
					{
						editlink = new Tag(new Vector2(mouse_mappos.x, mouse_mappos.y), Tag.Type.StorageInfo);
						
						Vector3 l = map.maplink[mouse_mappos.x][mouse_mappos.y];
						
						if(l != null)
						{
							editlink.args = new Vector3(l.x, l.y, l.z);
						}
					}
					else if((id >= 15 && id <= 18) || id == 13)
					{

						editlink = new Tag(new Vector2(mouse_mappos.x, mouse_mappos.y), Tag.Type.StringTag);
					}
				}
			}
			
			/*if(Mouse.isButtonDown(0))
			{
				//TODO
				if(mouse_mappos.x >= 0 && mouse_mappos.x < map.map.length && mouse_mappos.y >= 0 && mouse_mappos.y < map.map[0].length)
				{
					map.map[mouse_mappos.x][mouse_mappos.y] = (short) gui_terminal.tertminal_te.guiinfo.itemselected;
					map.maplink[mouse_mappos.x][mouse_mappos.y] = null;
					map.textTags.removeByPos(mouse_mappos);
					//TODO 2
					if(gui_terminal.tertminal_te.guiinfo.itemselected != 0)
						map.maplink[mouse_mappos.x][mouse_mappos.y] = new Vector3(0,0,0);
				}
			}*/
			
			int wheel = Mouse.getDWheel()/120;
			if(wheel != 0)
			{
				int size = truelist.length;
				
				if(wheel < 0)
				{
					//down
					if(idselected > 0)
					{
						--idselected;
					}
					else
					{
						idselected = size-1;
					}
				}
				else
				{
					//up
					if(idselected < size-1)
					{
						++idselected;
					}
					else
					{
						idselected = 0;
					}
				}
				
				gui_terminal.tertminal_te.guiinfo.itemselected = truelist[idselected];
			}
		}
		
		for(int m_x = 0; m_x < map.map.length; ++m_x)
		{
			for(int m_y = 0; m_y < map.map[0].length; ++m_y)
			{
				Vector3 link_ent = map.maplink[m_x][m_y];
				short id = map.map[m_x][m_y];
				
				if(id >= 6 && id <= 11)//switch
				{
					boolean noexist = true;
					for(TileEntity te : machines)
					{
						if(link_ent != null && te instanceof Machine_Switch && te.xCoord == link_ent.x && te.yCoord == link_ent.y && te.zCoord == link_ent.z)
						{
							noexist = false;
							if(id == 6 || id == 8 || id == 10)
							{
								map.map[m_x][m_y] = (short) (((Machine_Switch)te).SwitchGetPosition()? 6 : 8);
							}
							else
							{
								map.map[m_x][m_y] = (short) (((Machine_Switch)te).SwitchGetPosition()? 7 : 9);
							}
						}
					}
					
					if(noexist)
					{
						if(id == 6 || id == 8 || id == 10)
						{
							map.map[m_x][m_y] = 10;
						}
						else
						{
							map.map[m_x][m_y] = 11;
						}
					}
				}
			}
		}
		
	}
	
	/*public void progressbar(int x, int y, int w, int h, int pour, int color_bar, boolean draw_pourcenttext, String coment, int color_text)
	{
		if(pour > 0)
		{
			gui_terminal.mc.renderEngine.bindTexture(ResourceLocationRegister.texture_pixel255);
			int r_bar = (color_bar >> 16) & 0xFF;
			int g_bar = (color_bar >> 8) & 0xFF;
			int b_bar = (color_bar >> 0) & 0xFF;
			gui_terminal.setColorRGBA(r_bar, g_bar, b_bar, 255);
			gui_terminal.drawTexturedModalRect(x, y, 0, 0, (int)(w*(pour/100f)), h);
			
			gui_terminal.setColorRGBA(0, 0, 0, 255);
			if(pour < 100)
				gui_terminal.drawTexturedModalRect((int)(x+w*(pour/100f)), y, 0, 0, w - (int)(w*(pour/100f)), h);
		}
		else
		{
			gui_terminal.mc.renderEngine.bindTexture(ResourceLocationRegister.texture_pixel255);
			gui_terminal.setColorRGBA(0, 0, 0, 255);
			gui_terminal.drawTexturedModalRect(x, y, 0, 0, w, h);
		}
		
		if(coment != null)
		{
			gui_terminal.drawString(fontRendererObj, coment, x, y + h + 5, color_text);
		}
		
		if(draw_pourcenttext)
		{
			gui_terminal.drawString(fontRendererObj, pour+" %", x, (y - fontRendererObj.FONT_HEIGHT)-5, color_text);
		}
	}*/

	@Override
	public void panelClose()
	{
		if(map.is_charged)map.saveconf(gui_terminal.tertminal_te);
	}
	
}