package com.enm.block;

import java.util.List;

import com.enm.api.network.Machine_Switch;
import com.enm.api.network.NetWorkUtil;
import com.enm.block.util.BlockENM;
import com.enm.container.ENMContainer;
import com.enm.core.CoreMod;
import com.enm.guicontainer.GuiContainerSwitchControler;
import com.enm.itemrender.ItemRenderSwitchControler;
import com.enm.tileEntity.TileEntitySwitchControler;
import com.enm.tileEntitySpecialRenderer.TileEntitySRSwitchControler;
import com.enm.tileEntityutil.GuiRegister;
import com.enm.tileEntityutil.ISR;
import com.enm.tileEntityutil.TSR;
import com.enm.util.Tools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockSwitchControler extends BlockENM implements ISR, TSR, GuiRegister
{

	public BlockSwitchControler(Material material)
	{
		super(material);
	}
	
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float fx, float fy, float fz)
	{
		int int_x = (int)(fx*16);
		int int_y = (int)(fy*16);
		int int_z = (int)(fz*16);
		
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		
		if(tileEntity == null || player.isSneaking())
	    {
	    	return false;
	    }
		
		if(tileEntity instanceof TileEntitySwitchControler)
		{
			TileEntitySwitchControler sc = (TileEntitySwitchControler)tileEntity;
			if(sc.inputdir == ForgeDirection.EAST)
			{
				if(int_z >= 7 && int_z <= 8 && int_y == 9 && int_x == 1)//button on
				{
					buttonOn(sc);
					return true;
				}
				else if(int_z >= 7 && int_z <= 8 && int_y == 7 && int_x == 1)//button off
				{
					buttonOff(sc);
					return true;
				}
			}
			else if(sc.inputdir == ForgeDirection.NORTH)
			{
				if(int_x >= 7 && int_x <= 8 && int_y == 9 && int_z == 14)//button on
				{
					buttonOn(sc);
					return true;
				}
				else if(int_x >= 7 && int_x <= 8 && int_y == 7 && int_z == 14)//button off
				{
					buttonOff(sc);
					return true;
				}
			}
			else if(sc.inputdir == ForgeDirection.SOUTH)
			{
				if(int_x >= 7 && int_x <= 8 && int_y == 9 && int_z == 1)//button on
				{
					buttonOn(sc);
					return true;
				}
				else if(int_x >= 7 && int_x <= 8 && int_y == 7 && int_z == 1)//button off
				{
					buttonOff(sc);
					return true;
				}
			}
	    	else if(sc.inputdir == ForgeDirection.WEST)
	    	{
	    		if(int_z >= 7 && int_z <= 8 && int_y == 9 && int_x == 14)//button on
				{
	    			buttonOn(sc);
	    			return true;
				}
				else if(int_z >= 7 && int_z <= 8 && int_y == 7 && int_x == 14)//button off
				{
					buttonOff(sc);
					return true;
				}
	    	}
		}
	    player.openGui(CoreMod.instance, 0, world, x, y, z);
	    return true;
	}
	
	private void buttonOff(TileEntitySwitchControler sc)
	{
		if(sc.ent != null)
		{
			List<TileEntity> machines = NetWorkUtil.GetAllMachines(sc.getWorldObj(), sc.xCoord, sc.yCoord, sc.zCoord);
			for(TileEntity mach : machines)
			{
				if(mach instanceof Machine_Switch && mach.xCoord == sc.ent.x && mach.yCoord == sc.ent.y && mach.zCoord == sc.ent.z)
				{
					((Machine_Switch)mach).SwitchONOFF(false);
					return;
				}
			}
		}
	}

	private void buttonOn(TileEntitySwitchControler sc)
	{
		if(sc.ent != null)
		{
			List<TileEntity> machines = NetWorkUtil.GetAllMachines(sc.getWorldObj(), sc.xCoord, sc.yCoord, sc.zCoord);
			for(TileEntity mach : machines)
			{
				if(mach instanceof Machine_Switch && mach.xCoord == sc.ent.x && mach.yCoord == sc.ent.y && mach.zCoord == sc.ent.z)
				{
					((Machine_Switch)mach).SwitchONOFF(true);
					return;
				}
			}
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack i)
	{
		TileEntity te = world.getTileEntity(x, y, z);
		if(te instanceof TileEntitySwitchControler)
		{
			((TileEntitySwitchControler)te).inputdir = Tools.EntityLivingBaseToForgeDirection(player, true);
			((TileEntitySwitchControler)te).updateInventory();
		}
	}
	
	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
	{
		float startX = 0, startY = 0, startZ = 0, endX = 1, endY = 1, endZ = 1;
		
		TileEntity te = world.getTileEntity(x, y, z);
		if(te instanceof TileEntitySwitchControler)
		{
			TileEntitySwitchControler sc = (TileEntitySwitchControler)te;
			if(sc.inputdir == ForgeDirection.EAST)
			{
				startZ = 0.375f;
				endZ = 0.625f;
				
				startY = 0.3125f;
				endY = 0.6875f;
				
				endX = 0.09375f;
			}
			else if(sc.inputdir == ForgeDirection.NORTH)
			{
				startX = 0.375f;
				endX = 0.625f;
				
				startY = 0.3125f;
				endY = 0.6875f;
				
				startZ = 0.90625f;
			}
			else if(sc.inputdir == ForgeDirection.SOUTH)
			{
				startX = 0.375f;
				endX = 0.625f;
				
				startY = 0.3125f;
				endY = 0.6875f;
				
				endZ = 0.09375f;
			}
	    	else if(sc.inputdir == ForgeDirection.WEST)
	    	{
	    		startZ = 0.375f;
				endZ = 0.625f;
				
				startY = 0.3125f;
				endY = 0.6875f;
				
				startX = 0.90625f;
	    	}
		}
		
		this.setBlockBounds(startX, startY, startZ, endX, endY, endZ);
		return super.getSelectedBoundingBoxFromPool(world, x, y, z);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		float startX = 0, startY = 0, startZ = 0, endX = 1, endY = 1, endZ = 1;
		
		TileEntity te = world.getTileEntity(x, y, z);
		if(te instanceof TileEntitySwitchControler)
		{
			TileEntitySwitchControler sc = (TileEntitySwitchControler)te;
			if(sc.inputdir == ForgeDirection.EAST)
			{
				startZ = 0.375f;
				endZ = 0.625f;
				
				startY = 0.3125f;
				endY = 0.6875f;
				
				endX = 0.09375f;
			}
			else if(sc.inputdir == ForgeDirection.NORTH)
			{
				startX = 0.375f;
				endX = 0.625f;
				
				startY = 0.3125f;
				endY = 0.6875f;
				
				startZ = 0.90625f;
			}
			else if(sc.inputdir == ForgeDirection.SOUTH)
			{
				startX = 0.375f;
				endX = 0.625f;
				
				startY = 0.3125f;
				endY = 0.6875f;
				
				endZ = 0.09375f;
			}
	    	else if(sc.inputdir == ForgeDirection.WEST)
	    	{
	    		startZ = 0.375f;
				endZ = 0.625f;
				
				startY = 0.3125f;
				endY = 0.6875f;
				
				startX = 0.90625f;
	    	}
		}
		return AxisAlignedBB.getBoundingBox((double)x + startX, (double)y + startY, (double)z + startZ, (double) x + endX, (double)y + endY, (double)z + endZ);
	}

	@Override
	public String DisplayBlockName()
	{
		return "RF Switch Controler";
	}

	@Override
	public String BlockClassName()
	{
		return getClass().getSimpleName();
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
	{
		return new TileEntitySwitchControler();
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

	@Override
	public Container GetContainer(InventoryPlayer pl, TileEntity te)
	{
		return new ENMContainer();
	}

	@SideOnly(Side.CLIENT)
	public GuiContainer GetGuiContainer(InventoryPlayer pl, TileEntity te)
	{
		return new GuiContainerSwitchControler(pl, (TileEntitySwitchControler)te);
	}

	@SideOnly(Side.CLIENT)
	public TileEntitySpecialRenderer TESpecialRenderer()
	{
		return new TileEntitySRSwitchControler();
	}

	@SideOnly(Side.CLIENT)
	public IItemRenderer ItemRender()
	{
		return new ItemRenderSwitchControler();
	}

}
