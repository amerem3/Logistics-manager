package com.enm.tileEntity;

import java.util.List;

import com.enm.api.network.Machine_FluidValve;
import com.enm.core.CoreMod;
import com.enm.network.NetWorkTileEntityNBT;
import com.enm.tileEntityutil.TEServerUpdate;
import com.enm.util.Tools;

import mekanism.api.gas.Gas;
import mekanism.api.gas.GasStack;
import mekanism.api.gas.IGasHandler;
import mekanism.api.gas.ITubeConnection;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityGasValve extends TileEntity implements Machine_FluidValve, TEServerUpdate, IGasHandler, ITubeConnection
{
	public String Custom_Name = "";
	public ForgeDirection inputdir;
	public boolean valve_position = false;
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("dir", Tools.ForgeDirectionToIntegerSide(inputdir));
		nbt.setString("customname", Custom_Name);
		nbt.setBoolean("position", valve_position);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		inputdir = Tools.IntegerSideToForgeDirection(nbt.getInteger("dir"));
		Custom_Name = nbt.getString("customname");
		valve_position = nbt.getBoolean("position");
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

	/*@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
	{
		//System.out.println(valve_position);
		if(from == inputdir && valve_position)
		{
			ForgeDirection dir_out = inputdir.getOpposite();
			TileEntity tef = worldObj.getTileEntity(xCoord+dir_out.offsetX, yCoord+dir_out.offsetY, zCoord+dir_out.offsetZ);
			
			if(tef instanceof IFluidHandler)
			{
				IFluidHandler tank = (IFluidHandler) tef;
				return tank.fill(from, resource, doFill);
			}
			
		}
		return 0;
	}*/

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
		return "Gas Valve";
	}

	@Override
	public void ValveOpenClose(boolean OC)
	{
		valve_position = OC;
		updateInventory();
	}

	@Override
	public boolean ValveGetPosition()
	{
		if(Tools.isClient() && !Minecraft.getMinecraft().isSingleplayer())Tools.RequestClientCmd(xCoord, yCoord, zCoord, "ValveGetPosition");
		return valve_position;
	}

	@Override
	public String GetSyntaxe()
	{
		return valve_position ? "Close" : "Open";
	}

	@Override
	public int receiveGas(ForgeDirection side, GasStack stack, boolean doTransfer)
	{
		if(side == inputdir && valve_position)
		{
			ForgeDirection dir_out = inputdir.getOpposite();
			TileEntity tef = worldObj.getTileEntity(xCoord+dir_out.offsetX, yCoord+dir_out.offsetY, zCoord+dir_out.offsetZ);
			if(tef instanceof IGasHandler)
			{
				((IGasHandler)tef).receiveGas(side, stack, doTransfer);
			}
		}
		return 0;
	}

	@Override
	public int receiveGas(ForgeDirection side, GasStack stack)
	{
		return 0;
	}

	@Override
	public GasStack drawGas(ForgeDirection side, int amount, boolean doTransfer)
	{
		return null;
	}

	@Override
	public GasStack drawGas(ForgeDirection side, int amount)
	{
		return null;
	}

	@Override
	public boolean canReceiveGas(ForgeDirection side, Gas type)
	{
		if(side == inputdir && side == inputdir.getOpposite())return true;
		return true;
	}

	@Override
	public boolean canDrawGas(ForgeDirection side, Gas type)
	{
		return false;
	}

	@Override
	public boolean canTubeConnect(ForgeDirection side)
	{
		return true;
	}

	@Override
	public void InfoFromServer(boolean pos)
	{
		valve_position = pos;
	}
}
