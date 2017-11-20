package com.enm.guicontainer;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.enm.api.network.MachineNetWork;
import com.enm.api.network.Machine_Counter;
import com.enm.api.network.Machine_FluxMeter;
import com.enm.api.network.Machine_StorageInfo;
import com.enm.api.network.Machine_Switch;
import com.enm.api.network.NetWorkUtil;
import com.enm.api.util.Vector3;
import com.enm.container.ENMContainer;
import com.enm.guiutil.ResourceLocationRegister;
import com.enm.tileEntity.TileEntitySwitchControler;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;

public class GuiContainerSwitchControler extends GuiContainer
{
	List<TileEntity> machines;
	TileEntitySwitchControler sc;
	Vector3 editlink;
	int id_machine;
	
	public GuiContainerSwitchControler(InventoryPlayer pl, TileEntitySwitchControler te)
	{
		super(new ENMContainer());
		machines = NetWorkUtil.GetAllMachines(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord);
		editlink = te.ent.copy();
		sc = te;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float tick_, int x_, int y_)
	{
		int x = width/2 - 75;
		int y = height/2 - 20;
		
		drawString(fontRendererObj, "[Enter] valid [Up Down] select", x, y + 45, 0xffff00);
		drawString(fontRendererObj, "[Esc] cancel", x, y + 55, 0xffff00);
		
		if(machines != null)
		for(TileEntity t: machines)
		{
			if(!(t instanceof TileEntitySwitchControler) && t.xCoord == editlink.x && t.yCoord == editlink.y && t.zCoord == editlink.z)
			{
				drawelement(t, x, y);
				break;
			}
		}
	}
	
	public void drawelement(TileEntity tem, int x2, int y2)
	{
		if(tem instanceof Machine_Switch)
		{
			mc.renderEngine.bindTexture(ResourceLocationRegister.texture_pixel255);
			setColorRGBA(128, 0, 0, 255);
			drawTexturedModalRect(x2, y2, 0, 0, 150, 40);
			drawString(fontRendererObj, "Type: "+((Machine_Switch)tem).name(), x2+1, y2+1, 0xffffff);
			drawString(fontRendererObj, "CustomName: "+((Machine_Switch)tem).CustomName(), x2+1, y2+11, 0xffffff);
			drawString(fontRendererObj, "Pos: ["+tem.xCoord+","+tem.yCoord+","+tem.zCoord+"]", x2+1, y2+21, 0xffffff);
			drawString(fontRendererObj, "Switch position: "+ONOFF(((Machine_Switch)tem).SwitchGetPosition()), x2+1, y2+31, 0xffffff);
		}
		else if(tem instanceof Machine_Counter)
		{
			mc.renderEngine.bindTexture(ResourceLocationRegister.texture_pixel255);
			setColorRGBA(0, 128, 0, 255);
			drawTexturedModalRect(x2, y2, 0, 0, 150, 40);
			drawString(fontRendererObj, "Type: "+((MachineNetWork)tem).name(), x2+1, y2+1, 0xffffff);
			drawString(fontRendererObj, "CustomName: "+((MachineNetWork)tem).CustomName(), x2+1, y2+11, 0xffffff);
			drawString(fontRendererObj, "Pos: ["+tem.xCoord+","+tem.yCoord+","+tem.zCoord+"]", x2+1, y2+21, 0xffffff);
			drawString(fontRendererObj, "Consomation: "+((Machine_Counter)tem).GetSyntaxe(), x2+1, y2+31, 0xffffff);
		}
		else if(tem instanceof Machine_FluxMeter)
		{
			mc.renderEngine.bindTexture(ResourceLocationRegister.texture_pixel255);
			setColorRGBA(0, 0, 128, 255);
			drawTexturedModalRect(x2, y2, 0, 0, 150, 40);
			drawString(fontRendererObj, "Type: "+((MachineNetWork)tem).name(), x2+1, y2+1, 0xffffff);
			drawString(fontRendererObj, "CustomName: "+((MachineNetWork)tem).CustomName(), x2+1, y2+11, 0xffffff);
			drawString(fontRendererObj, "Pos: ["+tem.xCoord+","+tem.yCoord+","+tem.zCoord+"]", x2+1, y2+21, 0xffffff);
			drawString(fontRendererObj, "Flux: "+((Machine_FluxMeter)tem).GetFlux(), x2+1, y2+31, 0xffffff);
		}
		else if(tem instanceof Machine_StorageInfo)
		{
			mc.renderEngine.bindTexture(ResourceLocationRegister.texture_pixel255);
			setColorRGBA(128, 0, 128, 255);
			drawTexturedModalRect(x2, y2, 0, 0, 150, 40);
			drawString(fontRendererObj, "Type: "+((MachineNetWork)tem).name(), x2+1, y2+1, 0xffffff);
			drawString(fontRendererObj, "CustomName: "+((MachineNetWork)tem).CustomName(), x2+1, y2+11, 0xffffff);
			drawString(fontRendererObj, "Pos: ["+tem.xCoord+","+tem.yCoord+","+tem.zCoord+"]", x2+1, y2+21, 0xffffff);
			drawString(fontRendererObj, "Bat: "+((Machine_StorageInfo)tem).GetEnergyStored()+"/"+((Machine_StorageInfo)tem).GetMaxEnergyStored()+" RF", x2+1, y2+31, 0xffffff);
		}
		else if(tem instanceof MachineNetWork)
		{
			mc.renderEngine.bindTexture(ResourceLocationRegister.texture_pixel255);
			setColorRGBA(128, 128, 0, 255);
			drawTexturedModalRect(x2, y2, 0, 0, 150, 40);
			drawString(fontRendererObj, "Type: "+((MachineNetWork)tem).name(), x2+1, y2+1, 0xffffff);
			drawString(fontRendererObj, "CustomName: "+((MachineNetWork)tem).CustomName(), x2+1, y2+11, 0xffffff);
			drawString(fontRendererObj, "Pos: ["+tem.xCoord+","+tem.yCoord+","+tem.zCoord+"]", x2+1, y2+21, 0xffffff);
		}
	}
	
	@Override
	protected void keyTyped(char c, int id)
	{
		if(id == 200)//up
		{
			List<TileEntity> machines_sp = new ArrayList<TileEntity>();
			if(machines != null)
			for(TileEntity te : machines)
			{
				if(te instanceof Machine_Switch)machines_sp.add(te);
			}
			
			if(id_machine > 0)--id_machine;
			else id_machine = machines_sp.size()-1;
			
			if(machines_sp.size() > 0)
			{
				TileEntity mt = machines_sp.get(id_machine);
				editlink = new Vector3(mt.xCoord, mt.yCoord, mt.zCoord);
			}
		}
		else if(id == 208)//down
		{
			List<TileEntity> machines_sp = new ArrayList<TileEntity>();
			if(machines != null)
			for(TileEntity te : machines)
			{
				if(te instanceof Machine_Switch)machines_sp.add(te);
			}
			
			if(id_machine < machines_sp.size()-1)++id_machine;
			else id_machine = 0;
			
			if(machines_sp.size() > 0)
			{
				TileEntity mt = machines_sp.get(id_machine);
				editlink = new Vector3(mt.xCoord, mt.yCoord, mt.zCoord);
			}
		}
			
			if(id == 28)//enter
			{
				sc.ent = editlink.copy();
				super.keyTyped((char)27, 1);
			}
		super.keyTyped(c, id);
	}
	
	public void setColorRGBA(int i, int j, int k, int l)
	{
		GL11.glColor4f((1f/255f)*(float)i, (1f/255f)*(float)j, (1f/255f)*(float)k, (1f/255f)*(float)l);
	}
	public String ONOFF(boolean state)
	{
		return state ? "ON" : "OFF";
	}

}
