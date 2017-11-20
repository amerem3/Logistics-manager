package com.enm.tileEntity;

import java.util.List;

import com.enm.api.network.MachineNetWork;
import com.enm.api.util.Vector3;
import com.enm.core.CoreMod;
import com.enm.network.NetWorkTileEntityNBT;
import com.enm.util.Tools;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntitySwitchControler extends TileEntity implements MachineNetWork
{
	public ForgeDirection inputdir;
	public Vector3 ent;
	
	public TileEntitySwitchControler()
	{
		ent = new Vector3();
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("dir", Tools.ForgeDirectionToIntegerSide(inputdir));
		ent.SaveToNBT(nbt, "ent");
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		inputdir = Tools.IntegerSideToForgeDirection(nbt.getInteger("dir"));
		ent.ReadFromNBT(nbt, "ent");
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
	public String name()
	{
		return "switch controler";
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

	@Override
	public String GetSyntaxe()
	{
		return null;
	}
}
