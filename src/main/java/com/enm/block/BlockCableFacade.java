package com.enm.block;

import com.enm.block.util.BlockENM;
import com.enm.core.CoreMod;
import com.enm.tileEntity.TileEntityCableFacade;
import com.enm.util.Tools;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCableFacade extends BlockENM
{

	public BlockCableFacade(Material material)
	{
		super(material);
		setBlockTextureName(CoreMod.MODID+":CableFacade");
	}

	@Override
	public String DisplayBlockName()
	{
		return "Cable Facade";
	}

	@Override
	public String BlockClassName()
	{
		return getClass().getSimpleName();
	}
	
	 @Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float fx, float fy, float fz)
	{
		 ItemStack item_hand = player.getCurrentEquippedItem();
		 if(item_hand != null)
		 {
			 Block b = Block.getBlockFromItem(item_hand.getItem());
			 TileEntity te = world.getTileEntity(x, y, z);
			 if(b != null && b != Blocks.air && te instanceof TileEntityCableFacade)
			 {
				 ((TileEntityCableFacade)te).TXID = Block.getIdFromBlock(b);
				 ((TileEntityCableFacade)te).meta = item_hand.getItemDamage();
				 ((TileEntityCableFacade)te).updateInventory();
				 world.markBlockForUpdate(x, y, z);
				 return true;
			 }
		 }
		 return false;
	}
	 
	 @Override
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
	{
		 TileEntity te = Tools.GetTileEntityOnServer(x, y, z);
		 if(te instanceof TileEntityCableFacade)
		 {
			return Block.getBlockById(((TileEntityCableFacade)te).TXID).getIcon(side, ((TileEntityCableFacade)te).meta);
			 
		 }
		 return null;
	}
	
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
	{
		return new TileEntityCableFacade();
	}

}
