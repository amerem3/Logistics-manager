package com.enm.tileEntity;

import com.enm.api.network.Machine_Switch;
import com.enm.api.redstone.IRedstoneOutput;
import com.enm.api.redstone.RedstoneOutput;
import com.enm.core.CoreMod;
import com.enm.network.NetWorkTileEntityNBT;
import com.enm.tileEntityutil.TEServerUpdate;
import com.enm.util.Tools;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityRedstoneSwitch extends TileEntity implements TEServerUpdate, Machine_Switch, IRedstoneOutput
{
	public String Custom_Name = "";
	public boolean switch_position = false;
	public RedstoneOutput redstone = new RedstoneOutput();
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setString("customname", Custom_Name);
		nbt.setBoolean("position", switch_position);
		redstone.writeToNBT(nbt);
	}
	
	public void writeToNBTServer(NBTTagCompound nbt)
	{
		nbt.setString("customname", Custom_Name);
		nbt.setBoolean("position", switch_position);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		Custom_Name = nbt.getString("customname");
		switch_position = nbt.getBoolean("position");
		redstone.readFromNBT(nbt);
	}
	
	public void updateInventory()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBTServer(nbt);
		CoreMod.network_TileEntityNBT.sendToServer(new NetWorkTileEntityNBT(xCoord, yCoord, zCoord, nbt));
	}
	
	@Override
	public void updateEntity()
	{
		if(switch_position)redstone.setRedstoneSinal(15);
		else redstone.setRedstoneSinal(0);
		redstone.updateEntity(worldObj, xCoord, yCoord, zCoord);
		super.updateEntity();
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
		return "Redstone Switch";
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
	public String GetSyntaxe()
	{
		return switch_position ? "ON" : "OFF";
	}

	@Override
	public void InfoFromServer(boolean pos)
	{
		switch_position = pos;
	}

	@Override
	public int getRedstoneSinal()
	{
		return redstone.getRedstoneSinal();
	}
}