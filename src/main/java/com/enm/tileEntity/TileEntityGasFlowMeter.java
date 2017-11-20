package com.enm.tileEntity;

import com.enm.api.network.Machine_FluidFlowMeter;
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

public class TileEntityGasFlowMeter extends TileEntity implements TEServerUpdate, Machine_FluidFlowMeter, IGasHandler, ITubeConnection
{
	public String Custom_Name = "";
	public ForgeDirection inputdir;
	
	public int[] flow = new int[10];
	int flow_br = 0;
	public int id_fl = 0;
	short tick;
	
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
	public void updateEntity()
	{
		if(tick > 1) --tick; else flow = new int[10];
		super.updateEntity();
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
		return "Gas Flow Meter";
	}

	@Override
	public int GetFlow()
	{
		if(Tools.isClient() && !Minecraft.getMinecraft().isSingleplayer())
		{
			Tools.RequestClientCmd(xCoord, yCoord, zCoord, "GetFlow");
			return flow_br;
		}
		else
		{
			int flowout = 0;
			for(int i :((TileEntityGasFlowMeter)Tools.GetTileEntityOnServer(xCoord, yCoord, zCoord)).flow)
			{
				flowout += i;
			}
			return flowout/10;
		}
	}

	@Override
	public String GetSyntaxe()
	{
		return GetFlow()+" mB/t";
	}
	
	@Override
	public int receiveGas(ForgeDirection side, GasStack stack, boolean doTransfer)
	{
		if(side == inputdir)
		{
			ForgeDirection dir_out = inputdir.getOpposite();
			TileEntity tef = worldObj.getTileEntity(xCoord+dir_out.offsetX, yCoord+dir_out.offsetY, zCoord+dir_out.offsetZ);
			if(tef instanceof IGasHandler)
			{
				tick = 10;
				int this_flow = ((IGasHandler)tef).receiveGas(side, stack, doTransfer);
				flow[id_fl] = this_flow;
	        	if(id_fl < flow.length-1 ) ++id_fl; else id_fl = 0;
	        	return this_flow;
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
	public void InfoFromServer(int f)
	{
		flow_br = f;
	}
}