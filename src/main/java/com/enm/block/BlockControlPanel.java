package com.enm.block;

import com.enm.block.util.BlockENM;
import com.enm.container.ENMContainer;
import com.enm.core.CoreMod;
import com.enm.guicontainer.GuiContainerControlPanel;
import com.enm.guicontainer.GuiContainerCounter;
import com.enm.itemrender.ItemRenderControlPanel;
import com.enm.register.RItems;
import com.enm.tileEntity.TileEntityControlPanel;
import com.enm.tileEntity.TileEntityCounter;
import com.enm.tileEntitySpecialRenderer.TileEntitySRControlPanel;
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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;

public class BlockControlPanel extends BlockENM implements TSR, ISR, GuiRegister
{

	public BlockControlPanel(Material material)
	{
		super(material);
		this.setHardness(2.0f);
		this.setResistance(5.1f);
	}
	
	@Override
	public Container GetContainer(InventoryPlayer pl, TileEntity te)
	{
		return new ENMContainer();
	}

	@SideOnly(Side.CLIENT)
	public GuiContainer GetGuiContainer(InventoryPlayer pl, TileEntity te)
	{
		return new GuiContainerControlPanel(pl, (TileEntityControlPanel)te);
	}

	@Override
	public String DisplayBlockName()
	{
		return "Control Panel";
	}

	@Override
	public String BlockClassName()
	{
		return getClass().getSimpleName();
	}

	@Override
	public TileEntity createNewTileEntity(World world, int id)
	{
		return new TileEntityControlPanel();
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
		return new TileEntitySRControlPanel();
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IItemRenderer ItemRender()
	{
		return new ItemRenderControlPanel();
	}
	
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack item)
	{
		TileEntity te = world.getTileEntity(x, y, z);
		if(te instanceof TileEntityControlPanel)
		{
			((TileEntityControlPanel)te).inputdir = Tools.EntityLivingBaseToForgeDirection(player, false);
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float deltaX, float deltaY, float deltaZ)
	{
		/*if(world.getTileEntity(x, y, z) instanceof TileEntityControlPanel && !player.isSneaking() )//&& Tools.ItemHeldIsEquals(Minecraft.getMinecraft(), RItems.Item_Screwdriver))
		{
			TileEntityControlPanel te = (TileEntityControlPanel) world.getTileEntity(x, y, z);
			int by = 1- (int)(deltaY*2);
			int bx = 0;
			
			if(te.inputdir.ordinal() == 2)bx = 1-(int)(deltaX*2);
			if(te.inputdir.ordinal() == 3)bx = (int)(deltaX*2);
			if(te.inputdir.ordinal() == 4)bx = (int)(deltaZ*2);
			if(te.inputdir.ordinal() == 5)bx = 1-(int)(deltaZ*2);
			
			te.pos_screen.x = bx;
			te.pos_screen.y = by;
			
			player.openGui(CoreMod.instance, 0, world, x, y, z);
			return true;
		}*/
		
		return false;
	}

}
