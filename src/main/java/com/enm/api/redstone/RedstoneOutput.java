package com.enm.api.redstone;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class RedstoneOutput
{
	private int redstone_sinal = 0;
	private int redstone_buffer = -1;
	private boolean nbtascheked = false;
	
	public void updateEntity(World worldObj, int xCoord, int yCoord, int zCoord)
	{
		//redstone_sinal = 15;
		if(redstone_sinal != redstone_buffer)
		{
			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			{
				if(worldObj.getBlockPowerInput(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ) == redstone_sinal)
				{
					//worldObj.notifyBlockOfNeighborChange(xCoord, yCoord, zCoord, blockType);
					redstone_buffer = redstone_sinal;
					break;
				}
			}
			//redstone_buffer = redstone_sinal;
			worldObj.notifyBlockChange(xCoord, yCoord, zCoord, worldObj.getBlock(xCoord, yCoord, zCoord));
			if(nbtascheked)worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}
	
	public void setRedstoneSinal(int sinal)
	{
		redstone_sinal = sinal;
	}
	
	public int getRedstoneSinal()
	{
		return redstone_sinal;
	}
	
	public void writeToNBT(NBTTagCompound nbt)
	{
		if(nbtascheked)
		nbt.setInteger("redStoneSinal", redstone_sinal);
	}
	
	public void readFromNBT(NBTTagCompound nbt)
	{
		redstone_sinal = nbt.getInteger("redStoneSinal");
		nbtascheked = true;
	}
}
