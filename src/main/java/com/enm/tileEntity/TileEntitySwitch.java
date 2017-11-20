package com.enm.tileEntity;

import java.util.List;

import com.enm.api.network.Machine_Switch;
import com.enm.core.CoreMod;
import com.enm.network.NetWorkTileEntityNBT;
import com.enm.tileEntityutil.TEServerUpdate;
import com.enm.util.Tools;

import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntitySwitch extends TileEntity implements TEServerUpdate, Machine_Switch, IEnergyHandler
{
	public String Custom_Name = "";
	public ForgeDirection inputdir;
	public boolean switch_position = false;
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("dir", Tools.ForgeDirectionToIntegerSide(inputdir));
		nbt.setString("customname", Custom_Name);
		nbt.setBoolean("position", switch_position);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		inputdir = Tools.IntegerSideToForgeDirection(nbt.getInteger("dir"));
		Custom_Name = nbt.getString("customname");
		switch_position = nbt.getBoolean("position");
	}
	
	public void updateInventory()
	{
		//if(Tools.isClient())
		//{
			NBTTagCompound nbt = new NBTTagCompound();
			this.writeToNBT(nbt);
			CoreMod.network_TileEntityNBT.sendToServer(new NetWorkTileEntityNBT(xCoord, yCoord, zCoord, nbt));
		//}
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
		return "RF Switch";
	}

	@Override
	public void SwitchONOFF(boolean OnOff)
	{
		switch_position = OnOff;
		updateInventory();
	}

	@Override
	public boolean SwitchGetPosition()
	{
		if(Tools.isClient() && !Minecraft.getMinecraft().isSingleplayer())Tools.RequestClientCmd(xCoord, yCoord, zCoord, "SwitchGetPosition");
		return switch_position;
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from)
	{
		return true;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate)
	{
		if(switch_position && from == inputdir)
		{
			ForgeDirection output_side = inputdir.getOpposite();
			TileEntity tile = getWorldObj().getTileEntity(xCoord + output_side .offsetX, yCoord + output_side.offsetY, zCoord + output_side.offsetZ);
	        if(tile instanceof IEnergyReceiver)
	        {
	        	IEnergyReceiver receiver = (IEnergyReceiver)tile;
	        	return receiver.receiveEnergy(inputdir, maxReceive, simulate);
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
		if(switch_position && from == inputdir)
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
		if(switch_position && from == inputdir)
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
	public String GetSyntaxe()
	{
		return switch_position ? "ON" : "OFF";
	}

	@Override
	public void InfoFromServer(boolean pos)
	{
		switch_position = pos;
	}
}
