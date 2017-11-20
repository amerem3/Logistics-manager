package com.enm.tileEntitySpecialRenderer;

import org.lwjgl.opengl.GL11;

import com.enm.core.CoreMod;
import com.enm.guiutil.ResourceLocationRegister;
import com.enm.model.ModelNetWorkCable;
import com.enm.model.ModelSwitchControler;
import com.enm.tileEntity.TileEntitySwitchControler;
import com.enm.util.Tools;

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
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntitySRSwitchControler extends TileEntitySpecialRenderer
{

	//The model of your block
    private final ModelSwitchControler model;
   
    public TileEntitySRSwitchControler()
    {
    	model = new ModelSwitchControler();
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
            Minecraft.getMinecraft().renderEngine.bindTexture(ResourceLocationRegister.texture_switchcontroler);
            GL11.glPushMatrix();
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            if(te instanceof TileEntitySwitchControler)
            {
            	TileEntitySwitchControler sc = (TileEntitySwitchControler) te;
            	if(sc.inputdir == ForgeDirection.EAST)GL11.glRotatef(90, 0, 1, 0);
            	else if(sc.inputdir == ForgeDirection.SOUTH)GL11.glRotatef(180, 0, 1, 0);
            	else if(sc.inputdir == ForgeDirection.WEST)GL11.glRotatef(270, 0, 1, 0);
            	
            	this.model.render(te , 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
            }
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