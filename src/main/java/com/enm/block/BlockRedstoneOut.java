package com.enm.block;

import com.enm.api.redstone.RedstoneUtil;
import com.enm.block.util.BlockENM;
import com.enm.container.ENMContainer;
import com.enm.core.CoreMod;
import com.enm.guicontainer.GuiContainerRedstoneOut;
import com.enm.tileEntity.TileEntityRedstoneOut;
import com.enm.tileEntityutil.GuiRegister;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRedstoneOut extends BlockENM implements GuiRegister
{

	public BlockRedstoneOut(Material material)
	{
		super(material);
		setBlockTextureName(CoreMod.MODID+":"+"redstoneOUT");
	}

	@Override
	public String DisplayBlockName()
	{
		return "Redstone Output";
	}

	@Override
	public String BlockClassName()
	{
		return getClass().getSimpleName();
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntityRedstoneOut();
	}
	
	public boolean canProvidePower()
    {
        return true;
    }
	
	public int isProvidingWeakPower(IBlockAccess ba, int x, int y, int z, int m)
	{
		return RedstoneUtil.isProvidingWeakPower(ba, x, y, z, m);
	}

	public int isProvidingStrongPower(IBlockAccess ba, int x, int y, int z, int m)
	{
		return RedstoneUtil.isProvidingStrongPower(ba, x, y, z, m);
	}
	
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float fx, float fy, float fz)
	{
		TileEntity tileEntity = world.getTileEntity(x, y, z);
	    if(tileEntity == null || player.isSneaking())
	    {
	    	return false;
	    }
	    player.openGui(CoreMod.instance, 0, world, x, y, z);
	    return true;
	}

	@Override
	public Container GetContainer(InventoryPlayer pl, TileEntity te)
	{
		return new ENMContainer();
	}
	
	@SideOnly(Side.CLIENT)
	public GuiContainer GetGuiContainer(InventoryPlayer pl, TileEntity te)
	{
		return new GuiContainerRedstoneOut(pl, (TileEntityRedstoneOut)te);
	}

}
