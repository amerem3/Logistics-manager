package com.enm.tileEntitySpecialRenderer;

import org.lwjgl.opengl.GL11;

import com.enm.api.network.Machine_Counter;
import com.enm.api.network.Machine_FluidCounter;
import com.enm.api.network.Machine_FluidFlowMeter;
import com.enm.api.network.Machine_FluidName;
import com.enm.api.network.Machine_FluidStorageInfo;
import com.enm.api.network.Machine_FluidValve;
import com.enm.api.network.Machine_FluxMeter;
import com.enm.api.network.Machine_StorageInfo;
import com.enm.api.network.Machine_Switch;
import com.enm.core.CoreMod;
import com.enm.model.ModelLogicMachineFrame;
import com.enm.tileEntity.TileEntityMonitor;
import com.enm.tileEntity.TileEntitySwitchControler;
import com.enm.tileEntity.TileEntityTerminal;
import com.enm.util.Tools;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntitySRMonitor extends TileEntitySpecialRenderer
{
	ResourceLocation t = new ResourceLocation(CoreMod.MODID+":textures/blocks/models/monitor.png");
	Minecraft mc = Minecraft.getMinecraft();
	private final ModelLogicMachineFrame model;
	    
	    public TileEntitySRMonitor()
	    {
	            model = new ModelLogicMachineFrame();
	    }
	   
	    private void adjustRotatePivotViaMeta(World world, int x, int y, int z)
	    {
	            GL11.glPushMatrix();
	            GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
	            GL11.glPopMatrix();
	    }
	   
	    @Override
	    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale)
	    {
	            GL11.glPushMatrix();
	            GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
	            mc.renderEngine.bindTexture(t);                    
	            GL11.glPushMatrix();
	            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
	            if(te instanceof TileEntityMonitor)
	            {
	            	TileEntityMonitor sc = (TileEntityMonitor) te;
	            	if(sc.inputdir == ForgeDirection.EAST)GL11.glRotatef(90, 0, 1, 0);
	            	else if(sc.inputdir == ForgeDirection.SOUTH)GL11.glRotatef(180, 0, 1, 0);
	            	else if(sc.inputdir == ForgeDirection.WEST)GL11.glRotatef(270, 0, 1, 0);
	            }
	            this.model.render((Entity)null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
	            
	            GL11.glTranslatef(-0.5F,0.5F,-0.5F);
	            GL11.glScaled(1f/128, 1f/128, 1f/128);
	            GL11.glTranslated(0, 0, -0.001d);
	            if(te instanceof TileEntityMonitor)
	            {
	            	ForgeDirection side = ((TileEntityMonitor) te).inputdir.getOpposite();
	            	TileEntity tem = null;
	            	if(te != null && Tools.GetTileEntityOnServer(te.xCoord, te.yCoord, te.zCoord) instanceof TileEntityMonitor)
		            tem = ((TileEntityMonitor)Tools.GetTileEntityOnServer(te.xCoord, te.yCoord, te.zCoord)).machine;
		            if(tem instanceof Machine_Counter)
		            {
		            	mc.fontRenderer.drawString(((Machine_Counter)tem).CustomName(), 8, 8, 0xffffff);
		            	mc.fontRenderer.drawString(((Machine_Counter)tem).GetSyntaxe(), 8, 18, 0xffffff);
		            }
		            else if(tem instanceof Machine_FluidCounter)
		            {
		            	mc.fontRenderer.drawString(((Machine_FluidCounter)tem).CustomName(), 8, 8, 0xffffff);
		            	mc.fontRenderer.drawString(((Machine_FluidCounter)tem).GetSyntaxe(), 8, 18, 0xffffff);
		            }
		            else if(tem instanceof Machine_FluidFlowMeter)
		            {
		            	mc.fontRenderer.drawString(((Machine_FluidFlowMeter)tem).CustomName(), 8, 8, 0xffffff);
		            	mc.fontRenderer.drawString(((Machine_FluidFlowMeter)tem).GetSyntaxe(), 8, 18, 0xffffff);
		            }
		            else if(tem instanceof Machine_FluidName)
		            {
		            	mc.fontRenderer.drawString(((Machine_FluidName)tem).CustomName(), 8, 8, 0xffffff);
		            	mc.fontRenderer.drawString(((Machine_FluidName)tem).GetFluidName(), 8, 18, 0xffffff);
		            }
		            else if(tem instanceof Machine_FluidStorageInfo)
		            {
		            	mc.fontRenderer.drawString(((Machine_FluidStorageInfo)tem).CustomName(), 8, 8, 0xffffff);
		            	if(((Machine_FluidStorageInfo)tem).GetFluidStoredInfo() != null &&((Machine_FluidStorageInfo)tem).GetFluidStoredInfo().length > 0 && ((Machine_FluidStorageInfo)tem).GetFluidStoredInfo()[0] != null && ((Machine_FluidStorageInfo)tem).GetFluidStoredInfo()[0].fluid != null)
		            	{
		            		mc.fontRenderer.drawString(((Machine_FluidStorageInfo)tem).GetFluidStoredInfo()[0].fluid.amount+"/", 8, 18, 0xffffff);
		            		mc.fontRenderer.drawString(((Machine_FluidStorageInfo)tem).GetFluidStoredInfo()[0].capacity+" mB", 8, 28, 0xffffff);
		            	}
		            	
		            }
		            else if(tem instanceof Machine_FluidValve)
		            {
		            	mc.fontRenderer.drawString(((Machine_FluidValve)tem).CustomName(), 8, 8, 0xffffff);
		            	mc.fontRenderer.drawString(((Machine_FluidValve)tem).GetSyntaxe(), 8, 18, 0xffffff);
		            }
		            else if(tem instanceof Machine_FluxMeter)
		            {
		            	mc.fontRenderer.drawString(((Machine_FluxMeter)tem).CustomName(), 8, 8, 0xffffff);
		            	mc.fontRenderer.drawString(((Machine_FluxMeter)tem).GetSyntaxe(), 8, 18, 0xffffff);
		            }
		            else if(tem instanceof Machine_StorageInfo)
		            {
		            	mc.fontRenderer.drawString(((Machine_StorageInfo)tem).CustomName(), 8, 8, 0xffffff);
		            	mc.fontRenderer.drawString(((Machine_StorageInfo)tem).GetEnergyStored()+"/", 8, 18, 0xffffff);
		            	mc.fontRenderer.drawString(((Machine_StorageInfo)tem).GetMaxEnergyStored()+" RF", 8, 28, 0xffffff);
		            }
		            else if(tem instanceof Machine_Switch)
		            {
		            	mc.fontRenderer.drawString(((Machine_Switch)tem).CustomName(), 8, 8, 0xffffff);
		            	mc.fontRenderer.drawString(((Machine_Switch)tem).GetSyntaxe(), 8, 18, 0xffffff);
		            }
		            
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