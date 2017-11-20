package com.enm.api.network;

public interface Machine_FluidCounter extends MachineNetWork
{
	public long GetConsomation();
	public void Reset();
	public void InfoFromServer(long consomation);
}
