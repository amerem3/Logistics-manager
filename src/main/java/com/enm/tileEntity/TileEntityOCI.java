package com.enm.tileEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.enm.api.network.MachineNetWork;
import com.enm.api.network.Machine_Counter;
import com.enm.api.network.Machine_FluidCounter;
import com.enm.api.network.Machine_FluidFlowMeter;
import com.enm.api.network.Machine_FluidName;
import com.enm.api.network.Machine_FluidStorageInfo;
import com.enm.api.network.Machine_FluidValve;
import com.enm.api.network.Machine_FluxMeter;
import com.enm.api.network.Machine_Redstone;
import com.enm.api.network.Machine_StorageInfo;
import com.enm.api.network.Machine_Switch;
import com.enm.api.network.NetWork;
import com.enm.api.network.NetWorkUtil;
import com.enm.tileEntityutil.TileEntityENM;
import com.enm.util.Tools;

import li.cil.oc.api.Network;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.ComponentConnector;
import li.cil.oc.api.network.Environment;
import li.cil.oc.api.network.Message;
import li.cil.oc.api.network.Node;
import li.cil.oc.api.network.Visibility;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityOCI extends TileEntityENM implements Environment, MachineNetWork
{
	public ForgeDirection inputdir;
	protected ComponentConnector node = Network.newNode(this, Visibility.Network).withComponent(getComponentName()).withConnector(32).create();
	
	public List<TileEntity> machines;
	public List<TileEntity> virtualmachines = new ArrayList<TileEntity>();
	TileEntity machine_;
	
	private static String getComponentName()
	{
		return "networkmanager_oci";
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if (node != null && node.network() == null)
		{
			Network.joinOrCreateNetwork(this);
		}
	}
	
	@Override
	public void onChunkUnload()
	{
		super.onChunkUnload();
		if (node != null)
			node.remove();
	}

	@Override
	public void invalidate()
	{
		super.invalidate();
		if (node != null)
			node.remove();
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("dir", Tools.ForgeDirectionToIntegerSide(inputdir));
		if (node != null && node.host() == this)
		{
			final NBTTagCompound nodeNbt = new NBTTagCompound();
			node.save(nodeNbt);
			nbt.setTag("oc:node", nodeNbt);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		inputdir = Tools.IntegerSideToForgeDirection(nbt.getInteger("dir"));
		if (node != null && node.host() == this)
		{
			node.load(nbt.getCompoundTag("oc:node"));
		}
	}

	@Override
	public Node node()
	{
		return node;
	}

	@Override
	public void onConnect(Node node)
	{
		
	}

	@Override
	public void onDisconnect(Node node)
	{
		
	}

	@Override
	public void onMessage(Message message)
	{
		
	}
	
	@Callback
	public Object[] loadlist(Context context, Arguments args)
	{
		machines = NetWorkUtil.GetAllMachines(worldObj, xCoord, yCoord, zCoord);
		return new Object[]{true};
	}
	
	@Callback
	public Object[] size(Context context, Arguments args)
	{
		if (machines != null) return new Object[]{machines.size()};
		return new Object[]{0};
	}
	
	@Callback
	public Object[] machineid(Context context, Arguments args)
	{
		if (machines != null)
		{
			if(args.count() == 1 && args.isString(0))
			{
				int id = 0;
				for(TileEntity te : machines)
				{
					if(te instanceof MachineNetWork && ((MachineNetWork)te).CustomName().equals(args.checkString(0)))
					{
						return new Object[]{id};
					}
					++id;
				}
			}
			else if(args.count() == 3 && args.isInteger(0) && args.isInteger(1) && args.isInteger(2))
			{
				int id = 0;
				for(TileEntity te : machines)
				{
					if(te instanceof MachineNetWork && ((MachineNetWork)te).entity().xCoord == args.checkInteger(0) && ((MachineNetWork)te).entity().yCoord == args.checkInteger(1) && ((MachineNetWork)te).entity().zCoord == args.checkInteger(2))
					{
						return new Object[]{id};
					}
					++id;
				}
			}
		}
		return new Object[]{-1};
	}
	
	@Callback
	public Object[] loadmachine(Context context, Arguments args)
	{
		if (machines != null)
		{
			if(args.count() == 1 && args.isInteger(0) && args.checkInteger(0) < machines.size())
			{
				machine_ = machines.get(args.checkInteger(0));
				return new Object[]{true};
			}
			else if(args.count() == 1 && args.isString(0))
			{
				for(TileEntity te : machines)
				{
					if(te instanceof MachineNetWork && ((MachineNetWork)te).CustomName().equals(args.checkString(0)))
					{
						machine_ = te;
						return new Object[]{true};
					}
				}
			}
			else if(args.count() == 3 && args.isInteger(0) && args.isInteger(1) && args.isInteger(2))
			{
				for(TileEntity te : machines)
				{
					if(te instanceof MachineNetWork && ((MachineNetWork)te).entity().xCoord == args.checkInteger(0) && ((MachineNetWork)te).entity().yCoord == args.checkInteger(1) && ((MachineNetWork)te).entity().zCoord == args.checkInteger(2))
					{
						machine_ = te;
						return new Object[]{true};
					}
				}
			}
		}
		return new Object[]{false};
	}
	
	@Callback
	public Object[] machineisloaded(Context context, Arguments args)
	{
		return new Object[]{machine_ != null ? true : false};
	}
	
	@Callback
	public Object[] unloadmachine(Context context, Arguments args)
	{
		if(machine_ != null)
		{
			machine_ = null;
		}
		return new Object[]{};
	}
	
	@Callback
	public Object[] callmachine(Context context, Arguments args)
	{
		if(machine_ != null && args.count() > 0 && args.isString(0))
		{
			String method = args.checkString(0);
			if(machine_ instanceof Machine_Counter)
			{
				if(method.equals("GetConsomation"))return new Object[]{((Machine_Counter)machine_).GetConsomation()};
				else if(method.equals("GetSyntaxe"))return new Object[]{((Machine_Counter)machine_).GetSyntaxe()};
				else if(method.equals("CustomName"))return new Object[]{((Machine_Counter)machine_).CustomName()};
				else if(method.equals("name"))return new Object[]{((Machine_Counter)machine_).name()};
				else if(method.equals("Reset"))
				{
					((Machine_Counter)machine_).Reset();
					return new Object[]{true};
				}
			}
			else if(machine_ instanceof Machine_FluidCounter)
			{
				if(method.equals("GetConsomation"))return new Object[]{((Machine_FluidCounter)machine_).GetConsomation()};
				else if(method.equals("GetSyntaxe"))return new Object[]{((Machine_FluidCounter)machine_).GetSyntaxe()};
				else if(method.equals("CustomName"))return new Object[]{((Machine_FluidCounter)machine_).CustomName()};
				else if(method.equals("name"))return new Object[]{((Machine_FluidCounter)machine_).name()};
				else if(method.equals("Reset"))
				{
					((Machine_FluidCounter)machine_).Reset();
					return new Object[]{true};
				}
			}
			else if(machine_ instanceof Machine_FluidFlowMeter)
			{
				if(method.equals("GetFlow"))return new Object[]{((Machine_FluidFlowMeter)machine_).GetFlow()};
				else if(method.equals("GetSyntaxe"))return new Object[]{((Machine_FluidFlowMeter)machine_).GetSyntaxe()};
				else if(method.equals("CustomName"))return new Object[]{((Machine_FluidFlowMeter)machine_).CustomName()};
				else if(method.equals("name"))return new Object[]{((Machine_FluidFlowMeter)machine_).name()};
			}
			else if(machine_ instanceof Machine_FluidName)
			{
				if(method.equals("GetFluidName"))return new Object[]{((Machine_FluidName)machine_).GetFluidName()};
				else if(method.equals("CustomName"))return new Object[]{((Machine_FluidName)machine_).CustomName()};
				else if(method.equals("name"))return new Object[]{((Machine_FluidName)machine_).name()};
			}
			else if(machine_ instanceof Machine_FluidStorageInfo)
			{
				if(method.equals("GetSyntaxe"))return new Object[]{((Machine_FluidStorageInfo)machine_).GetSyntaxe()};
				else if(method.equals("CustomName"))return new Object[]{((Machine_FluidStorageInfo)machine_).CustomName()};
				else if(method.equals("name"))return new Object[]{((Machine_FluidStorageInfo)machine_).name()};
			}
			else if(machine_ instanceof Machine_FluidValve)
			{
				if(method.equals("ValveGetPosition"))return new Object[]{((Machine_FluidValve)machine_).ValveGetPosition()};
				else if(method.equals("GetSyntaxe"))return new Object[]{((Machine_FluidValve)machine_).GetSyntaxe()};
				else if(method.equals("CustomName"))return new Object[]{((Machine_FluidValve)machine_).CustomName()};
				else if(method.equals("name"))return new Object[]{((Machine_FluidValve)machine_).name()};
				else if(method.equals("ValveOpenClose") && args.count() > 1 && args.isBoolean(1))
				{
					((Machine_FluidValve)machine_).ValveOpenClose(args.checkBoolean(1));
					return new Object[]{true};
				}
			}
			else if(machine_ instanceof Machine_FluxMeter)
			{
				if(method.equals("GetFlux"))return new Object[]{((Machine_FluxMeter)machine_).GetFlux()};
				else if(method.equals("GetSyntaxe"))return new Object[]{((Machine_FluxMeter)machine_).GetSyntaxe()};
				else if(method.equals("CustomName"))return new Object[]{((Machine_FluxMeter)machine_).CustomName()};
				else if(method.equals("name"))return new Object[]{((Machine_FluxMeter)machine_).name()};
			}
			else if(machine_ instanceof Machine_StorageInfo)
			{
				if(method.equals("GetEnergyStored"))return new Object[]{((Machine_StorageInfo)machine_).GetEnergyStored()};
				else if(method.equals("GetMaxEnergyStored"))return new Object[]{((Machine_StorageInfo)machine_).GetMaxEnergyStored()};
				else if(method.equals("GetSyntaxe"))return new Object[]{((Machine_StorageInfo)machine_).GetSyntaxe()};
				else if(method.equals("CustomName"))return new Object[]{((Machine_StorageInfo)machine_).CustomName()};
				else if(method.equals("name"))return new Object[]{((Machine_StorageInfo)machine_).name()};
			}
			else if(machine_ instanceof Machine_Switch)
			{
				if(method.equals("SwitchGetPosition"))return new Object[]{((Machine_Switch)machine_).SwitchGetPosition()};
				else if(method.equals("GetSyntaxe"))return new Object[]{((Machine_Switch)machine_).GetSyntaxe()};
				else if(method.equals("CustomName"))return new Object[]{((Machine_Switch)machine_).CustomName()};
				else if(method.equals("name"))return new Object[]{((Machine_Switch)machine_).name()};
				else if(method.equals("SwitchONOFF") && args.count() > 1 && args.isBoolean(1))
				{
					((Machine_Switch)machine_).SwitchONOFF(args.checkBoolean(1));
					return new Object[]{true};
				}
			}
			else if(machine_ instanceof MachineNetWork)
			{
				if(method.equals("CustomName"))return new Object[]{((MachineNetWork)machine_).CustomName()};
				else if(method.equals("name"))return new Object[]{((MachineNetWork)machine_).name()};
			}
			else if(machine_ instanceof NetWork)
			{
				if(method.equals("name"))return new Object[]{((NetWork)machine_).name()};
			}
		}
		return new Object[]{null};
	}
	
	@Callback
	public Object[] getfunctionmachine(Context context, Arguments args)
	{
		if(machine_ instanceof Machine_Counter)return new Object[]{
			"GetConsomation",
			"GetSyntaxe",
			"CustomName",
			"name",
			"Reset"};
		else if(machine_ instanceof Machine_FluidCounter)return new Object[]{
			"GetConsomation",
			"GetSyntaxe",
			"CustomName",
			"name",
			"Reset"};
		else if(machine_ instanceof Machine_FluidFlowMeter)return new Object[]{
			"GetFlow",
			"GetSyntaxe",
			"CustomName",
			"name"};
		else if(machine_ instanceof Machine_FluidName)return new Object[]{
			"GetFluidName",
			"CustomName",
			"name"};
		else if(machine_ instanceof Machine_FluidStorageInfo)return new Object[]{
			"GetSyntaxe",
			"CustomName",
			"name"};
		else if(machine_ instanceof Machine_FluidValve)return new Object[]{
			"ValveGetPosition",
			"GetSyntaxe",
			"CustomName",
			"name",
			"ValveOpenClose"};
		else if(machine_ instanceof Machine_FluxMeter)return new Object[]{
			"GetFlux",
			"GetSyntaxe",
			"CustomName",
			"name"};
		else if(machine_ instanceof Machine_StorageInfo)return new Object[]{
			"GetEnergyStored",
			"GetMaxEnergyStored",
			"GetSyntaxe",
			"CustomName",
			"name"};
		else if(machine_ instanceof Machine_Switch)return new Object[]{
			"SwitchGetPosition",
			"GetSyntaxe",
			"CustomName",
			"name",
			"SwitchONOFF"};
		else if(machine_ instanceof MachineNetWork)return new Object[]{
			"CustomName",
			"name"};
		else if(machine_ instanceof NetWork)return new Object[]{
			"name"};
			
		return new Object[]{};
	}
	
	@Callback
	public Object[] getdocfunctionmachine(Context context, Arguments args)
	{
		if(machine_ instanceof Machine_Counter)return new Object[]{
			"GetConsomation() return long",
			"GetSyntaxe() return String",
			"CustomName() return String",
			"name() return String",
			"Reset() return boolean[true]"};
		else if(machine_ instanceof Machine_FluidCounter)return new Object[]{
			"GetConsomation() return long",
			"GetSyntaxe() return String",
			"CustomName() return String",
			"name() return String",
			"Reset() return boolean[true]"};
		else if(machine_ instanceof Machine_FluidFlowMeter)return new Object[]{
			"GetFlow() return int",
			"GetSyntaxe() return String",
			"CustomName() return String",
			"name() return String"};
		else if(machine_ instanceof Machine_FluidName)return new Object[]{
			"GetFluidName() return String",
			"CustomName() return String",
			"name() return String"};
		else if(machine_ instanceof Machine_FluidStorageInfo)return new Object[]{
			"GetSyntaxe() return String",
			"CustomName() return String",
			"name() return String"};
		else if(machine_ instanceof Machine_FluidValve)return new Object[]{
			"ValveGetPosition() return boolean",
			"GetSyntaxe() return String",
			"CustomName() return String",
			"name() return String",
			"ValveOpenClose(boolean pos) return boolean[true]"};
		else if(machine_ instanceof Machine_FluxMeter)return new Object[]{
			"GetFlux() return int",
			"GetSyntaxe() return String",
			"CustomName() return String",
			"name() return String"};
		else if(machine_ instanceof Machine_StorageInfo)return new Object[]{
			"GetEnergyStored() return int",
			"GetMaxEnergyStored() return int",
			"GetSyntaxe() return String",
			"CustomName() return String",
			"name() return String"};
		else if(machine_ instanceof Machine_Switch)return new Object[]{
			"SwitchGetPosition() return boolean",
			"GetSyntaxe() return String",
			"CustomName() return String",
			"name() return String",
			"SwitchONOFF(boolean pos) return boolean[true]"};
		else if(machine_ instanceof MachineNetWork)return new Object[]{
			"CustomName() return String",
			"name() return String"};
		else if(machine_ instanceof NetWork)return new Object[]{
			"name() return String"};
			
		return new Object[]{};
	}
	
	@Override
	public String name()
	{
		return "OCI";
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
