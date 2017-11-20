package com.enm.tileEntity;

import java.util.List;

import com.enm.api.network.ListMachinesUpdate;
import com.enm.api.network.MachineNetWork;
import com.enm.api.network.Machine_Counter;
import com.enm.api.network.Machine_FluidCounter;
import com.enm.api.network.Machine_FluidFlowMeter;
import com.enm.api.network.Machine_FluidName;
import com.enm.api.network.Machine_FluidStorageInfo;
import com.enm.api.network.Machine_FluidValve;
import com.enm.api.network.Machine_FluxMeter;
import com.enm.api.network.Machine_StorageInfo;
import com.enm.api.network.Machine_Switch;
import com.enm.api.network.NetWorkUtil;
import com.enm.api.redstone.IRedstoneOutput;
import com.enm.api.redstone.RedstoneOutput;
import com.enm.api.util.Vector3;
import com.enm.core.CoreMod;
import com.enm.network.NetWorkTileEntityNBT;
import com.enm.tileEntityutil.TileEntityENM;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityRedstoneOut extends TileEntityENM implements IRedstoneOutput, ListMachinesUpdate, MachineNetWork
{
	public RedstoneOutput redstone = new RedstoneOutput();
	byte timeReload = 4;
	
	public TileEntity machine;
	public Vector3 posmachine = new Vector3();
	public String machine_type = "";
	
	public byte VarType = 0;
	public byte ComparType = 0;
	public boolean var0;
	public String var1;
	public int Var2;
	public long Var3;
	public int methode = 0;
	//long lasttime = 0l;
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		
		redstone.writeToNBT(nbt);
		if(!posmachine.ZeroPoint())posmachine.SaveToNBT(nbt, "MachPos");
		
		nbt.setByte("VT", VarType);
		nbt.setByte("CT", ComparType);
		nbt.setBoolean("V0", var0);
		if(var1 != null)
			nbt.setString("V1", var1);
		nbt.setInteger("V2", Var2);
		nbt.setLong("V3", Var3);
		nbt.setInteger("MET", methode);
	}
	
	public void writeToNBTServer(NBTTagCompound nbt)
	{
		nbt.setByte("VT", VarType);
		nbt.setByte("CT", ComparType);
		nbt.setBoolean("V0", var0);
		if(var1 != null)
			nbt.setString("V1", var1);
		nbt.setInteger("V2", Var2);
		nbt.setLong("V3", Var3);
		nbt.setInteger("MET", methode);
		posmachine.SaveToNBT(nbt, "MachPos");
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		//System.out.println(posmachine.GetOutPutConsole());
		super.readFromNBT(nbt);
		
		if(nbt.hasKey("redStoneSinal"))redstone.readFromNBT(nbt);
		posmachine.ReadFromNBT(nbt, "MachPos");
		
		VarType = nbt.getByte("VT");
		ComparType = nbt.getByte("CT");
		var0 = nbt.getBoolean("V0");
		var1 = nbt.getString("V1");
		Var2 = nbt.getInteger("V2");
		Var3 = nbt.getLong("V3");
		methode = nbt.getInteger("MET");
		
		updateMachineList();
	}
	
	public void updateInventory()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBTServer(nbt);
		CoreMod.network_TileEntityNBT.sendToServer(new NetWorkTileEntityNBT(xCoord, yCoord, zCoord, nbt));
	}

	@Override
	public int getRedstoneSinal()
	{
		return redstone.getRedstoneSinal();
	}
	
	@Override
	public void updateEntity()
	{
		redstone.updateEntity(worldObj, xCoord, yCoord, zCoord);
		if(timeReload > 0)
		{
			--timeReload;
			if(timeReload == 0)
			{
				updateMachineList();
			}
		}
		
		//event
		if(machine != null)
		{
			if(machine instanceof Machine_Counter && VarType == 3)
			{
				//Consomation long
				long flx = ((Machine_Counter)machine).GetConsomation();
				if(ComparType == 0)
				{
					if(flx == Var3)
					{
						redstone.setRedstoneSinal(15);
					}
					else
					{
						redstone.setRedstoneSinal(0);
					}
				}
				else if(ComparType == 1)
				{
					if(flx < Var3)
					{
						redstone.setRedstoneSinal(15);
					}
					else
					{
						redstone.setRedstoneSinal(0);
					}
				}
				else if(ComparType == 2)
				{
					if(flx > Var3)
					{
						redstone.setRedstoneSinal(15);
					}
					else
					{
						redstone.setRedstoneSinal(0);
					}
				}
			}
			else if(machine instanceof Machine_FluidCounter && VarType == 3)
			{
				//Consomation long
				long flx = ((Machine_FluidCounter)machine).GetConsomation();
				if(ComparType == 0)
				{
					if(flx == Var3)
					{
						redstone.setRedstoneSinal(15);
					}
					else
					{
						redstone.setRedstoneSinal(0);
					}
				}
				else if(ComparType == 1)
				{
					if(flx < Var3)
					{
						redstone.setRedstoneSinal(15);
					}
					else
					{
						redstone.setRedstoneSinal(0);
					}
				}
				else if(ComparType == 2)
				{
					if(flx > Var3)
					{
						redstone.setRedstoneSinal(15);
					}
					else
					{
						redstone.setRedstoneSinal(0);
					}
				}
			}
			else if(machine instanceof Machine_FluidFlowMeter && VarType == 2)
			{
				//Flow int
				int flx = ((Machine_FluidFlowMeter)machine).GetFlow();
				if(ComparType == 0)
				{
					if(flx == Var2)
					{
						redstone.setRedstoneSinal(15);
					}
					else
					{
						redstone.setRedstoneSinal(0);
					}
				}
				else if(ComparType == 1)
				{
					if(flx < Var2)
					{
						redstone.setRedstoneSinal(15);
					}
					else
					{
						redstone.setRedstoneSinal(0);
					}
				}
				else if(ComparType == 2)
				{
					if(flx > Var2)
					{
						redstone.setRedstoneSinal(15);
					}
					else
					{
						redstone.setRedstoneSinal(0);
					}
				}
			}
			else if(machine instanceof Machine_FluidName && VarType == 1)
			{
				//FluidName String
				if(ComparType == 0)
				{
					if(((Machine_FluidName)machine).GetFluidName().equals(var1))
					{
						redstone.setRedstoneSinal(15);
					}
					else
					{
						redstone.setRedstoneSinal(0);
					}
				}
				else if(ComparType == 1)
				{
					if(((Machine_FluidName)machine).GetFluidName() != var1)
					{
						redstone.setRedstoneSinal(15);
					}
					else
					{
						redstone.setRedstoneSinal(0);
					}
				}
				else if(ComparType == 2)
				{
					if(((Machine_FluidName)machine).GetFluidName().equalsIgnoreCase(var1))
					{
						redstone.setRedstoneSinal(15);
					}
					else
					{
						redstone.setRedstoneSinal(0);
					}
				}
			}
			else if(machine instanceof Machine_FluidStorageInfo)
			{
				//FluidAmount ([]) int
				//FluidCapacity ([]) int
				//FluidName ([]) String
				if(methode == 0 && VarType == 2)
				{
					if(((Machine_FluidStorageInfo)machine).GetFluidStoredInfo() != null && ((Machine_FluidStorageInfo)machine).GetFluidStoredInfo().length > 0)
					{
						int va = ((Machine_FluidStorageInfo)machine).GetFluidStoredInfo()[0].fluid.amount;
						if(ComparType == 0)
						{
							if(va == Var2) redstone.setRedstoneSinal(15);
							else redstone.setRedstoneSinal(0);
						}
						else if(ComparType == 1)
						{
							if(va < Var2) redstone.setRedstoneSinal(15);
							else redstone.setRedstoneSinal(0);
						}
						else if(ComparType == 2)
						{
							if(va > Var2) redstone.setRedstoneSinal(15);
							else redstone.setRedstoneSinal(0);
						}
					}
				}
				else if(methode == 1 && VarType == 2)
				{
					if(((Machine_FluidStorageInfo)machine).GetFluidStoredInfo() != null && ((Machine_FluidStorageInfo)machine).GetFluidStoredInfo().length > 0)
					{
						int va = ((Machine_FluidStorageInfo)machine).GetFluidStoredInfo()[0].capacity;
						if(ComparType == 0)
						{
							if(va == Var2) redstone.setRedstoneSinal(15);
							else redstone.setRedstoneSinal(0);
						}
						else if(ComparType == 1)
						{
							if(va < Var2) redstone.setRedstoneSinal(15);
							else redstone.setRedstoneSinal(0);
						}
						else if(ComparType == 2)
						{
							if(va > Var2) redstone.setRedstoneSinal(15);
							else redstone.setRedstoneSinal(0);
						}
					}
				}
				else if(methode == 2 && VarType == 1)
				{
					if(((Machine_FluidStorageInfo)machine).GetFluidStoredInfo() != null && ((Machine_FluidStorageInfo)machine).GetFluidStoredInfo().length > 0)
					{
						String va = ((Machine_FluidStorageInfo)machine).GetFluidStoredInfo()[0].fluid.fluid.getLocalizedName();
						if(ComparType == 0)
						{
							if(va == var1) redstone.setRedstoneSinal(15);
							else redstone.setRedstoneSinal(0);
						}
						else if(ComparType == 1)
						{
							if(va != var1) redstone.setRedstoneSinal(15);
							else redstone.setRedstoneSinal(0);
						}
						else if(ComparType == 2)
						{
							if(va.equalsIgnoreCase(var1)) redstone.setRedstoneSinal(15);
							else redstone.setRedstoneSinal(0);
						}
					}
				}
			}
			else if(machine instanceof Machine_FluidValve && VarType == 0)//OK
			{
				//ValvePosition boolean
				if(((Machine_FluidValve)machine).ValveGetPosition() == var0)
				{
					redstone.setRedstoneSinal(15);
				}
				else
				{
					redstone.setRedstoneSinal(0);
				}
			}
			else if(machine instanceof Machine_FluxMeter && VarType == 2)
			{
				//Flux int
				int flx = ((Machine_FluxMeter)machine).GetFlux();
				if(ComparType == 0)
				{
					if(flx == Var2)
					{
						redstone.setRedstoneSinal(15);
					}
					else
					{
						redstone.setRedstoneSinal(0);
					}
				}
				else if(ComparType == 1)
				{
					if(flx < Var2)
					{
						redstone.setRedstoneSinal(15);
					}
					else
					{
						redstone.setRedstoneSinal(0);
					}
				}
				else if(ComparType == 2)
				{
					if(flx > Var2)
					{
						redstone.setRedstoneSinal(15);
					}
					else
					{
						redstone.setRedstoneSinal(0);
					}
				}
				
			}
			else if(machine instanceof Machine_StorageInfo && VarType == 2)
			{
				//EnergyStored int 0
				//MaxEnergyStored int 1
				if(methode == 0)
				{
					int va = ((Machine_StorageInfo)machine).GetEnergyStored();
					if(ComparType == 0)
					{
						if(va == Var2) redstone.setRedstoneSinal(15);
						else redstone.setRedstoneSinal(0);
					}
					else if(ComparType == 1)
					{
						if(va < Var2) redstone.setRedstoneSinal(15);
						else redstone.setRedstoneSinal(0);
					}
					else if(ComparType == 2)
					{
						if(va > Var2) redstone.setRedstoneSinal(15);
						else redstone.setRedstoneSinal(0);
					}
				}
				else if(methode == 1)
				{
					int va = ((Machine_StorageInfo)machine).GetMaxEnergyStored();
					if(ComparType == 0)
					{
						if(va == Var2) redstone.setRedstoneSinal(15);
						else redstone.setRedstoneSinal(0);
					}
					else if(ComparType == 1)
					{
						if(va < Var2) redstone.setRedstoneSinal(15);
						else redstone.setRedstoneSinal(0);
					}
					else if(ComparType == 2)
					{
						if(va > Var2) redstone.setRedstoneSinal(15);
						else redstone.setRedstoneSinal(0);
					}
				}
			}
			else if(machine instanceof Machine_Switch && VarType == 0)//OK
			{
				//SwitchPosition boolean
				if(((Machine_Switch)machine).SwitchGetPosition() == var0)
				{
					redstone.setRedstoneSinal(15);
				}
				else
				{
					redstone.setRedstoneSinal(0);
				}
			}
		}
	}
	
	private void updateMachineList()
	{
		machine = null;
		List<TileEntity> machines = NetWorkUtil.GetAllMachines(worldObj, xCoord, yCoord, zCoord);
		for(TileEntity te : machines)
		{
			//System.out.println(posmachine.GetOutPutConsole());
			if(posmachine.x == te.xCoord && posmachine.y == te.yCoord && posmachine.z == te.zCoord)
			{
				machine = te;
				machine_type = NetWorkUtil.GetType(te).getSimpleName();
				break;
			}
		}
	}

	@Override
	public void onEventListMachinesUpdate()
	{
		timeReload = 40;
	}

	@Override
	public String name()
	{
		return "Redstone Output";
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
}
