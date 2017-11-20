package com.enm.tileEntity;

import com.enm.api.network.MachineNetWork;
import com.enm.api.util.ILinker;
import com.enm.api.util.Vector2;
import com.enm.api.util.Vector3;
import com.enm.guipanel.myinstallation.Map;
import com.enm.tileEntityutil.TileEntityENM;
import com.enm.util.Tools;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityControlPanel extends TileEntityENM implements MachineNetWork, ILinker
{
	public ForgeDirection inputdir;
	public short[] map = new short[4];
	public NBTTagCompound[] nbt_map = new NBTTagCompound[4];
	
	//public Vector2 pos_screen = new Vector2();
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("dir", Tools.ForgeDirectionToIntegerSide(inputdir));
		for(int id = 0; id < nbt_map.length; ++ id)
		{
			if(nbt_map[id] != null)
				nbt.setTag("maptag"+id, nbt_map[id]);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		inputdir = Tools.IntegerSideToForgeDirection(nbt.getInteger("dir"));
		for(int id = 0; id < nbt_map.length; ++ id)
		{
			if(nbt.hasKey("maptag"+id))
				nbt_map[id] = nbt.getCompoundTag("maptag"+id);
		}
	}

	@Override
	public String name()
	{
		return "ControlPanel 2x2";
	}

	@Override
	public boolean useLinker(String playerName, World world, int x, int y, int z, int side, float deltaX, float deltaY, float deltaZ, String machineType, Vector3 machinePos)
	{
		if(machineType == null)return false;
		int by = 1 - (int)(deltaY*2);
		int bx = 0;
		if(inputdir.ordinal() == 2)bx = 1-(int)(deltaX*2);
		if(inputdir.ordinal() == 3)bx = (int)(deltaX*2);
		if(inputdir.ordinal() == 4)bx = (int)(deltaZ*2);
		if(inputdir.ordinal() == 5)bx = 1-(int)(deltaZ*2);
		
		String icon = Map.Icon.values()[map[bx+(by*2)]].name();
		if(icon.contains("switch") && machineType.equals("Machine_Switch"))
		{
			//switch
			if(nbt_map[bx+(by*2)] == null)nbt_map[bx+(by*2)] = new NBTTagCompound();
			machinePos.SaveToNBT(nbt_map[bx+(by*2)], "pos");
		}
		System.out.println(nbt_map[bx+(by*2)]);
		
		return true;
	}

	@Override
	public TileEntity entity()
	{
		return this;
	}

	@Override
	public String CustomName()
	{
		return "";
	}

	@Override
	public String GetSyntaxe()
	{
		return "";
	}
}
