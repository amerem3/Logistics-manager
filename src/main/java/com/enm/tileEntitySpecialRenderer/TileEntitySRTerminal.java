package com.enm.tileEntitySpecialRenderer;

import org.lwjgl.opengl.GL11;

import com.enm.core.CoreMod;
import com.enm.guiutil.ResourceLocationRegister;
import com.enm.model.ModelTerminal;
import com.enm.tileEntity.TileEntityTerminal;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class TileEntitySRTerminal extends TileEntitySpecialRenderer
{
    
    //The model of your block
    private final ModelTerminal model;
    
    public TileEntitySRTerminal()
    {
            model = new ModelTerminal();
    }
   
    private void adjustRotatePivotViaMeta(World world, int x, int y, int z)
    {
            //int meta = world.getBlockMetadata(x, y, z);
            GL11.glPushMatrix();
            GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
            GL11.glPopMatrix();
    }
   
    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale)
    {
            GL11.glPushMatrix();
            GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
            Minecraft.getMinecraft().renderEngine.bindTexture(ResourceLocationRegister.texture_terminal);                    
            GL11.glPushMatrix();
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
            if(te instanceof TileEntityTerminal)GL11.glRotatef(90F* ((TileEntityTerminal)te).dir, 0.0F, 1.0F, 0.0F);
            this.model.render((Entity)null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
            GL11.glPopMatrix();
            GL11.glPopMatrix();
    }
    private void adjustLightFixture(World world, int i, int j, int k, Block block) {
            Tessellator tess = Tessellator.instance;
            float brightness = block.getLightValue(world, i, j, k);
            int skyLight = world.getLightBrightnessForSkyBlocks(i, j, k, 0);
            int modulousModifier = skyLight % 65536;
            int divModifier = skyLight / 65536;
            tess.setColorOpaque_F(brightness, brightness, brightness);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit,  (float) modulousModifier,  divModifier);
    }
}