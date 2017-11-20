package com.enm.item;

import org.lwjgl.opengl.GL11;

import com.enm.core.CoreMod;
import com.enm.core.ENMResource;
import com.enm.item.util.ItemIdentity;
import com.enm.model.ModelLogicMachineFrame;
import com.enm.tileEntityutil.ISR;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class ItemRFLogicMachineFrame extends Item implements ItemIdentity, ISR
{

	public ItemRFLogicMachineFrame()
	{
		setCreativeTab(ENMResource.ENMCreativeTab);
		setUnlocalizedName(ItemClassName());
	}
	@Override
	public String DisplayItemName(){return "RF Logic Machine Casing";}
	@Override
	public String ItemClassName(){return getClass().getSimpleName();}

	@Override
	@SideOnly(Side.CLIENT)
	public IItemRenderer ItemRender()
	{
		return new CItemRender();
	}
	
	public class CItemRender implements IItemRenderer
	{
		private final ModelLogicMachineFrame model;
	    ResourceLocation textures;
	    
		public CItemRender()
		{
			model = new ModelLogicMachineFrame();
			textures = new ResourceLocation(CoreMod.MODID+":textures/blocks/models/LogicMachineFrame.png");
		}
		
		@Override public boolean handleRenderType(ItemStack item, ItemRenderType type){return true;}
		@Override public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper){return true;}
		
		@Override
		public void renderItem(ItemRenderType type, ItemStack item, Object... data)
		{
			 GL11.glPushMatrix();
			 if(type == ItemRenderType.EQUIPPED)
			 {
				 GL11.glTranslatef(0.6F, 0.6F, 0.7F);
			 }
			 GL11.glTranslatef(0, 1, 0);
			 Minecraft.getMinecraft().renderEngine.bindTexture(textures);
			 GL11.glRotatef(180F, 1.0F, 0F, 0F);
			 model.render(0.0625F);
			 GL11.glPopMatrix();
		}
	}

}
