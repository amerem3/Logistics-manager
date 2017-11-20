package com.enm.api.network;

public interface Machine_Redstone extends MachineNetWork
{
	public void SetOutputSinal(int sinal);
	public int GetOutputSinal();
	public int GetInputSinal();
	
	public boolean CanUseOutput();
	public boolean CanUseInput();
}
