package com.enm.core;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.enm.api.network.ListMachinesUpdate;
import com.enm.api.network.NetWork;
import com.enm.api.network.NetWorkUtil;
import com.enm.guicontainer.GuiContainerTerminal;
import com.enm.guiutil.ResourceLocationRegister;

import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.world.BlockEvent;

public class EventHandlerRegister
{
	GuiScreen lastGui;
	
	@SubscribeEvent
    public void tickEvent(TickEvent.RenderTickEvent event)
	{
		Minecraft mc = Minecraft.getMinecraft();
		if(event.phase == Phase.END && mc.currentScreen != null)
		{
			if(mc.currentScreen instanceof GuiContainerTerminal)
			{
				GuiContainerTerminal term = (GuiContainerTerminal) mc.currentScreen;
			}
		}
	}
	
	@SubscribeEvent
    public void tickBlockEvent(BlockEvent event)
	{
		TileEntity te = event.world.getTileEntity(event.x, event.y, event.z);
		if(te instanceof NetWork)
		{
			List<TileEntity> machines = NetWorkUtil.GetAllMachines(event.world, event.x, event.y, event.z);
			for(TileEntity machine : machines)
			{
				if(machine instanceof ListMachinesUpdate)
				{
					((ListMachinesUpdate)machine).onEventListMachinesUpdate();
				}
			}
		}
	}
}
