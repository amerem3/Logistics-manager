package com.enm.guicontainer;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.enm.api.util.Vector2;
import com.enm.container.ENMContainer;
import com.enm.core.Config;
import com.enm.guipanel.GuiPanel;
import com.enm.guipanel.PanelFluidMyInstallation;
import com.enm.guipanel.PanelMaterialList;
import com.enm.guipanel.PanelMyInstallation;
import com.enm.guipanel.myinstallation.Map;
import com.enm.guiutil.ActionPerformed;
import com.enm.guiutil.ResourceLocationRegister;
import com.enm.tileEntity.TileEntityTerminal;
import com.enm.util.BufferMemory;
import com.enm.util.NEI;
import com.enm.util.Tools;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiContainerTerminal extends GuiContainer
{
	public TileEntityTerminal tertminal_te;
	//ResourceLocation texture = new ResourceLocation(CoreMod.MODID ,"textures/guis/pixel255.png");
	public Vector2 mousepos = new Vector2(0, 0);
	int width_save, height_save;
	
	//menu
	//public boolean menu_dep;
	public String[] menu_list = new String[]{"materiel list", "my Installation", "info"};
	//public int menu_id = 0;
	
	public GuiPanel panel;
	
	public int guiScale = 1;
	
	public void addbutton(GuiButton b)
	{
		buttonList.add(b);
	}
	
	public List<GuiButton> getlistbutton()
	{
		return buttonList;
	}
	
	public GuiContainerTerminal(InventoryPlayer pl, TileEntityTerminal te)
	{
		super(new ENMContainer());
		tertminal_te = te;
		fontRendererObj = Minecraft.getMinecraft().fontRenderer;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
	{
		GL11.glEnable(GL11.GL_BLEND);
		//drawBackground(0);
		this.mc.renderEngine.bindTexture(ResourceLocationRegister.texture_pixel255);
		
		setColorRGBA(32, 32, 32, 255);
		this.drawTexturedModalRect(0, 0, 0, 0, width, height);
		
		//draw panel
		if(panel != null) panel.draw();
		else
		{
			this.drawCenteredString(fontRendererObj, "select the panel", width/2, height/2-75, 0xffffff);
			
			if(mousepos.y >= (height/2)-50 && mousepos.y < (height/2)+50)
			{
				if(mousepos.x >= (width/2)-215 && mousepos.x < (width/2)-115)
				{
					//System.out.println("my instalaction");
					this.drawCenteredString(fontRendererObj, "monitoring", width/2, height/2-65, 0xffffff);
				}
				else if(mousepos.x >= (width/2)-105 && mousepos.x < (width/2)-5)
				{
					//System.out.println("material list");
					this.drawCenteredString(fontRendererObj, "material list", width/2, height/2-65, 0xffffff);
				}
				else if(mousepos.x >= (width/2)+5 && mousepos.x < (width/2)+105)
				{
					//System.out.println("info");
					this.drawCenteredString(fontRendererObj, "information [in development]", width/2, height/2-65, 0xffffff);
				}
				else if(mousepos.x >= (width/2)+115 && mousepos.x < (width/2)+215)
				{
					//System.out.println("parameter");
					this.drawCenteredString(fontRendererObj, "parameter [in development]", width/2, height/2-65, 0xffffff);
				}
			}
			
			this.mc.renderEngine.bindTexture(ResourceLocationRegister.texture_terminal_icon);
			setColorRGBA(255, 255, 255, 255);
			this.drawTexturedModalRect((width/2)-215, (height/2)-50, 0, 0, 100, 100);
			this.drawTexturedModalRect((width/2)-105, (height/2)-50, 100, 0, 100, 100);
			this.drawTexturedModalRect((width/2)+5, (height/2)-50, 0, 100, 100, 100);
			this.drawTexturedModalRect((width/2)+115, (height/2)-50, 100, 100, 100, 100);
			
		}
		//overlay
		if(tertminal_te.guiinfo.menu_dep)
		{
			this.mc.renderEngine.bindTexture(ResourceLocationRegister.texture_pixel255);
			setColorRGBA(0, 148, 255, 200);
			this.drawTexturedModalRect(10, 10, 0, 0, 100, 10*menu_list.length);
			
			setColorRGBA(255, 255, 255, 200);
			this.drawTexturedModalRect(10, 10 + (10*tertminal_te.guiinfo.menu_id), 0, 0, 100, 10);
			
			for(int i = 0; i < menu_list.length; ++i)
			{
				if(i == tertminal_te.guiinfo.menu_id)this.drawString(fontRendererObj, menu_list[i], 12, 11 + (i*10), 0x55f5f5);
				else this.drawString(fontRendererObj, menu_list[i], 12, 11 + (i*10), 0xffffff);
			}
		}
		
		this.mc.renderEngine.bindTexture(ResourceLocationRegister.texture_pixel255);
		setColorRGBA(0, 148, 255, 100);
		this.drawTexturedModalRect(0, 0, 0, 0, width, 22);
		
		this.mc.renderEngine.bindTexture(ResourceLocationRegister.texture_terminal_icon);
		setColorRGBA(255, 255, 255, 255);
		this.drawTexturedModalRect(1, 1, 200, 40, 40, 20);
		if(panel != null)
		{
			this.drawTexturedModalRect(43, 1, 200, 0, 40, 20);
			if(panel instanceof PanelMyInstallation || panel instanceof PanelFluidMyInstallation)
			{
				this.drawTexturedModalRect(85, 1, 200, 80, 40, 20);
				if(this.tertminal_te.guiinfo.editmode)
				{
					this.drawTexturedModalRect(127, 1, 200, 120, 40, 20);
					this.drawTexturedModalRect(169, 1, 200, 160, 40, 20);
					this.drawTexturedModalRect(211, 1, 200, 200, 40, 20);
				}
			}
		}
		
		//this.drawString(fontRendererObj, info, 2, height - 9, 0xffffff);
		
		GL11.glDisable(GL11.GL_BLEND);
	}

	public void setColorRGBA(int i, int j, int k, int l)
	{
		GL11.glColor4f((1f/255f)*(float)i, (1f/255f)*(float)j, (1f/255f)*(float)k, (1f/255f)*(float)l);
	}
	
	@Override
	public void updateScreen()
	{
		super.updateScreen();
		Tools.GetMouseLocalisationNoMC(mousepos, guiScale);
		//Tools.GetMouseLocalisation(mousepos, width, height, width, height);
		//System.out.println(Display.getPixelScaleFactor());
		if(panel != null)
		{
			panel.update();
			if(width_save != width || height_save != height)
			{
				width_save = width; height_save = height;
				panel.screenResized(width, height);
			}
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton p_146284_1_)
	{
		if(panel != null && panel instanceof ActionPerformed)((ActionPerformed)panel).actionPerformed(p_146284_1_);
	}
	
	@Override
	protected void mouseClicked(int MCx, int MCy, int click)
	{
		if(panel != null)
		{
			//panel.eventMouse(MCx, MCy, click);
			if(MCy >= 1 && MCy < 20 && MCx >= 1 && MCx <= 41)
			{
				this.mc.thePlayer.closeScreen();
			}
			else if(MCy >= 1 && MCy < 20 && MCx >= 43 && MCx < 83)
			{
				if(panel != null)
				{
					if(tertminal_te.tag_gui != null)
					{
						tertminal_te.tag_gui.setInteger("panel", -1);
					}
					panel.panelClose();
					panel = null;
					return;
				}
			}
			else if(panel instanceof PanelMyInstallation)
			{
				if(MCy >= 1 && MCy < 20 && MCx >= 85 && MCx < 125 && ((PanelMyInstallation)panel).editlink == null)
				{
					this.tertminal_te.guiinfo.editmode = !this.tertminal_te.guiinfo.editmode;
					return;
				}
				else if(MCy >= 1 && MCy < 20 && MCx >= 127 && MCx < 167 && this.tertminal_te.guiinfo.editmode)
				{
					((PanelMyInstallation)panel).map.saveconf(tertminal_te);
					return;
				}
				else if(MCy >= 1 && MCy < 20 && MCx >= 169 && MCx < 209 && this.tertminal_te.guiinfo.editmode)
				{
					((PanelMyInstallation)panel).map.loadconf(tertminal_te);
					return;
				}
				else if(MCy >= 1 && MCy < 20 && MCx >= 211 && MCx < 251 && this.tertminal_te.guiinfo.editmode)
				{
					((PanelMyInstallation)panel).map = new Map(true);
					return;
				}
			}
			else if(panel instanceof PanelFluidMyInstallation)
			{
				if(MCy >= 1 && MCy < 20 && MCx >= 85 && MCx < 125 && ((PanelFluidMyInstallation)panel).editlink == null)
				{
					this.tertminal_te.guiinfo.editmode = !this.tertminal_te.guiinfo.editmode;
					return;
				}
				else if(MCy >= 1 && MCy < 20 && MCx >= 127 && MCx < 167)
				{
					((PanelFluidMyInstallation)panel).map.saveconf(tertminal_te);
					return;
				}
				else if(MCy >= 1 && MCy < 20 && MCx >= 169 && MCx < 209)
				{
					((PanelFluidMyInstallation)panel).map.loadconf(tertminal_te);
					return;
				}
				else if(MCy >= 1 && MCy < 20 && MCx >= 211 && MCx < 251)
				{
					((PanelFluidMyInstallation)panel).map = new Map(true);
					return;
				}
			}
			
			panel.eventMouse(MCx, MCy, click);
			
		}
		else
		{
			if(MCy >= 1 && MCy < 20 && MCx >= 1 && MCx <= 40)
			{
				super.keyTyped((char)1, 1);
			}
			else if(MCy >= (height/2)-50 && MCy < (height/2)+50)
				{
					if(MCx >= (width/2)-215 && MCx < (width/2)-115)
					{
						PanelLoad(1);
					}
					else if(MCx >= (width/2)-105 && MCx < (width/2)-5)
					{
						PanelLoad(0);
					}
					else if(MCx >= (width/2)+5 && MCx < (width/2)+105)
					{
						
					}
					else if(MCx >= (width/2)+115 && MCx < (width/2)+215)
					{
						
					}
				}
		}
	}
	
	@Override
	public void onGuiClosed()
	{
		Minecraft.getMinecraft().gameSettings.guiScale = BufferMemory.integer_001;
		BufferMemory.bool_001 = false;
		Minecraft.getMinecraft().gameSettings.saveOptions();
		Minecraft.getMinecraft().gameSettings.sendSettingsToServer();
		
		if(panel != null)panel.panelClose();
		tertminal_te.updateInventory();
		
		NEI.setInternalEnabled(true);
	}
	
	@Override
	public void initGui()
	{
		NEI.setInternalEnabled(false);
		
		if(!BufferMemory.bool_001)
		{
			BufferMemory.integer_001 = Minecraft.getMinecraft().gameSettings.guiScale;
			BufferMemory.bool_001 = true;
			Minecraft.getMinecraft().gameSettings.guiScale = Config.conf.getInt("terminal gui scale", "general", 1, 1, 2, "");;//TODO
			Minecraft.getMinecraft().gameSettings.saveOptions();
			Minecraft.getMinecraft().gameSettings.sendSettingsToServer();
			guiScale = Minecraft.getMinecraft().gameSettings.guiScale;
			
			if(guiScale == 1 || guiScale == 2)
			{
				width = Minecraft.getMinecraft().displayWidth/guiScale;
				height = Minecraft.getMinecraft().displayHeight/guiScale +1;
			}
			
			//System.out.println(width);
			if(tertminal_te.tag_gui != null)
			{
				if(tertminal_te.tag_gui.hasKey("panel"))
				{
					PanelLoad(tertminal_te.tag_gui.getInteger("panel"));
				}
			}
		}
		super.initGui();
	}
	
	@Override
	protected void keyTyped(char c, int cid)
	{
		if(panel != null) panel.eventKeyBoard(c, cid);
		
		if(cid == 1)super.keyTyped(c, cid);
	}
	
	public void PanelLoad(int id)
	{
		tertminal_te.guiinfo.menu_dep = false;
		int saveid = -1;
		if(panel != null)
		{
			panel.panelClose();
			panel = null;
		}
		
		if(id == 0)
		{
			panel = new PanelMaterialList();
			panel.init(this);
			saveid = id;
		}
		else if(id == 1)
		{
			panel = new PanelMyInstallation();
			panel.init(this);
			saveid = id;
		}
		
		if(tertminal_te.tag_gui != null)
		{
			tertminal_te.tag_gui.setInteger("panel", saveid);
		}
	}

}
