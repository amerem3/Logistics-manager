package com.enm.itemrender;

import org.lwjgl.opengl.GL11;

import com.enm.core.CoreMod;
import com.enm.model.ModelNetWorkCable;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderNetWorkCable implements IItemRenderer
{
	private final ModelNetWorkCable model;
    ResourceLocation textures;
	public ItemRenderNetWorkCable()
	{
		model = new ModelNetWorkCable();
		textures =  new ResourceLocation(CoreMod.MODID+":textures/blocks/models/NetWorkCable.png");
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		 GL11.glPushMatrix();
		 if(type == ItemRenderType.EQUIPPED)
		 {
			 GL11.glTranslatef(0.6F, 1.6F, 0.7F);
		 }
		 else GL11.glTranslatef(0, 1, 0);
		 Minecraft.getMinecraft().renderEngine.bindTexture(textures);
		 GL11.glRotatef(180F, 1.0F, 0F, 0F);
		 model.render(0.0625F);
		 GL11.glPopMatrix();
	}
}
