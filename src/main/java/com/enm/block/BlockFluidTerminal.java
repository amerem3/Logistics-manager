package com.enm.block;

import java.io.File;

import com.enm.block.util.BlockENM;
import com.enm.container.ENMContainer;
import com.enm.core.CoreMod;
import com.enm.guicontainer.GuiContainerFluidTerminal;
import com.enm.itemrender.ItemRenderFluidTerminal;
import com.enm.tileEntity.TileEntityTerminal;
import com.enm.tileEntitySpecialRenderer.TileEntitySRFluidTerminal;
import com.enm.tileEntityutil.GuiRegister;
import com.enm.tileEntityutil.ISR;
import com.enm.tileEntityutil.TSR;
import com.enm.util.Tools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;

public class BlockFluidTerminal extends BlockENM implements ISR, TSR, GuiRegister
{
	@Override
	public Container GetContainer(InventoryPlayer pl, TileEntity te)
	{
		return new ENMContainer();
	}

	@SideOnly(Side.CLIENT)
	public GuiContainer GetGuiContainer(InventoryPlayer pl, TileEntity te)
	{
		return new GuiContainerFluidTerminal(pl, (TileEntityTerminal)te);
	}
	
	@SideOnly(Side.CLIENT)
	public TileEntitySpecialRenderer TESpecialRenderer()
	{
		return new TileEntitySRFluidTerminal();
	}

	@SideOnly(Side.CLIENT)
	public IItemRenderer ItemRender()
	{
		return new ItemRenderFluidTerminal();
	}
	
	public BlockFluidTerminal(Material material)
	{
		super(material);
		this.setHardness(2.0f);
		this.setResistance(5.1f);
		setBlockTextureName(CoreMod.MODID+":Fnameinfo");
	}
	
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack item)
	{
		super.onBlockPlacedBy(world, x, y, z, player, item);
		int rotation = MathHelper.floor_double((double)((player.rotationYaw * 4F) / 360F) + 2.5D) & 3;
		if(world.getTileEntity(x, y, z) instanceof TileEntityTerminal)
		{
			TileEntityTerminal t = (TileEntityTerminal) world.getTileEntity(x, y, z);
			t.dir = rotation;
			t.updateInventory();
		}
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
	public void onBlockDestroyedByPlayer(World w, int x, int y, int z, int meta)
	{
		String dir = "";
		if(Tools.isServer())dir = MinecraftServer.getServer().getEntityWorld().getSaveHandler().getWorldDirectory()+"\\ENM\\";
		if(Tools.isClient() && Minecraft.getMinecraft().isSingleplayer())dir = Minecraft.getMinecraft().getIntegratedServer().getEntityWorld().getSaveHandler().getWorldDirectory()+"\\ENM\\";
		if(dir == "")return;
		
		if(new File(dir+"X"+x+"Y"+y+"Z"+z+"D"+w.provider.dimensionId).exists())
		{
			new File(dir+"X"+x+"Y"+y+"Z"+z+"D"+w.provider.dimensionId).delete();
		}
	}

	@Override
	public String DisplayBlockName()
	{
		return "Fluid Terminal";
	}

	@Override
	public String BlockClassName()
	{
		return getClass().getSimpleName();
	}

	@Override
	public TileEntity createNewTileEntity(World world, int id)
	{
		return new TileEntityFluidTerminal();
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
    
    public static class TileEntityFluidTerminal extends TileEntityTerminal
    {
    	public String name()
    	{
			return "Fluid Terminal";
    	}
    }
}
