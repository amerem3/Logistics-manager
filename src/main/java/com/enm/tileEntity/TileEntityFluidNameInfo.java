package com.enm.tileEntity;

import java.util.List;

import com.enm.api.network.Machine_FluidName;
import com.enm.core.CoreMod;
import com.enm.network.NetWorkTileEntityNBT;
import com.enm.tileEntityutil.TEServerUpdate;
import com.enm.util.Tools;

import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.IFluidTank;

public class TileEntityFluidNameInfo extends TileEntity implements Machine_FluidName, TEServerUpdate
{
	public ForgeDirection inputdir;
	public String Custom_Name = "";
	String fluidname = "";
	int id_fluid = 0;
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("dir", Tools.ForgeDirectionToIntegerSide(inputdir));
		nbt.setString("customname", Custom_Name);
		nbt.setInteger("idfluid", id_fluid);
	}
	
	@Override	
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		inputdir = Tools.IntegerSideToForgeDirection(nbt.getInteger("dir"));
		Custom_Name = nbt.getString("customname");
		id_fluid = nbt.getInteger("idfluid");
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
		return "Fluid Name Info";
	}

	@Override
	public String GetFluidName()
	{
		if(Tools.isClient() && !Minecraft.getMinecraft().isSingleplayer())
		{
			Tools.RequestClientCmd(xCoord, yCoord, zCoord, "GetFluidName");
			return fluidname;
		}
		else
		{
			TileEntity te = worldObj.getTileEntity(xCoord+inputdir.offsetX, yCoord+inputdir.offsetY, zCoord+inputdir.offsetZ);
			if(te instanceof IFluidHandler)
			{
				if(((IFluidHandler)te).getTankInfo(inputdir).length <= id_fluid || ((IFluidHandler)te).getTankInfo(inputdir)[id_fluid].fluid == null || ((IFluidHandler)te).getTankInfo(inputdir)[id_fluid].fluid.fluid == null)return "empty";
				String b = ((IFluidHandler)te).getTankInfo(inputdir)[id_fluid].fluid.fluid.getLocalizedName();
				if(b == null)return "empty";
				return b;
			}
			else if(te instanceof IFluidTank)
			{
				if(((IFluidTank)te).getFluid() == null || ((IFluidTank)te).getFluid().fluid == null)return "empty";
				String b = ((IFluidTank)te).getFluid().fluid.getLocalizedName();
				if(b == null)return "empty";
				return b;
			}
			return "empty";
		}
	}

	@Override
	public void InfoFromServer(String name)
	{
		fluidname = name;
	}

	@Override
	public String GetSyntaxe()
	{
		return "FName: "+GetFluidName();
	}
}