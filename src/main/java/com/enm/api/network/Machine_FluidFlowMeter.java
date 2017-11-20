package com.enm.api.network;

public interface Machine_FluidFlowMeter extends MachineNetWork
{
	public int GetFlow();
	public void InfoFromServer(int flow);
}
