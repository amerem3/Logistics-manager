package com.enm.api.network;

import java.util.ArrayList;
import java.util.List;

import com.enm.api.util.Vector3;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class NetWorkUtil
{
	public static final Class[] machine_listing =
		{
				Machine_Counter.class,
				Machine_FluidCounter.class,
				Machine_FluidFlowMeter.class,
				Machine_FluidName.class,
				Machine_FluidStorageInfo.class,
				Machine_FluidValve.class,
				Machine_FluxMeter.class,
				Machine_Redstone.class,
				Machine_StorageInfo.class,
				Machine_Switch.class
		};
	public static Class<?> GetType(TileEntity te)
	{
		if(te instanceof Machine_Counter) return Machine_Counter.class;
		else if(te instanceof Machine_FluidCounter) return Machine_FluidCounter.class;
		else if(te instanceof Machine_FluidFlowMeter) return Machine_FluidFlowMeter.class;
		else if(te instanceof Machine_FluidName) return Machine_FluidName.class;
		else if(te instanceof Machine_FluidStorageInfo) return Machine_FluidStorageInfo.class;
		else if(te instanceof Machine_FluidValve) return Machine_FluidValve.class;
		else if(te instanceof Machine_FluxMeter) return Machine_FluxMeter.class;
		else if(te instanceof Machine_Redstone) return Machine_Redstone.class;
		else if(te instanceof Machine_StorageInfo) return Machine_StorageInfo.class;
		else if(te instanceof Machine_Switch) return Machine_Switch.class;
		else return null;
	}
	public static List<TileEntity> GetAllMachines(World world, int x, int y, int z)
	{
		List<TileEntity> machines = new ArrayList<TileEntity>();
		List<Vector3> visited = new ArrayList<Vector3>();
		scan(machines, visited, x, y, z, world);
		visited = null;
		return machines;
	}
	
	private static void scan(List<TileEntity> tes, List<Vector3> vecs, int x, int y, int z, World world)
	{
		if(world == null)return;
		TileEntity te = world.getTileEntity(x, y, z);
		if( !isVisited(vecs, te) && te instanceof NetWork)
		{
			vecs.add(new Vector3(te.xCoord, te.yCoord, te.zCoord));
			if(te instanceof MachineNetWork)tes.add(te);
			
			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			{
				scan(tes, vecs, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, world);
			}
		}
	}
	
	public static boolean isVisited(List<Vector3> list_vec, TileEntity this_te)
	{
		if(this_te != null)for(Vector3 v : list_vec)if(v.x == this_te.xCoord && v.y == this_te.yCoord && v.z == this_te.zCoord) return true;
		return false;
	}
}
