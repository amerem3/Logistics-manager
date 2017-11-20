package com.enm.tileEntity;

import java.util.List;

import com.enm.api.network.Machine_StorageInfo;
import com.enm.core.Config;
import com.enm.core.CoreMod;
import com.enm.network.NetWorkTileEntityNBT;
import com.enm.tileEntityutil.TEServerUpdate;
import com.enm.util.Tools;

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import cofh.api.energy.IEnergyStorage;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityRFStorageInfo extends TileEntity implements TEServerUpdate, Machine_StorageInfo
{
	public ForgeDirection inputdir;
	public String Custom_Name = "";
	public String sytx = "";
	int mx = 0, s = 0;
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("dir", Tools.ForgeDirectionToIntegerSide(inputdir));
		nbt.setString("customname", Custom_Name);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		inputdir = Tools.IntegerSideToForgeDirection(nbt.getInteger("dir"));
		Custom_Name = nbt.getString("customname");
	}
	
	public void updateInventory()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		CoreMod.network_TileEntityNBT.sendToServer(new NetWorkTileEntityNBT(xCoord, yCoord, zCoord, nbt));
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
	public TileEntity entity()
	{
		return this;
	}

	@Override
	public String CustomName()
	{
		return Custom_Name;
	}

	@Override
	public String name()
	{
		return "RF Storage Info";
	}

	@Override
	public int GetEnergyStored()
	{
		if(Tools.isClient() && !Minecraft.getMinecraft().isSingleplayer())
		{
			Tools.RequestClientCmd(xCoord, yCoord, zCoord, "GetEnergyStored");
			return s;
		}
		else
		{
			TileEntity te = Tools.GetTileEntityOnServer(xCoord+inputdir.offsetX, yCoord+inputdir.offsetY, zCoord+inputdir.offsetZ);
			if(te != null)
			{
				if(te instanceof IEnergyProvider)
				{
					return ((IEnergyProvider)te).getEnergyStored(inputdir.getOpposite());
				}
				else if(te instanceof IEnergyReceiver)
				{
					return ((IEnergyReceiver)te).getEnergyStored(inputdir.getOpposite());
				}
				else if(te instanceof IEnergyStorage)
				{
					return ((IEnergyStorage)te).getEnergyStored();
				}
			}
		}
		return 0;
	}
	
	@Override
	public int GetMaxEnergyStored()
	{
		if(Tools.isClient() && !Minecraft.getMinecraft().isSingleplayer())
		{
			Tools.RequestClientCmd(xCoord, yCoord, zCoord, "GetMaxEnergyStored");
			return mx;
		}
		else
		{
			TileEntity te = Tools.GetTileEntityOnServer(xCoord+inputdir.offsetX, yCoord+inputdir.offsetY, zCoord+inputdir.offsetZ);
			if(te != null)
			{
				if(te instanceof IEnergyProvider)
				{
					return ((IEnergyProvider)te).getMaxEnergyStored(inputdir.getOpposite());
				}
				else if(te instanceof IEnergyReceiver)
				{
					return ((IEnergyReceiver)te).getMaxEnergyStored(inputdir.getOpposite());
				}
				else if(te instanceof IEnergyStorage)
				{
					return ((IEnergyStorage)te).getMaxEnergyStored();
				}
			}
		}
		return 0;
	}

	@Override
	public String GetSyntaxe()
	{
		if(Tools.isClient() && !Minecraft.getMinecraft().isSingleplayer())
		{
			return sytx;
		}
		if(Config.onPourcent)
		{
			return (GetEnergyStored()/GetMaxEnergyStored())*100 +"%";
		}
		else
		{
			return GetEnergyStored()+"/"+GetMaxEnergyStored()+" RF";
		}
	}

	@Override
	public void InfoFromServer(int Estored, int MaxEstored)
	{
		if(Estored >= 0)s = Estored;
		if(MaxEstored >= 0)mx = MaxEstored;
	}
}
