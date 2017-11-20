package com.enm.block.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class TextureBlock
{
	IIcon[] textureBlock;
	public TextureBlock(IIcon front, IIcon back, IIcon top, IIcon bottom, IIcon left, IIcon right)
	{
		textureBlock = new IIcon[6];
		textureBlock[0] = front;
		textureBlock[1] = back;
		textureBlock[2] = top;
		textureBlock[3] = bottom;
		textureBlock[4] = left;
		textureBlock[5] = right;
	}
	
	public IIcon getIcon(int side, int meta)
	{
		if(side == 0)
		{
			return textureBlock[3];
		}
		else if(side == 1)
		{
			return textureBlock[2];
		}
		else
		{
			if(meta == 0)
			{
				switch(side)
				{
					case 2: return textureBlock[1];
					case 3: return textureBlock[0];
					case 4: return textureBlock[4];
					case 5: return textureBlock[5];
				}
			}
			if(meta == 1)
			{
				switch(side)
				{
					case 2: return textureBlock[4];
					case 3: return textureBlock[5];
					case 4: return textureBlock[0];
					case 5: return textureBlock[1];
				}
			}
			if(meta == 2)
			{
				switch(side)
				{
					case 2: return textureBlock[0];
					case 3: return textureBlock[1];
					case 4: return textureBlock[5];
					case 5: return textureBlock[4];
				}
			}
			if(meta == 3)
			{
				switch(side)
				{
					case 2: return textureBlock[5];
					case 3: return textureBlock[4];
					case 4: return textureBlock[1];
					case 5: return textureBlock[0];
				}
			}
		}
		return textureBlock[0];
	}
	
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack stack)
	{
		int rotation = MathHelper.floor_double((double)((entityliving.rotationYaw * 4F) / 360F) + 2.5D) & 3;
		world.setBlockMetadataWithNotify(i, j, k, rotation, rotation);
	}
}
