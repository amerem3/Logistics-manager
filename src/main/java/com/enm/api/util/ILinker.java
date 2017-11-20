package com.enm.api.util;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.World;

public interface ILinker
{
	public boolean useLinker(String playerName, World world, int x, int y, int z, int side, float deltaX, float deltaY, float deltaZ, String machineType, Vector3 machinePos);
}
