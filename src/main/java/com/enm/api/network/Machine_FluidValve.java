package com.enm.api.network;

public interface Machine_FluidValve extends MachineNetWork
{
	/**
	 * true = Open </br>
	 * false = Close
	 */
	public void ValveOpenClose(boolean OC);
	/**
	 * true = Open </br>
	 * false = Close
	 */
	public boolean ValveGetPosition();
	
	public void InfoFromServer(boolean pos);
}
