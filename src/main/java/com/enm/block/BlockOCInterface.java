package com.enm.block;

import com.enm.block.util.BlockENM;
import com.enm.core.CoreMod;
import com.enm.tileEntity.TileEntityOCI;
import com.enm.tileEntityutil.ModDependence;
import com.enm.util.Tools;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockOCInterface extends BlockENM implements ModDependence
{

	public BlockOCInterface(Material material)
	{
		super(material);
	}
	
	IIcon[] textures;
	@Override
	public void registerBlockIcons(IIconRegister register)
	{
		textures = new IIcon[]
			{
				register.registerIcon(CoreMod.MODID+":calculationunit"),
				register.registerIcon(CoreMod.MODID+":machineBack"),
				register.registerIcon(CoreMod.MODID+":machine")
			};
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack item)
	{
		TileEntity te = world.getTileEntity(x, y, z);
		if(te instanceof TileEntityOCI)
		{
			((TileEntityOCI)te).inputdir = Tools.EntityLivingBaseToForgeDirection(player, true);
		}
	}
	
	@Override
	public IIcon getIcon(int side, int meta)
	{
		if(side == 4) return textures[0];
		return textures[2];
	}
	
	@Override
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
	{
		TileEntity te = world.getTileEntity(x, y, z);
		if(te instanceof TileEntityOCI)
		{
			TileEntityOCI tes = (TileEntityOCI) te;
			if(Tools.ForgeDirectionToIntegerSide(tes.inputdir) == side)
			{
				return textures[0];
			}
			else if(Tools.ForgeDirectionToIntegerSide(tes.inputdir.getOpposite()) == side)
			{
				return textures[1];
			}
		}
		return textures[2];
	}

	@Override
	public String DisplayBlockName()
	{
		return "Open Computers Interface";
	}

	@Override
	public String BlockClassName()
	{
		return getClass().getSimpleName();
	}

	@Override
	public TileEntity createNewTileEntity(World w, int m)
	{
		return new TileEntityOCI();
	}

	@Override
	public String[] ModsDependence()
	{
		return new String[]{"OpenComputers"};
	}

}
