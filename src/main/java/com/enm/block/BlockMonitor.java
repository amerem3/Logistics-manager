package com.enm.block;

import com.enm.block.util.BlockENM;
import com.enm.core.CoreMod;
import com.enm.itemrender.ItemRenderMonitor;
import com.enm.tileEntity.TileEntityMonitor;
import com.enm.tileEntitySpecialRenderer.TileEntitySRMonitor;
import com.enm.tileEntityutil.ISR;
import com.enm.tileEntityutil.TSR;
import com.enm.util.Tools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;

public class BlockMonitor extends BlockENM implements TSR, ISR
{

	public BlockMonitor(Material m)
	{
		super(m);
		this.setHardness(2.0f);
		this.setResistance(5.1f);
		setBlockTextureName(CoreMod.MODID+":rfstorageinfopanel");
	}

	@Override
	public String DisplayBlockName()
	{
		return "Monitor";
	}

	@Override
	public String BlockClassName()
	{
		return getClass().getSimpleName();
	}

	@Override
	public TileEntity createNewTileEntity(World world, int id)
	{
		return new TileEntityMonitor();
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
    
    @SideOnly(Side.CLIENT)
	@Override
	public TileEntitySpecialRenderer TESpecialRenderer()
	{
		return new TileEntitySRMonitor();
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IItemRenderer ItemRender()
	{
		return new ItemRenderMonitor();
	}
	
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack item)
	{
		TileEntity te = world.getTileEntity(x, y, z);
		if(te instanceof TileEntityMonitor)
		{
			((TileEntityMonitor)te).inputdir = Tools.EntityLivingBaseToForgeDirection(player, false);
		}
	}

}
