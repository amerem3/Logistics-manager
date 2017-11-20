package com.enm.guipanel.myinstallation;

import java.io.File;
import java.io.IOException;

import com.enm.api.util.Vector2;
import com.enm.api.util.Vector3;
import com.enm.util.Tools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;

public class Map
{
	public short[][] map;
	public Vector3[][] maplink;
	public ListStringTag textTags;
	
	public boolean is_charged = false;
	
	public Map(boolean new_)
	{
		if(new_) is_charged = true;
		 map = new short[100][50];
		 maplink = new Vector3[100][50];
		 textTags = new ListStringTag();
	}
	
	@SideOnly(Side.CLIENT)
	private void ischarged(TileEntity te)
	{
		if(Minecraft.getMinecraft().isSingleplayer())
		{
			String dir = Minecraft.getMinecraft().getIntegratedServer().getEntityWorld().getSaveHandler().getWorldDirectory()+"\\ENM\\";;
			if(dir != null)
			{
				boolean fileexiste = new File(dir+"X"+te.xCoord+"Y"+te.yCoord+"Z"+te.zCoord+"D"+te.getWorldObj().provider.dimensionId).exists();
				if(!fileexiste)is_charged = true;
			}
		}
		else
		{
			Tools.RequestMapExist(te.xCoord, te.yCoord, te.zCoord, Minecraft.getMinecraft().thePlayer);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void loadconf(TileEntity te)
	{
		if(Tools.isClient() && !Minecraft.getMinecraft().isSingleplayer())
		{
			Tools.RequestClientMapInfo(te.xCoord, te.yCoord, te.zCoord, Minecraft.getMinecraft().thePlayer);
			return;
		}
		
		String dir = "";
		if(Tools.isServer())dir = MinecraftServer.getServer().getEntityWorld().getSaveHandler().getWorldDirectory()+"\\ENM\\";
		if(Tools.isClient() && Minecraft.getMinecraft().isSingleplayer())dir = Minecraft.getMinecraft().getIntegratedServer().getEntityWorld().getSaveHandler().getWorldDirectory()+"\\ENM\\";
		
		if(!new File(dir).exists() && dir != "")
		{
			new File(dir).mkdirs();
		}
		if(dir == "")return;
		
		if(te.getWorldObj() != null && new File(dir+"X"+te.xCoord+"Y"+te.yCoord+"Z"+te.zCoord+"D"+te.getWorldObj().provider.dimensionId).exists())
		{
			try
			{
				NBTTagCompound tag_map = CompressedStreamTools.read(new File(dir+"X"+te.xCoord+"Y"+te.yCoord+"Z"+te.zCoord+"D"+te.getWorldObj().provider.dimensionId));
				if(tag_map != null)
				{
					ReadNBT(tag_map);
				}
			}
			catch (IOException e){}
		}
	}
	
	public void loadconfServer(TileEntity te)
	{
		String dir = "";
		if(Tools.isServer())dir = MinecraftServer.getServer().getEntityWorld().getSaveHandler().getWorldDirectory()+"\\ENM\\";
		if(Tools.isClient() && Minecraft.getMinecraft().isSingleplayer())dir = Minecraft.getMinecraft().getIntegratedServer().getEntityWorld().getSaveHandler().getWorldDirectory()+"\\ENM\\";
		
		if(!new File(dir).exists() && dir != "")
		{
			new File(dir).mkdirs();
		}
		if(dir == "")return;
		boolean fileexiste = new File(dir+"X"+te.xCoord+"Y"+te.yCoord+"Z"+te.zCoord+"D"+te.getWorldObj().provider.dimensionId).exists();
		if(te.getWorldObj() != null && fileexiste)
		{
			try
			{
				NBTTagCompound tag_map = CompressedStreamTools.read(new File(dir+"X"+te.xCoord+"Y"+te.yCoord+"Z"+te.zCoord+"D"+te.getWorldObj().provider.dimensionId));
				if(tag_map != null)
				{
					ReadNBT(tag_map);
				}
			}
			catch (IOException e){}
		}
		else if(!fileexiste)
		{
			is_charged = true;
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void saveconf(TileEntity te)
	{
		if(Tools.isClient() && !Minecraft.getMinecraft().isSingleplayer())
		{
			Tools.SendClientMapInfo(te.xCoord, te.yCoord, te.zCoord, this);
			return;
		}
			
		String dir = "";
		if(Tools.isServer())dir = MinecraftServer.getServer().getEntityWorld().getSaveHandler().getWorldDirectory()+"\\ENM\\";
		if(Tools.isClient() && Minecraft.getMinecraft().isSingleplayer())dir = Minecraft.getMinecraft().getIntegratedServer().getEntityWorld().getSaveHandler().getWorldDirectory()+"\\ENM\\";
		
		if(!new File(dir).exists() && dir != "")
		{
			new File(dir).mkdirs();
		}
		if(dir == "")return;
		
		NBTTagCompound tag_map = new NBTTagCompound();
		SaveToNBT(tag_map);
		
		if(te.getWorldObj() != null)
		{
			try
			{
				CompressedStreamTools.write(tag_map, new File(dir+"X"+te.xCoord+"Y"+te.yCoord+"Z"+te.zCoord+"D"+te.getWorldObj().provider.dimensionId));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void saveconfServer(TileEntity te)
	{
		String dir = "";
		if(Tools.isServer())dir = MinecraftServer.getServer().getEntityWorld().getSaveHandler().getWorldDirectory()+"\\ENM\\";
		if(Tools.isClient() && Minecraft.getMinecraft().isSingleplayer())dir = Minecraft.getMinecraft().getIntegratedServer().getEntityWorld().getSaveHandler().getWorldDirectory()+"\\ENM\\";
		
		if(!new File(dir).exists() && dir != "")
		{
			new File(dir).mkdirs();
		}
		if(dir == "")return;
		
		NBTTagCompound tag_map = new NBTTagCompound();
		SaveToNBT(tag_map);
		
		if(te.getWorldObj() != null)
		{
			try
			{
				CompressedStreamTools.write(tag_map, new File(dir+"X"+te.xCoord+"Y"+te.yCoord+"Z"+te.zCoord+"D"+te.getWorldObj().provider.dimensionId));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void SaveToNBT(NBTTagCompound tag)
	{
		
		for(int m_x = 0; m_x < map.length; ++m_x)
		{
			for(int m_y = 0; m_y < map[0].length; ++m_y)
			{
				short icon = map[m_x][m_y];
				Vector3 lnk = maplink [m_x][m_y];
				
				if(icon != 0)
				{
					NBTTagCompound tag_grid = new NBTTagCompound();
					
					tag_grid.setShort("icon", icon);
					if(lnk != null)
					{
						if(lnk != null)
						{
							tag_grid.setInteger("entityX", lnk.x);
							tag_grid.setInteger("entityY", lnk.y);
							tag_grid.setInteger("entityZ", lnk.z);
						}
					}
					tag.setTag("X"+m_x+"Y"+m_y, tag_grid);
				}
			}
		}
		NBTTagCompound tag_string = new NBTTagCompound();
		
		int id = 0;
		for(StringTag tag_s : textTags.strtag)
		{
			NBTTagCompound tag_thisstring = new NBTTagCompound();
			tag_thisstring.setString("txt", tag_s.text);
			tag_s.posOnMap.SaveToNBT(tag_thisstring, "pos");
			tag_string.setTag(""+id, tag_thisstring);
			++id;
		}
		tag.setTag("StringTag", tag_string);
	}
	
	public void ReadNBT(NBTTagCompound tag)
	{
		float pas = 100/(map.length*map[0].length);
		for(int m_x = 0; m_x < map.length; ++m_x)
		{
			for(int m_y = 0; m_y < map[0].length; ++m_y)
			{
				if(tag.hasKey("X"+m_x+"Y"+m_y))
				{
					NBTTagCompound tag_grid = tag.getCompoundTag("X"+m_x+"Y"+m_y);
					if(tag_grid != null)
					{
						map[m_x][m_y] = tag_grid.getShort("icon");
						//rep 1
						
						if(tag_grid.hasKey("entityX"))
						{
							maplink [m_x][m_y] = new Vector3(tag_grid.getInteger("entityX"), tag_grid.getInteger("entityY"), tag_grid.getInteger("entityZ"));
						}
					}
				}
				else
				{
					map[m_x][m_y] = 0;
				}
			}
		}
		NBTTagCompound tag_string = tag.getCompoundTag("StringTag");
		textTags.removeAll();
		int id = 0;
		while(tag_string.hasKey(""+id))
		{
			NBTTagCompound tag_thisstring = tag_string.getCompoundTag(""+id);
			String txt = tag_thisstring.getString("txt");
			Vector2 pos = new Vector2();
			pos.ReadFromNBT(tag_thisstring, "pos");
			textTags.add(new StringTag(txt, pos));
			++id;
		}
		is_charged = true;
		//for(StringTag t:textTags.strtag)System.out.println(t.text+" | "+t.posOnMap.GetOutPutConsole());
	}
	
	public static class link
	{
		//public Vector2 posmap;
		public Vector3 entity;
		
		public link(Vector3 _entity)
		{
			//posmap = _posmap;
			entity = _entity;
		}
	}
	
	public enum Icon
	{
		empty(8, 0, 0, 0, 20, 20),//0
		transformerV(0, 0, 0, 3, 20, 16),//1
		transformerH(1, 0, 3, 0, 16, 20),//2
		battry(5, 0, 2, 2, 18, 18),//3
		counter(6, 0, 2, 2, 18, 18),//4
		flux(7, 0, 2, 2, 18, 18),//5
		switchHOn(0, 1, 4, 0, 16, 20),//6
		switchVOn(1, 1, 0, 4, 20, 16),//7
		switchHOff(0, 2, 4, 0, 16, 20),//8
		switchVOff(1, 2, 0, 4, 20, 16),//9
		switchHErr(0, 3, 4, 0, 16, 20),//10
		switchVErr(1, 3, 0, 4, 20, 16),//11
		wire(4, 0, 9, 9, 11, 11),//12
		iconframe(9, 0, 1, 1, 19, 19),//13
		generator(2, 1, 2, 2, 18, 18),//14
		IO_up(3, 1, 0, 5, 20, 20),//15
		IO_right(4, 1, 0, 0, 15, 20),//16
		IO_down(5, 1, 0, 0, 20, 15),//17
		IO_left(6, 1, 5, 0, 20, 20),//18
		fluidName(10, 0, 2, 2, 18, 18);//19
		public int x, y, startx, starty, endx, endy;
		Icon(int _x, int _y, int _startx, int _starty, int _endx, int _endy)
		{
			x = _x;
			y = _y;
			startx = _startx;
			starty = _starty;
			endx = _endx;
			endy = _endy;
		}
	}
}

