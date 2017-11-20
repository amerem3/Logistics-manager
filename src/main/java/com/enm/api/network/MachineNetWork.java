package com.enm.api.network;

import java.util.List;

import net.minecraft.tileentity.TileEntity;

public interface MachineNetWork extends NetWork
{
	public TileEntity entity();
	public String CustomName();
	public String GetSyntaxe();
}
