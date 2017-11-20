package com.enm.tileEntity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

import com.enm.api.network.Machine_FluidStorageInfo;
import com.enm.core.Config;
import com.enm.core.CoreMod;
import com.enm.network.NetWorkTileEntityNBT;
import com.enm.tileEntityutil.TEServerUpdate;
import com.enm.util.Tools;

import mekanism.api.gas.GasStack;
import mekanism.api.gas.GasTank;
import mekanism.api.gas.IGasHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.IFluidTank;

public class TileEntityGasStorageInfo extends TileEntity implements Machine_FluidStorageInfo, TEServerUpdate
{
	public ForgeDirection inputdir;
	public String Custom_Name = "";
	FluidTankInfo[] tank;
	
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
		return "Gas Storage Info";
	}

	@Override
	public String GetSyntaxe()
	{
		FluidTankInfo[] info = GetFluidStoredInfo();
		if(info == null || info.length == 0) return "";
		
		if(Config.onPourcent)
		{
			return (info[0].fluid.amount/info[0].capacity)*100 +"%";
		}
		else
		{
			return info[0].fluid.amount+"/"+info[0].capacity+" mB";
		}
	}
	
	public String GetSyntaxeNOP()
	{
		FluidTankInfo[] info = GetFluidStoredInfo();
		if(info == null || info.length == 0) return "No Gas Info";
		return info[0].fluid.amount+"/"+info[0].capacity+" mB";
	}

	@Override
	public FluidTankInfo[] GetFluidStoredInfo()
	{
		if(Tools.isClient() && !Minecraft.getMinecraft().isSingleplayer())
		{
			Tools.RequestClientCmd(xCoord, yCoord, zCoord, "GetFluidStoredInfo");
			return tank;
			//FluidTankInfo flsd = new FluidTankInfo(new FluidStack(fluidID, amount), capacity)
		}
		else
		{
			TileEntity te = Tools.GetTileEntityOnServer(xCoord+inputdir.offsetX, yCoord+inputdir.offsetY, zCoord+inputdir.offsetZ);
			Class<?> te_c = te.getClass();
			try
			{
				Object instance = te_c.newInstance();
				for(Field f : te_c.getFields())
				{
					if(f.getType() == GasTank.class)
					{
						f.setAccessible(true);
							GasTank tank = (GasTank) f.get(instance);
							if(tank != null)
							{
								System.out.println(tank.getStored());
							}
					}
				}
			}
			
			catch (InstantiationException e) {e.printStackTrace();}
			catch (IllegalAccessException e) {e.printStackTrace();}
			catch (IllegalArgumentException e) {e.printStackTrace();}
		}
		return null;
	}

	@Override
	public void InfoFromServer(String sntx)
	{
		String[] fluids = sntx.split("-");
		tank = new FluidTankInfo[fluids.length];
		int id_case = 0;
		for(String fluid : fluids)
		{
			if(fluid.contains(","))
			{
				String[] args = fluid.split(",");
				if(args.length >= 3)
				{
					int id = Integer.parseInt(args[0]);
					int cap = Integer.parseInt(args[1]);
					int amt = Integer.parseInt(args[1]);
					tank[id_case] = new FluidTankInfo(new FluidStack(id, amt), cap);
					++id_case;
				}
			}
		}
	}
}
