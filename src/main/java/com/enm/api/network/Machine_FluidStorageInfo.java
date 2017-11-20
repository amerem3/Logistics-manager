package com.enm.api.network;

import net.minecraftforge.fluids.FluidTankInfo;

public interface Machine_FluidStorageInfo extends MachineNetWork
{
	public FluidTankInfo[] GetFluidStoredInfo();
	public void InfoFromServer(String sntx);
}
