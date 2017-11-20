package com.enm.tileEntity;

import java.util.List;

import com.enm.api.network.Machine_FluxMeter;
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
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityFluxMeter extends TileEntity implements TEServerUpdate, Machine_FluxMeter, IEnergyHandler
{

	public ForgeDirection inputdir;
	public String Custom_Name = "";
	
	public int[] flux = new int[10];
	public int id_fl = 0;
	int flux_br = 0;
	
	public short tick;
	
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
		return "RF Flux Metter";
	}

	@Override
	public int GetFlux()
	{
		if(Tools.isClient() && !Minecraft.getMinecraft().isSingleplayer())
		{
			Tools.RequestClientCmd(xCoord, yCoord, zCoord, "GetFlux");
			return flux_br;
		}
		else
		{
			int fluxout = 0;
			int[] ii;
			//System.out.println("flux " + flux[0]);
			if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) ii = flux;
			else ii = ((TileEntityFluxMeter)Tools.GetTileEntityOnServer(xCoord, yCoord, zCoord)).flux;
			
			for(int i : ii)
			{
				fluxout += i;
			}
			//fluxout = fluxout/((TileEntityFluxMeter)Tools.GetTileEntityOnServer(xCoord, yCoord, zCoord)).flux.length;
			return fluxout/10;
		}
		//return ((TileEntityFluxMeter)Tools.GetTileEntityOnServer(xCoord, yCoord, zCoord)).flux;
	}
	
	@Override
	public boolean canConnectEnergy(ForgeDirection from)
	{
		return true;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate)
	{
		//tick = 15;
		if(from == inputdir)
		{
			ForgeDirection output_side = inputdir.getOpposite();
			TileEntity tile = worldObj.getTileEntity(xCoord + output_side .offsetX, yCoord + output_side.offsetY, zCoord + output_side.offsetZ);
	        if(tile instanceof IEnergyReceiver)
	        {
	        	tick = 10;
	        	IEnergyReceiver receiver = (IEnergyReceiver)tile;
	        	int Eflux = receiver.receiveEnergy(inputdir, maxReceive, simulate);
	        	flux[id_fl] = Eflux;
	        	if(id_fl < flux.length-1 ) ++id_fl; else id_fl = 0;
	        	return Eflux;
	        }
		}
		return 0;
	}
	
	@Override
	public void updateEntity()
	{
		if(tick > 1) --tick; else flux = new int[10];
		
		super.updateEntity();
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
	public String GetSyntaxe()
	{
		return GetFlux() + " RF/t";
	}

	@Override
	public void InfoFromServer(int flux)
	{
		flux_br = flux;
	}

}
