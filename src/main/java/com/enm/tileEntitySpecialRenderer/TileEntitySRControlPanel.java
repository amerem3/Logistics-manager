package com.enm.tileEntitySpecialRenderer;

import org.lwjgl.opengl.GL11;

import com.enm.core.CoreMod;
import com.enm.guipanel.myinstallation.Map;
import com.enm.guiutil.ResourceLocationRegister;
import com.enm.model.ModelLogicMachineFrame;
import com.enm.register.RItems;
import com.enm.tileEntity.TileEntityControlPanel;
import com.enm.util.Direction;
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

public class TileEntitySRControlPanel extends TileEntitySpecialRenderer
{
	ResourceLocation t = new ResourceLocation(CoreMod.MODID+":textures/blocks/models/controlpanel.png");
	Minecraft mc = Minecraft.getMinecraft();
	private final ModelLogicMachineFrame model;
	    
	    public TileEntitySRControlPanel()
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
	            if(te instanceof TileEntityControlPanel)
	            {
	            	TileEntityControlPanel cp = (TileEntityControlPanel) te;
	            	if(cp.inputdir == ForgeDirection.EAST)GL11.glRotatef(90, 0, 1, 0);
	            	else if(cp.inputdir == ForgeDirection.SOUTH)GL11.glRotatef(180, 0, 1, 0);
	            	else if(cp.inputdir == ForgeDirection.WEST)GL11.glRotatef(270, 0, 1, 0);
	            }
	            this.model.render((Entity)null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
	            
	            GL11.glTranslatef(-0.5F,0.5F,-0.5F);
	            GL11.glScaled(1f/128, 1f/128, 1f/128);
	            GL11.glTranslated(0, 0, -0.001d);
	            if(te instanceof TileEntityControlPanel)
	            {
	            	TileEntityControlPanel cp = (TileEntityControlPanel) te;
	            	int dx = 0;
	            	int dy = 0;
	            	
	            	if(mc.objectMouseOver.sideHit == cp.inputdir.ordinal()
	            			//&& (Tools.ItemHeldIsEquals(mc, RItems.Item_linker) || Tools.ItemHeldIsEquals(mc, RItems.Item_ControlComponent))
	            			&& mc.objectMouseOver.blockX == te.xCoord && mc.objectMouseOver.blockY == te.yCoord && mc.objectMouseOver.blockZ == te.zCoord)
	            	{
	            		dy = (int)((1-((mc.objectMouseOver.hitVec.yCoord)-((int)mc.objectMouseOver.hitVec.yCoord)))*2);
	            		if(cp.inputdir == ForgeDirection.EAST)dx = (int)((1-((mc.objectMouseOver.hitVec.zCoord)-((int)mc.objectMouseOver.hitVec.zCoord)))*2);
	            		if(cp.inputdir == ForgeDirection.NORTH)dx = (int)((1-((mc.objectMouseOver.hitVec.xCoord)-((int)mc.objectMouseOver.hitVec.xCoord)))*2);
	            		if(cp.inputdir == ForgeDirection.SOUTH)dx = (int)(((mc.objectMouseOver.hitVec.xCoord)-((int)mc.objectMouseOver.hitVec.xCoord))*2);
	            		if(cp.inputdir == ForgeDirection.WEST)dx = (int)(((mc.objectMouseOver.hitVec.zCoord)-((int)mc.objectMouseOver.hitVec.zCoord))*2);
	            		
	            		//mc.fontRenderer.drawString("X:"+dx+"Y:"+dy, 1, 1, 0xffffff);
	            		//mc.objectMouseOver.hitVec
	            		//mc.fontRenderer.drawString("Y:", 1, 1, 0xffffff);
	            		
	            		//draw texture 2x2 for icon
		            	GL11.glScaled(3.2f, 3.2f, 3.2f);
		            	GL11.glTranslatef(0, 0, 89.9f);
		            	mc.renderEngine.bindTexture(ResourceLocationRegister.texture_guitool2);
		            	
	        			//mc.renderEngine.bindTexture(ResourceLocationRegister.texture_guitool2);
	        			GL11.glColor3f(1, 1, 0);
		            	mc.ingameGUI.drawTexturedModalRect(20*dx, 20*dy, 160, 0, 20, 20);
		            	GL11.glColor3f(1, 1, 1);
	            	}
	            	else
	            	{
	            		GL11.glScaled(3.2f, 3.2f, 3.2f);
		            	GL11.glTranslatef(0, 0, 89.9f);
		            	mc.renderEngine.bindTexture(ResourceLocationRegister.texture_guitool2);
	            	}
	            	
	            	for(int m_x = 0; m_x < 2; ++m_x)
	            	{
	            		for(int m_y = 0; m_y < 2; ++m_y)
		            	{
	            			Map.Icon icon = Map.Icon.values()[cp.map[m_x+(m_y*2)]];
	            			
							int ix = icon.x, iy = icon.y, startx = icon.startx, starty = icon.starty, endx = icon.endx, endy = icon.endy;
							//TODO
							if(startx > 0 && IsOnMap(m_x -1, m_y) && getIcon(m_x -1, m_y, cp.map[(m_x-1)+(m_y*2)]).endx < 20) startx = 0;
							if(starty > 0 && IsOnMap(m_x, m_y -1) && getIcon(m_x, m_y -1, cp.map[m_x+((m_y-1)*2)]).endy < 20) starty = 0;
							if(endx < 20 && IsOnMap(m_x +1, m_y) && getIcon(m_x +1, m_y, cp.map[m_x+1+(m_y*2)]).startx > 0) endx = 20;
							if(endy < 20 && IsOnMap(m_x, m_y +1) && getIcon(m_x, m_y +1, cp.map[m_x+((m_y+1)*2)]).starty > 0) endy = 20;
							
							if(m_x == 0 && m_y == 0)
							{
								if(te.getWorldObj() != null)
								{
									if(startx > 0)
									{
										TileEntity left_te = Tools.GetTileEntityAt(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord, cp.inputdir, Direction.RIGHT);
										if(left_te instanceof TileEntityControlPanel && Map.Icon.values()[((TileEntityControlPanel)left_te).map[1]].endx < 20) startx = 0;
									}
									if(starty > 0)
									{
										TileEntity up_te = Tools.GetTileEntityAt(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord, cp.inputdir, Direction.UP);
										if(up_te instanceof TileEntityControlPanel && Map.Icon.values()[((TileEntityControlPanel)up_te).map[2]].endy < 20) starty = 0;
									}
									
								}
							}
							else if(m_x == 1 && m_y == 0)
							{
								if(te.getWorldObj() != null)
								{
									if(endx < 20)
									{
										TileEntity left_te = Tools.GetTileEntityAt(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord, cp.inputdir, Direction.LEFT);
										if(left_te instanceof TileEntityControlPanel && Map.Icon.values()[((TileEntityControlPanel)left_te).map[0]].startx > 0) endx = 20;
									}
									if(starty > 0)
									{
										TileEntity up_te = Tools.GetTileEntityAt(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord, cp.inputdir, Direction.UP);
										if(up_te instanceof TileEntityControlPanel && Map.Icon.values()[((TileEntityControlPanel)up_te).map[3]].endy < 20) starty = 0;
									}
									
								}
							}
							else if(m_x == 0 && m_y == 1)
							{
								if(te.getWorldObj() != null)
								{
									if(startx > 0)
									{
										TileEntity left_te = Tools.GetTileEntityAt(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord, cp.inputdir, Direction.RIGHT);
										if(left_te instanceof TileEntityControlPanel && Map.Icon.values()[((TileEntityControlPanel)left_te).map[3]].endx < 20) startx = 0;
									}
									if(endy < 20)
									{
										TileEntity up_te = Tools.GetTileEntityAt(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord, cp.inputdir, Direction.DOWN);
										if(up_te instanceof TileEntityControlPanel && Map.Icon.values()[((TileEntityControlPanel)up_te).map[0]].starty > 0) endy = 20;
									}
									
								}
							}
							else if(m_x == 1 && m_y == 1)
							{
								if(te.getWorldObj() != null)
								{
									if(endx > 0)
									{
										TileEntity left_te = Tools.GetTileEntityAt(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord, cp.inputdir, Direction.LEFT);
										if(left_te instanceof TileEntityControlPanel && Map.Icon.values()[((TileEntityControlPanel)left_te).map[2]].startx > 0) endx = 20;
									}
									if(endy < 20)
									{
										TileEntity up_te = Tools.GetTileEntityAt(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord, cp.inputdir, Direction.DOWN);
										if(up_te instanceof TileEntityControlPanel && Map.Icon.values()[((TileEntityControlPanel)up_te).map[1]].starty > 0) endy = 20;
									}
									
								}
							}
							
							if(cp.map[m_x+(m_y*2)] != 0)mc.ingameGUI.drawTexturedModalRect(m_x*20 + startx, m_y*20 + starty, (20*ix) + startx, (20*iy) + starty, endx - startx, endy - starty);
		            	}
	            	}
	            }
	            GL11.glPopMatrix();
	            GL11.glPopMatrix();
	    }
	    
	    public boolean IsOnMap(int x, int y)
		{
			return (2 > x && x >= 0 && 2 > y && y >= 0);
		}
	    
	    public Map.Icon getIcon(int x, int y, int id)
		{
			if(2 > x && x >= 0 && 2 > y && y >= 0)
			{
				return Map.Icon.values()[id];
			}
			return null;
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