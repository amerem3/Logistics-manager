package com.enm.tileEntity;

import java.util.List;

import com.enm.api.network.Machine_Counter;
import com.enm.core.CoreMod;
import com.enm.network.NetWorkTileEntityNBT;
import com.enm.tileEntityutil.TEServerUpdate;
import com.enm.util.Tools;

import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCounter extends TileEntity implements TEServerUpdate, Machine_Counter, IEnergyHandler
{

	public ForgeDirection inputdir;
	public String Custom_Name = "";
	long consomation = 0l;
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("dir", Tools.ForgeDirectionToIntegerSide(inputdir));
		nbt.setString("customname", Custom_Name);
		nbt.setLong("conso", consomation);
	}
	
	public void writeToNBTSend(NBTTagCompound nbt)
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
		if(nbt.hasKey("conso"))consomation = nbt.getLong("conso");
	}
	
	public void updateInventory()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBTSend(nbt);
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
		return "RF Counter";
	}
	
	@Override
	public boolean canConnectEnergy(ForgeDirection from)
	{
		return true;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate)
	{
		if(from == inputdir)
		{
			ForgeDirection output_side = inputdir.getOpposite();
			TileEntity tile = getWorldObj().getTileEntity(xCoord + output_side .offsetX, yCoord + output_side.offsetY, zCoord + output_side.offsetZ);
	        if(tile instanceof IEnergyReceiver)
	        {
	        	IEnergyReceiver receiver = (IEnergyReceiver)tile;
	        	int flux = receiver.receiveEnergy(inputdir, maxReceive, simulate);
	        	consomation += (long)flux;
	        	return flux;
	        }
		}
		return 0;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate)
	{
		return 0;
	}

	@Override
	public int getEnergyStored(ForgeDirection from)
	{
		if(from == inputdir)
		{
			ForgeDirection output_side = inputdir.getOpposite();
			TileEntity tile = getWorldObj().getTileEntity(xCoord + output_side .offsetX, yCoord + output_side.offsetY, zCoord + output_side.offsetZ);
	        if(tile instanceof IEnergyReceiver)
	        {
	        	IEnergyReceiver receiver = (IEnergyReceiver)tile;
	        	return receiver.getEnergyStored(inputdir);
	        }
		}
		return 0;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from)
	{
		if(from == inputdir)
		{
			ForgeDirection output_side = inputdir.getOpposite();
			TileEntity tile = getWorldObj().getTileEntity(xCoord + output_side .offsetX, yCoord + output_side.offsetY, zCoord + output_side.offsetZ);
	        if(tile instanceof IEnergyReceiver)
	        {
	        	IEnergyReceiver receiver = (IEnergyReceiver)tile;
	        	return receiver.getMaxEnergyStored(inputdir);
	        }
		}
		return 0;
	}

	@Override
	public long GetConsomation()
	{
		return consomation;
	}

	@Override
	public String GetSyntaxe()
	{
		long i = 0;
		if(Tools.isClient() && !Minecraft.getMinecraft().isSingleplayer())
		{
			Tools.RequestClientCmd(xCoord, yCoord, zCoord, "GetConsomation");
			i = consomation;
		}
		else
		{
			if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) i = ((TileEntityCounter)worldObj.getTileEntity(xCoord, yCoord, zCoord)).consomation;
			i = ((TileEntityCounter)Tools.GetTileEntityOnServer(xCoord, yCoord, zCoord)).consomation;
		}
		if((""+i).length() < 9)
		{
			return ""+i+" RF";
		}
		else if((""+i).length() < 12)
		{
			return ""+(i/1000)+" K RF";
		}
		else if((""+i).length() < 15)
		{
			return ""+(i/1000000)+" M RF";
		}
		else if((""+i).length() < 18)
		{
			return ""+(i/1000000000)+" G RF";
		}
		else if((""+i).length() < 21)
		{
			return ""+(i/1000000000000l)+" T RF";
		}
		else if((""+i).length() < 24)
		{
			return ""+(i/1000000000000000l)+" P RF";
		}
		else return ""+(i/1000000000000000000l)+" E RF";
	}

	@Override
	public void Reset()
	{
		if(Tools.isClient() && !Minecraft.getMinecraft().isSingleplayer())
		{
			Tools.RequestClientCmd(xCoord, yCoord, zCoord, "Reset");
		}
		else
		{
			((TileEntityCounter)Tools.GetTileEntityOnServer(xCoord, yCoord, zCoord)).consomation = 0l;
		}
	}

	@Override
	public void InfoFromServer(long cons)
	{
		consomation = cons;
	}

}
