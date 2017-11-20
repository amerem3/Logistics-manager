package com.enm.api.network;

public interface Machine_Switch extends MachineNetWork
{
	/**
	 * true = ON </br>
	 * false = OFF
	 */
	public void SwitchONOFF(boolean OnOff);
	/**
	 * true = ON </br>
	 * false = OFF
	 */
	public boolean SwitchGetPosition();

	public void InfoFromServer(boolean pos);
}
