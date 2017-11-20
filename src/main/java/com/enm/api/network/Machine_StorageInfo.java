package com.enm.api.network;

public interface Machine_StorageInfo extends MachineNetWork
{
	public int GetEnergyStored();
	public int GetMaxEnergyStored();
	public void InfoFromServer(int Estored, int MaxEstored);
}
