package com.enm.guipanel.myinstallation;

import java.util.List;

import com.enm.api.network.MachineNetWork;

import net.minecraft.tileentity.TileEntity;

public class VirtualMachine implements MachineNetWork
{
	String s1,s2, sntx;
	public VirtualMachine(String type, String customname)
	{
		s1 = type;
		s2 = customname;
		sntx = "";
	}

	@Override
	public String name()
	{
		return s1;
	}

	@Override
	public TileEntity entity()
	{
		return null;
	}

	@Override
	public String CustomName()
	{
		return s2;
	}

	@Override
	public String GetSyntaxe()
	{
		return sntx;
	}
}
