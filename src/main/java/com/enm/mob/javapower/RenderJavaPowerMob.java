package com.enm.mob.javapower;

import com.enm.core.CoreMod;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderJavaPowerMob extends RenderLiving
{

	private static final ResourceLocation mobTextures = new ResourceLocation(CoreMod.MODID + ":textures/mob/me.png");
	//ModelBiped
	public RenderJavaPowerMob(ModelBase par1ModelBase, float par2)
	{
		super(par1ModelBase, par2);
		
	}
	
	protected ResourceLocation getEntityTexture(EntityJavaPowerMob entity)
	{
		return mobTextures;
	}
	
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return this.getEntityTexture((EntityJavaPowerMob)entity);
	}

}
