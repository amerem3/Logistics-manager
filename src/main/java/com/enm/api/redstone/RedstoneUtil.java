package com.enm.api.redstone;

import com.enm.block.BlockTesting.TileEntityTesting;
import com.enm.util.Tools;

import net.minecraft.world.IBlockAccess;

public class RedstoneUtil
{
	public static int isProvidingStrongPower(IBlockAccess ba, int x, int y, int z, int m)
	{
		if(Tools.GetTileEntityOnServer(x, y, z) instanceof IRedstoneOutput)
			return ((IRedstoneOutput)Tools.GetTileEntityOnServer(x, y, z)).getRedstoneSinal();
		return 0;
		
	}
	
	public static int isProvidingWeakPower(IBlockAccess ba, int x, int y, int z, int m)
	{
		if(Tools.GetTileEntityOnServer(x, y, z) instanceof IRedstoneOutput)
			return ((IRedstoneOutput)Tools.GetTileEntityOnServer(x, y, z)).getRedstoneSinal();
		return 0;
		
	}
}
