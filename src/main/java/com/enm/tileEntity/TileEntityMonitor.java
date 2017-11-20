package com.enm.tileEntity;

import java.util.List;

import com.enm.api.network.ListMachinesUpdate;
import com.enm.api.network.MachineNetWork;
import com.enm.api.network.NetWorkUtil;
import com.enm.api.util.Vector3;
import com.enm.tileEntityutil.TEServerUpdate;
import com.enm.tileEntityutil.TileEntityENM;
import com.enm.util.Tools;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMonitor extends TileEntityENM implements MachineNetWork, ListMachinesUpdate, TEServerUpdate
{
	public ForgeDirection inputdir;
	
	public TileEntity machine;
	public Vector3 pos = new Vector3();
	public byte timeReload = 10;
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("dir", Tools.ForgeDirectionToIntegerSide(inputdir));
		if(!pos.ZeroPoint())pos.SaveToNBT(nbt, "pos");
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		inputdir = Tools.IntegerSideToForgeDirection(nbt.getInteger("dir"));
		pos.ReadFromNBT(nbt, "pos");
		
		onEventListMachinesUpdate();
	}

	@Override
	public String name()
	{
		return "Monitor";
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
		return "";
	}

	@Override
	public void onEventListMachinesUpdate()
	{
		timeReload = 10;
	}
	
	@Override
	public void updateEntity()
	{
		if(timeReload > 0)
		{
			--timeReload;
			if(timeReload == 0)
			{
				updateMachineList();
			}
		}
	}

	private void updateMachineList()
	{
		machine = null;
		List<TileEntity> machines = NetWorkUtil.GetAllMachines(worldObj, xCoord, yCoord, zCoord);
		for(TileEntity te : machines)
		{
			if(pos.x == te.xCoord && pos.y == te.yCoord && pos.z == te.zCoord)
			{
				machine = te;
				break;
			}
		}
	}
}
