package com.enm.guicontainer;

import org.lwjgl.input.Keyboard;

import com.enm.guipanel.PanelFluidMaterialList;
import com.enm.guipanel.PanelFluidMyInstallation;
import com.enm.guipanel.PanelMyInstallation;
import com.enm.tileEntity.TileEntityTerminal;

import net.minecraft.entity.player.InventoryPlayer;

public class GuiContainerFluidTerminal extends GuiContainerTerminal
{	
	public GuiContainerFluidTerminal(InventoryPlayer pl, TileEntityTerminal te)
	{
		super(pl, te);
	}
	
	@Override
	protected void keyTyped(char c, int cid)
	{
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && cid == 1)
		{
			if(panel != null)
			{
				if(tertminal_te.tag_gui != null)
				{
					tertminal_te.tag_gui.setInteger("panel", -1);
				}
				panel.panelClose();
				panel = null;
			}
			return;
		}
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
			panel = new PanelFluidMaterialList();
			panel.init(this);
			saveid = id;
		}
		else if(id == 1)
		{
			panel = new PanelFluidMyInstallation();
			panel.init(this);
			saveid = id;
		}
		
		if(tertminal_te.tag_gui != null)
		{
			tertminal_te.tag_gui.setInteger("panel", saveid);
		}
	}
}
