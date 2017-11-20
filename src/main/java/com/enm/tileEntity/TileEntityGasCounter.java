package com.enm.tileEntity;

import com.enm.api.network.Machine_FluidCounter;
import com.enm.core.CoreMod;
import com.enm.network.NetWorkTileEntityNBT;
import com.enm.tileEntityutil.TEServerUpdate;
import com.enm.util.Tools;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
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

public class TileEntityGasCounter extends TileEntity implements TEServerUpdate, Machine_FluidCounter, IGasHandler, ITubeConnection
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
	public long GetConsomation()
	{
		return consomation;
	}

	@Override
	public String GetSyntaxe()
	{
		//long i = ((TileEntityGasCounter)Tools.GetTileEntityOnServer(xCoord, yCoord, zCoord)).consomation;
		long i = 0;
		if(Tools.isClient() && !Minecraft.getMinecraft().isSingleplayer())
		{
			Tools.RequestClientCmd(xCoord, yCoord, zCoord, "GetConsomation");
			i = consomation;
		}
		else
		{
			if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) i = ((TileEntityGasCounter)worldObj.getTileEntity(xCoord, yCoord, zCoord)).consomation;
			i = ((TileEntityGasCounter)Tools.GetTileEntityOnServer(xCoord, yCoord, zCoord)).consomation;
		}
		if((""+i).length() < 9)
		{
			return ""+i+" mB";
		}
		else if((""+i).length() < 12)
		{
			return ""+(i/1000)+" B";
		}
		else if((""+i).length() < 15)
		{
			return ""+(i/1000000)+" KB";
		}
		else if((""+i).length() < 18)
		{
			return ""+(i/1000000000)+" MB";
		}
		else if((""+i).length() < 21)
		{
			return ""+(i/1000000000000l)+" GB";
		}
		else if((""+i).length() < 24)
		{
			return ""+(i/1000000000000000l)+" TB";
		}
		else return ""+(i/1000000000000000000l)+" PB";
	}

	@Override
	public void Reset()
	{
		((TileEntityGasCounter)Tools.GetTileEntityOnServer(xCoord, yCoord, zCoord)).consomation = 0l;
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
		return "Gas Counter";
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
				int this_flow = ((IGasHandler)tef).receiveGas(side, stack, doTransfer);
				consomation += (long)this_flow;
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
	public void InfoFromServer(long cons)
	{
		consomation = cons;
	}
}
