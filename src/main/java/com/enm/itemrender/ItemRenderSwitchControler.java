package com.enm.itemrender;

import org.lwjgl.opengl.GL11;

import com.enm.core.CoreMod;
import com.enm.guiutil.ResourceLocationRegister;
import com.enm.model.ModelSwitchControler;
import com.enm.model.ModelTerminal;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderSwitchControler implements IItemRenderer
{
	private final ModelSwitchControler model;
    
	public ItemRenderSwitchControler()
	{
		model = new ModelSwitchControler();
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
			 GL11.glTranslatef(0.7F, 1.0F, 0.9F);
			 GL11.glRotatef(30f, 0, 1, 0);
			 GL11.glRotatef(-70f, 1, 0, 0);
		 }
		 GL11.glTranslatef(0, 1, 0);
		 Minecraft.getMinecraft().renderEngine.bindTexture(ResourceLocationRegister.texture_switchcontroler);
		 GL11.glRotatef(180F, 1.0F, 0F, 0F);
		 model.render(0.0625F);
		 GL11.glPopMatrix();
	}
	 
}
