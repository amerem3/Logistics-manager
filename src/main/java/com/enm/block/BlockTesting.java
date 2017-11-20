package com.enm.block;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.enm.api.network.MachineNetWork;
import com.enm.api.network.Machine_StorageInfo;
import com.enm.api.network.NetWorkUtil;
import com.enm.block.util.BlockENM;
import com.enm.core.CoreMod;
import com.enm.guiutil.ResourceLocationRegister;
import com.enm.model.ModelLogicMachineFrame;
import com.enm.model.ModelTerminal;
import com.enm.tileEntity.TileEntityTerminal;
import com.enm.tileEntityutil.TSR;
import com.enm.util.Tools;

import cpw.mods.fml.common.IFuelHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class BlockTesting extends BlockENM
{
	public BlockTesting()
	{
		super(Material.iron);
		//this.setTickRandomly(true);
	}

	@Override
	public String DisplayBlockName()
	{
		return "Testing";
	}

	@Override
	public String BlockClassName()
	{
		return getClass().getSimpleName();
	}

	@Override
	public TileEntity createNewTileEntity(World world, int id)
	{
		return new TileEntityTesting();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float fx, float fy, float fz)
	{
		if(world.getTileEntity(x, y, z) instanceof TileEntityTesting)
		{
			TileEntityTesting te = (TileEntityTesting)world.getTileEntity(x, y, z);
			if(te.redstone_sinal < 15)
			{
				++te.redstone_sinal;
			}
			else
				te.redstone_sinal = 0;
		}
		return true;
		
	}
	
	//redstone
	
	public boolean canProvidePower()
    {
        return true;
    }
	
	public int isProvidingWeakPower(IBlockAccess ba, int x, int y, int z, int m)
	{
		if(Tools.GetTileEntityOnServer(x, y, z) instanceof TileEntityTesting)
			return ((TileEntityTesting)Tools.GetTileEntityOnServer(x, y, z)).redstone_sinal;
		
		return 0;
	}

	public int isProvidingStrongPower(IBlockAccess ba, int x, int y, int z, int m)
	{
		if(Tools.GetTileEntityOnServer(x, y, z) instanceof TileEntityTesting)
			return ((TileEntityTesting)Tools.GetTileEntityOnServer(x, y, z)).redstone_sinal;
			
		return 0;
		 //return ba.getBlockMetadata(x, y, z) == 2 ? 15 : 0;
	}
	
	public class TileEntityTesting extends TileEntity
	{
		public int redstone_sinal = 0;
		private int redstone_buffer = -1;
		
		@Override
		public void updateEntity()
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
				System.out.println("update redstone");
				worldObj.notifyBlockChange(xCoord, yCoord, zCoord, worldObj.getBlock(xCoord, yCoord, zCoord));
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
		}
	}

}
