package com.enm.block;

import com.enm.api.network.NetWork;
import com.enm.block.util.BlockENM;
import com.enm.core.CoreMod;
import com.enm.itemrender.ItemRenderNetWorkCable;
import com.enm.register.RBlocks;
import com.enm.tileEntity.TileEntityNetWorkCable;
import com.enm.tileEntitySpecialRenderer.TileEntitySRNetWorkCable;
import com.enm.tileEntityutil.ISR;
import com.enm.tileEntityutil.TSR;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;

public class BlockNetWorkCable extends BlockENM implements TSR, ISR
{
	
	@SideOnly(Side.CLIENT)
	public TileEntitySpecialRenderer TESpecialRenderer()
	{
		return new TileEntitySRNetWorkCable();
	}
	
	@SideOnly(Side.CLIENT)
	public IItemRenderer ItemRender()
	{
		return new ItemRenderNetWorkCable();
	}

	public BlockNetWorkCable(Material material)
	{
		super(material);
		setBlockTextureName(CoreMod.MODID+":machine");
	}

	@Override
	public String DisplayBlockName()
	{
		return "NetWork Cable";
	}

	@Override
	public String BlockClassName()
	{
		return getClass().getSimpleName();
	}

	@Override
	public TileEntity createNewTileEntity(World arg0, int arg1)
	{
		return new TileEntityNetWorkCable();
	}
	
	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
	{
		float startX = 0.375f, startY = 0.375f, startZ = 0.375f, endX = 0.625f, endY = 0.625f, endZ = 0.625f;
		
		if(world.getTileEntity(x -1, y, z) instanceof NetWork) startX = 0;
		if(world.getTileEntity(x +1, y, z) instanceof NetWork) endX = 1;
		if(world.getTileEntity(x, y -1, z) instanceof NetWork) startY = 0;
		if(world.getTileEntity(x, y +1, z) instanceof NetWork) endY = 1;
		if(world.getTileEntity(x, y, z -1) instanceof NetWork) startZ = 0;
		if(world.getTileEntity(x, y, z +1) instanceof NetWork) endZ = 1;
		
		this.setBlockBounds(startX, startY, startZ, endX, endY, endZ);
		return super.getSelectedBoundingBoxFromPool(world, x, y, z);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		float startX = 0.375f, startY = 0.375f, startZ = 0.375f, endX = 0.625f, endY = 0.625f, endZ = 0.625f;
		
		if(world.getTileEntity(x -1, y, z) instanceof NetWork) startX = 0;
		if(world.getTileEntity(x +1, y, z) instanceof NetWork) endX = 1;
		if(world.getTileEntity(x, y -1, z) instanceof NetWork) startY = 0;
		if(world.getTileEntity(x, y +1, z) instanceof NetWork) endY = 1;
		if(world.getTileEntity(x, y, z -1) instanceof NetWork) startZ = 0;
		if(world.getTileEntity(x, y, z +1) instanceof NetWork) endZ = 1;
		
		return AxisAlignedBB.getBoundingBox((double)x + startX, (double)y + startY, (double)z + startZ, (double) x + endX, (double)y + endY, (double)z + endZ);
	}
	
	@Override
    public int getRenderType()
	{
            return -1;
    }
	
    @Override
    public boolean isOpaqueCube()
    {
            return false;
    }
    
    public boolean renderAsNormalBlock()
    {
            return false;
    }

}
