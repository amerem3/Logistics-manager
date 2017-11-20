package com.enm.util;

import net.minecraft.nbt.NBTTagCompound;

public class Temperature
{
	private float min_temperature, max_temperature, temperature;
	
	public Temperature(float _mintemp, float _maxtemp, float _starttemp)
	{
		min_temperature = _mintemp;
		max_temperature = _maxtemp;
		temperature = _starttemp;
	}
	
	public float GetRestToHeat()
	{
		return max_temperature - temperature;
	}
	
	public float GetRestToCool()
	{
		return temperature - min_temperature;
	}
	
	public boolean ConsumeHeat(float tempconsum)
	{
		if(GetRestToCool() - tempconsum >= 0)
		{
			temperature -= tempconsum;
			return true;
		}
		return false;
	}
	
	public boolean HeatUp(float tempup)
	{
		if(GetRestToHeat() > 0)
		{
			temperature += tempup;
			return true;
		}
		return false;
	}
	
	public float GetMaxTemperature()
	{
		return max_temperature;
	}
	
	public void SetMaxTemperature(float f)
	{
		max_temperature = f;
	}
	
	public float GetMinTemperature()
	{
		return min_temperature;
	}
	
	public void SetMinTemperature(float f)
	{
		min_temperature = f;
	}
	
	public float GetTemperature()
	{
		return temperature;
	}
	
	public void SetTemperature(float f)
	{
		temperature = f;
	}
}
