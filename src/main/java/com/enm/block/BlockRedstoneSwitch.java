package com.enm.block;

import com.enm.api.redstone.RedstoneUtil;
import com.enm.block.util.BlockENM;
import com.enm.container.ENMContainer;
import com.enm.core.CoreMod;
import com.enm.guicontainer.GuiContainerRedstoneSwitch;
import com.enm.guicontainer.GuiContainerSwitch;
import com.enm.tileEntity.TileEntityRedstoneSwitch;
import com.enm.tileEntity.TileEntitySwitch;
import com.enm.tileEntityutil.GuiRegister;
import com.enm.util.Tools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRedstoneSwitch extends BlockENM implements GuiRegister
{
	
	@Override
	public Container GetContainer(InventoryPlayer pl, TileEntity te)
	{
		return new ENMContainer();
	}

	@SideOnly(Side.CLIENT)
	public GuiContainer GetGuiContainer(InventoryPlayer pl, TileEntity te)
	{
		return new GuiContainerRedstoneSwitch(pl, (TileEntityRedstoneSwitch)te);
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
	
	public BlockRedstoneSwitch(Material material)
	{
		super(material);
		setBlockTextureName(CoreMod.MODID+":redstoneswitch");
	}

	@Override
	public String DisplayBlockName()
	{
		return "Redstone Switch";
	}

	@Override
	public String BlockClassName()
	{
		return getClass().getSimpleName();
	}

	@Override
	public TileEntity createNewTileEntity(World world, int id)
	{
		return new TileEntityRedstoneSwitch();
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

}
