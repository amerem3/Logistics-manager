package com.enm.tileEntitySpecialRenderer;

import org.lwjgl.opengl.GL11;

import com.enm.core.CoreMod;
import com.enm.model.ModelNetWorkCable;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class TileEntitySRNetWorkCable extends TileEntitySpecialRenderer{

	//The model of your block
    private final ModelNetWorkCable model;
    private ResourceLocation textures;
   
    public TileEntitySRNetWorkCable()
    {
    	model = new ModelNetWorkCable();
    	textures = new ResourceLocation(CoreMod.MODID+":textures/blocks/models/NetWorkCable.png");
    }
   
    private void adjustRotatePivotViaMeta(World world, int x, int y, int z)
    {
            int meta = world.getBlockMetadata(x, y, z);
            GL11.glPushMatrix();
            GL11.glRotatef(meta * (-90), 0.0F, 0.0F, 1.0F);
            GL11.glPopMatrix();
    }
   
    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale)
    {
            GL11.glPushMatrix();
            GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
            Minecraft.getMinecraft().renderEngine.bindTexture(textures);
            GL11.glPushMatrix();
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            this.model.render(te , 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
            GL11.glPopMatrix();
            GL11.glPopMatrix();
    }
    
    private void adjustLightFixture(World world, int i, int j, int k, Block block)
    {
            Tessellator tess = Tessellator.instance;
            float brightness = block.getLightValue(world, i, j, k);
            int skyLight = world.getLightBrightnessForSkyBlocks(i, j, k, 0);
            int modulousModifier = skyLight % 65536;
            int divModifier = skyLight / 65536;
            tess.setColorOpaque_F(brightness, brightness, brightness);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit,  (float) modulousModifier,  divModifier);
    }

}

		//super(new ModelNetWorkCable(), new ResourceLocation(CoreMod.MODID+":textures/blocks/models/NetWorkCable.png"));