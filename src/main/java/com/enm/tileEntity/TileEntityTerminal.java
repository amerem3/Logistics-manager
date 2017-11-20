package com.enm.tileEntity;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.enm.api.network.MachineNetWork;
import com.enm.api.util.Vector2;
import com.enm.core.CoreMod;
import com.enm.guipanel.myinstallation.Map;
import com.enm.network.NetWorkTileEntityNBT;
import com.enm.tileEntityutil.TEServerUpdate;
import com.enm.util.Tools;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;

public class TileEntityTerminal extends TileEntity implements TEServerUpdate, MachineNetWork
{
	public int dir;
	public NBTTagCompound tag_gui = new NBTTagCompound();
	public GuiInfo guiinfo = new GuiInfo();
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("dir", dir);
		nbt.setTag("guitag", tag_gui);
		guiinfo.writeToNBT(nbt);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		dir = nbt.getInteger("dir");
		tag_gui = nbt.getCompoundTag("guitag");
		guiinfo.readFromNBT(nbt);
	}
	
	public void updateInventory()
	{
		if(Tools.isClient())
		{
			NBTTagCompound nbt = new NBTTagCompound();
			this.writeToNBT(nbt);
			CoreMod.network_TileEntityNBT.sendToServer(new NetWorkTileEntityNBT(xCoord, yCoord, zCoord, nbt));
		}
	}
	
	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound nbtTag = new NBTTagCompound();
		writeToNBT(nbtTag);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbtTag);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet)
	{
		readFromNBT(packet.func_148857_g());
	}

	@Override
	public String name()
	{
		return "RF Terminal";
	}

	@Override
	public TileEntity entity()
	{
		return this;
	}

	@Override
	public String CustomName()
	{
		return "";
	}
	
	public class GuiInfo
	{
		public boolean menu_dep;
		public int menu_id = 0;
		
		public int itemselected = 0;
		public boolean editmode = false;
		
		public Vector2 map_pos = new Vector2(0,0);
		
		//public Map map = new Map();
		
		public void writeToNBT(NBTTagCompound nbt)
		{
			nbt.setBoolean("gui_menudep", menu_dep);
			nbt.setInteger("gui_menuid", menu_id);
			nbt.setInteger("itemselected", itemselected);
			nbt.setBoolean("editmode", editmode);
			nbt.setInteger("mapposX", map_pos.x);
			nbt.setInteger("mapposY", map_pos.y);
			
			//nbt.removeTag("map");
			//nbt.setTag("map", tag_map);
			String dir = "";
			if(Tools.isServer())dir = MinecraftServer.getServer().getEntityWorld().getSaveHandler().getWorldDirectory()+"\\ENM\\";
			if(Tools.isClient() && Minecraft.getMinecraft().isSingleplayer())dir = Minecraft.getMinecraft().getIntegratedServer().getEntityWorld().getSaveHandler().getWorldDirectory()+"\\ENM\\";
			
			if(!new File(dir).exists() && dir != "")
			{
				new File(dir).mkdirs();
			}
			//saveconf();
		}
		
		public void readFromNBT(NBTTagCompound nbt)
		{
			menu_dep = nbt.getBoolean("gui_menudep");
			menu_id = nbt.getInteger("gui_menuid");
			itemselected = nbt.getInteger("itemselected");
			editmode = nbt.getBoolean("editmode");
			map_pos.x = nbt.getInteger("mapposX");
			map_pos.y = nbt.getInteger("mapposY");
			
			String dir = "";
			if(Tools.isServer())dir = MinecraftServer.getServer().getEntityWorld().getSaveHandler().getWorldDirectory()+"\\ENM\\";
			if(Tools.isClient() && Minecraft.getMinecraft().isSingleplayer())dir = Minecraft.getMinecraft().getIntegratedServer().getEntityWorld().getSaveHandler().getWorldDirectory()+"\\ENM\\";
			
			if(!new File(dir).exists() && dir != "")
			{
				new File(dir).mkdirs();
			}
		}
	}

	@Override
	public String GetSyntaxe()
	{
		return null;
	}
}
