package com.enm.api.network;

public interface Machine_FluxMeter extends MachineNetWork
{
	public int GetFlux();
	public void InfoFromServer(int flux);
}
