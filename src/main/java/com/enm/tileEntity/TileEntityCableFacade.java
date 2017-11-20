package com.enm.tileEntity;

import com.enm.api.network.NetWork;
import com.enm.core.CoreMod;
import com.enm.network.NetWorkTileEntityNBT;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityCableFacade extends TileEntity implements NetWork
{
	
	public int TXID;
	public int meta;
	
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
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("TXID", TXID);
		nbt.setInteger("meta", meta);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		TXID = nbt.getInteger("TXID");
		meta = nbt.getInteger("meta");
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
		return "cable";
	}
}
