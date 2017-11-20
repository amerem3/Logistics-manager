package com.enm.network;

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
import com.enm.guicontainer.GuiContainerRedstoneSwitch;
import com.enm.guicontainer.GuiContainerSwitch;
import com.enm.guicontainer.GuiContainerTerminal;
import com.enm.guipanel.PanelFluidMyInstallation;
import com.enm.guipanel.PanelMyInstallation;
import com.enm.guipanel.myinstallation.Map;
import com.enm.tileEntity.TileEntityRedstoneSwitch;
import com.enm.tileEntity.TileEntitySwitch;
import com.enm.tileEntity.TileEntityTerminal;
import com.enm.tileEntityutil.TEServerUpdate;
import com.enm.util.Tools;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidTankInfo;

public class NetWorkTileEntityNBT implements IMessage
{
	int x;
	int y;
	int z;
	NBTTagCompound contents;
	
	public NetWorkTileEntityNBT()
	{
		
	}
	
	public NetWorkTileEntityNBT(int _x, int _y, int _z, NBTTagCompound _contents)
	{
		x = _x;
		y = _y;
		z = _z;
		contents = _contents;
	}
	@Override
	public void fromBytes(ByteBuf bytebuffer)
	{
		NBTTagCompound input = ByteBufUtils.readTag(bytebuffer);
		contents = input.getCompoundTag("CONTENTS");
		x = input.getInteger("X");
		y = input.getInteger("Y");
		z = input.getInteger("Z");
	}

	@Override
	public void toBytes(ByteBuf bytebuffer)
	{
		NBTTagCompound output = new NBTTagCompound();
		output.setTag("CONTENTS", contents);
		output.setInteger("X", x);
		output.setInteger("Y", y);
		output.setInteger("Z", z);
		ByteBufUtils.writeTag(bytebuffer, output);
	}
	
	public static class Handler implements IMessageHandler<NetWorkTileEntityNBT, IMessage>
	{

		@Override
		public IMessage onMessage(NetWorkTileEntityNBT msg, MessageContext msgcontext)
		{
			if(Tools.isServer())
			{
				MinecraftServer mcs = MinecraftServer.getServer();
				TileEntity t = mcs.getEntityWorld().getTileEntity(msg.x, msg.y, msg.z);
				
				if(msg.contents.hasKey("bk01"))
				{
					int id = msg.contents.getInteger("bk01");
					
					if(id == 1)
					{
						if(t instanceof TileEntityTerminal)
						{
							TileEntityTerminal term = (TileEntityTerminal) t;
							Map m = new Map(true);
							m.loadconfServer(term);
							
							NBTTagCompound tag = new NBTTagCompound();
							m.SaveToNBT(tag);
							
							tag.setByte("forTerminal", (byte)0);
							
							String name = msg.contents.getString("player");
							CoreMod.network_TileEntityNBT.sendTo(new NetWorkTileEntityNBT(msg.x, msg.y, msg.z, tag), (EntityPlayerMP) mcs.getEntityWorld().getPlayerEntityByName(name));
						}
					}
					else if(id == 2)
					{
						if(t instanceof TileEntityTerminal)
						{
							TileEntityTerminal term = (TileEntityTerminal) t;
							Map m = new Map(true);
							m.ReadNBT(msg.contents);
							m.saveconfServer(term);
						}
					}
					else if(id == 3)
					{
						String cmds = msg.contents.getString("cmd");
						
						if(t instanceof Machine_StorageInfo)
						{
							Machine_StorageInfo m =(Machine_StorageInfo)t;
							NBTTagCompound tag = new NBTTagCompound();
							for(String cmd : cmds.split(":"))
							{
								if(cmd.equalsIgnoreCase("GetSyntaxe"))tag.setString("GetSyntaxe", m.GetSyntaxe());
								else if(cmd.equalsIgnoreCase("GetEnergyStored"))tag.setInteger("GetEnergyStored", m.GetEnergyStored());
								else if(cmd.equalsIgnoreCase("GetMaxEnergyStored"))tag.setInteger("GetMaxEnergyStored", m.GetMaxEnergyStored());
								else if(cmd.equalsIgnoreCase("CustomName"))tag.setString("CustomName", m.CustomName());
								else if(cmd.equalsIgnoreCase("name"))tag.setString("name", m.name());
							}
							String name = msg.contents.getString("player");
							CoreMod.network_TileEntityNBT.sendTo(new NetWorkTileEntityNBT(msg.x, msg.y, msg.z, tag), (EntityPlayerMP) mcs.getEntityWorld().getPlayerEntityByName(name));
						}
						else if(t instanceof Machine_Counter)
						{
							Machine_Counter m =(Machine_Counter)t;
							NBTTagCompound tag = new NBTTagCompound();
							for(String cmd : cmds.split(":"))
							{
								if(cmd.equalsIgnoreCase("GetSyntaxe"))tag.setString("GetSyntaxe", m.GetSyntaxe());
								else if(cmd.equalsIgnoreCase("GetConsomation"))tag.setLong("GetConsomation", m.GetConsomation());
								else if(cmd.equalsIgnoreCase("Reset"))m.Reset();
								else if(cmd.equalsIgnoreCase("CustomName"))tag.setString("CustomName", m.CustomName());
								else if(cmd.equalsIgnoreCase("name"))tag.setString("name", m.name());
							}
							String name = msg.contents.getString("player");
							CoreMod.network_TileEntityNBT.sendTo(new NetWorkTileEntityNBT(msg.x, msg.y, msg.z, tag), (EntityPlayerMP) mcs.getEntityWorld().getPlayerEntityByName(name));
						}
						else if(t instanceof Machine_FluidCounter)
						{
							Machine_FluidCounter m =(Machine_FluidCounter)t;
							NBTTagCompound tag = new NBTTagCompound();
							for(String cmd : cmds.split(":"))
							{
								if(cmd.equalsIgnoreCase("GetSyntaxe"))tag.setString("GetSyntaxe", m.GetSyntaxe());
								else if(cmd.equalsIgnoreCase("GetConsomation"))tag.setLong("GetConsomation", m.GetConsomation());
								else if(cmd.equalsIgnoreCase("Reset"))m.Reset();
								else if(cmd.equalsIgnoreCase("CustomName"))tag.setString("CustomName", m.CustomName());
								else if(cmd.equalsIgnoreCase("name"))tag.setString("name", m.name());
							}
							String name = msg.contents.getString("player");
							CoreMod.network_TileEntityNBT.sendTo(new NetWorkTileEntityNBT(msg.x, msg.y, msg.z, tag), (EntityPlayerMP) mcs.getEntityWorld().getPlayerEntityByName(name));
						}
						else if(t instanceof Machine_FluidFlowMeter)
						{
							Machine_FluidFlowMeter m =(Machine_FluidFlowMeter)t;
							NBTTagCompound tag = new NBTTagCompound();
							for(String cmd : cmds.split(":"))
							{
								if(cmd.equalsIgnoreCase("GetSyntaxe"))tag.setString("GetSyntaxe", m.GetSyntaxe());
								else if(cmd.equalsIgnoreCase("CustomName"))tag.setString("CustomName", m.CustomName());
								else if(cmd.equalsIgnoreCase("name"))tag.setString("name", m.name());
								else if(cmd.equalsIgnoreCase("GetFlow"))tag.setInteger("GetFlow", m.GetFlow());
							}
							String name = msg.contents.getString("player");
							CoreMod.network_TileEntityNBT.sendTo(new NetWorkTileEntityNBT(msg.x, msg.y, msg.z, tag), (EntityPlayerMP) mcs.getEntityWorld().getPlayerEntityByName(name));
						}
						else if(t instanceof Machine_FluidName)
						{
							Machine_FluidName m =(Machine_FluidName)t;
							NBTTagCompound tag = new NBTTagCompound();
							for(String cmd : cmds.split(":"))
							{
								if(cmd.equalsIgnoreCase("CustomName"))tag.setString("CustomName", m.CustomName());
								else if(cmd.equalsIgnoreCase("name"))tag.setString("name", m.name());
								else if(cmd.equalsIgnoreCase("GetFluidName"))tag.setString("GetFluidName", m.GetFluidName());
							}
							String name = msg.contents.getString("player");
							CoreMod.network_TileEntityNBT.sendTo(new NetWorkTileEntityNBT(msg.x, msg.y, msg.z, tag), (EntityPlayerMP) mcs.getEntityWorld().getPlayerEntityByName(name));
						}
						else if(t instanceof Machine_FluidStorageInfo)
						{
							Machine_FluidStorageInfo m =(Machine_FluidStorageInfo)t;
							NBTTagCompound tag = new NBTTagCompound();
							for(String cmd : cmds.split(":"))
							{
								if(cmd.equalsIgnoreCase("CustomName"))tag.setString("CustomName", m.CustomName());
								else if(cmd.equalsIgnoreCase("name"))tag.setString("name", m.name());
								else if(cmd.equalsIgnoreCase("GetSyntaxe"))tag.setString("GetSyntaxe", m.GetSyntaxe());
								else if(cmd.equalsIgnoreCase("GetFluidStoredInfo"))
								{
									String s = "";
									if(m.GetFluidStoredInfo()!= null)
									for(FluidTankInfo inf :m.GetFluidStoredInfo())
									{
										if(inf != null && inf.fluid != null)
										{
											int fid = inf.fluid.getFluidID();
											int capacity = inf.capacity;
											int amount = inf.fluid.amount;
											//System.out.println(fid+","+capacity+","+amount);
											s += fid+","+capacity+","+amount+"-";
										}
									}
									//s = s.substring(0, s.length()-1);
									tag.setString("GetFluidStoredInfo", s);
								}
							}
							String name = msg.contents.getString("player");
							CoreMod.network_TileEntityNBT.sendTo(new NetWorkTileEntityNBT(msg.x, msg.y, msg.z, tag), (EntityPlayerMP) mcs.getEntityWorld().getPlayerEntityByName(name));
						}
						else if(t instanceof Machine_FluidValve)
						{
							Machine_FluidValve m =(Machine_FluidValve)t;
							NBTTagCompound tag = new NBTTagCompound();
							for(String cmd : cmds.split(":"))
							{
								if(cmd.equalsIgnoreCase("CustomName"))tag.setString("CustomName", m.CustomName());
								else if(cmd.equalsIgnoreCase("name"))tag.setString("name", m.name());
								else if(cmd.equalsIgnoreCase("GetSyntaxe"))tag.setString("GetSyntaxe", m.GetSyntaxe());
								else if(cmd.equalsIgnoreCase("GetFluidName"))tag.setBoolean("ValveGetPosition", m.ValveGetPosition());
								else if(cmd.equalsIgnoreCase("ValveOpenClose1"))m.ValveOpenClose(true);
								else if(cmd.equalsIgnoreCase("ValveOpenClose0"))m.ValveOpenClose(false);
								
							}
							String name = msg.contents.getString("player");
							CoreMod.network_TileEntityNBT.sendTo(new NetWorkTileEntityNBT(msg.x, msg.y, msg.z, tag), (EntityPlayerMP) mcs.getEntityWorld().getPlayerEntityByName(name));
						}
						else if(t instanceof Machine_FluxMeter)
						{
							Machine_FluxMeter m =(Machine_FluxMeter)t;
							NBTTagCompound tag = new NBTTagCompound();
							//System.out.println(""+((Machine_FluxMeter)t).GetFlux());
							for(String cmd : cmds.split(":"))
							{
								if(cmd.equalsIgnoreCase("CustomName"))tag.setString("CustomName", m.CustomName());
								else if(cmd.equalsIgnoreCase("name"))tag.setString("name", m.name());
								else if(cmd.equalsIgnoreCase("GetSyntaxe"))tag.setString("GetSyntaxe", m.GetSyntaxe());
								else if(cmd.equalsIgnoreCase("GetFlux"))tag.setInteger("GetFlux", m.GetFlux());
								
							}
							//System.out.println(tag.toString());
							String name = msg.contents.getString("player");
							CoreMod.network_TileEntityNBT.sendTo(new NetWorkTileEntityNBT(msg.x, msg.y, msg.z, tag), (EntityPlayerMP) mcs.getEntityWorld().getPlayerEntityByName(name));
						}
						else if(t instanceof Machine_Switch)
						{
							Machine_Switch m =(Machine_Switch)t;
							NBTTagCompound tag = new NBTTagCompound();
							for(String cmd : cmds.split(":"))
							{
								if(cmd.equalsIgnoreCase("CustomName"))tag.setString("CustomName", m.CustomName());
								else if(cmd.equalsIgnoreCase("name"))tag.setString("name", m.name());
								else if(cmd.equalsIgnoreCase("GetSyntaxe"))tag.setString("GetSyntaxe", m.GetSyntaxe());
								else if(cmd.equalsIgnoreCase("SwitchGetPosition"))tag.setBoolean("SwitchGetPosition", m.SwitchGetPosition());
								else if(cmd.equalsIgnoreCase("SwitchONOFF1"))m.SwitchONOFF(true);
								else if(cmd.equalsIgnoreCase("SwitchONOFF0"))m.SwitchONOFF(false);
								
							}
							String name = msg.contents.getString("player");
							CoreMod.network_TileEntityNBT.sendTo(new NetWorkTileEntityNBT(msg.x, msg.y, msg.z, tag), (EntityPlayerMP) mcs.getEntityWorld().getPlayerEntityByName(name));
						}
					}
					else if(id == 6)//TODO
					{
						/*String name = msg.contents.getString("player");
						if(name != "")
						{
							NBTTagCompound internal_nbt = new NBTTagCompound();
							
							if(t instanceof TileEntitySwitch)
							{
								internal_nbt.setBoolean("pos", ((TileEntitySwitch)t).SwitchGetPosition());
							}
							else if(t instanceof TileEntityRedstoneSwitch)
							{
								internal_nbt.setBoolean("pos", ((TileEntityRedstoneSwitch)t).SwitchGetPosition());
							}
							
							CoreMod.network_TileEntityNBT.sendTo(new NetWorkTileEntityNBT(msg.x, msg.y, msg.z, internal_nbt), (EntityPlayerMP) mcs.getEntityWorld().getPlayerEntityByName(name));
						}*/
					}
					
					return null;
				}
				
				if(t instanceof TEServerUpdate)
				{
					t.readFromNBT(msg.contents);
					mcs.getEntityWorld().setTileEntity(msg.x, msg.y, msg.z, t);
				}
			}
			return null;
		}
		
	}
	
	public static class HandlerClient implements IMessageHandler<NetWorkTileEntityNBT, IMessage>
	{

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(NetWorkTileEntityNBT msg, MessageContext msgcontext)
		{
			if(Tools.isClient())
			{
				Minecraft mc = Minecraft.getMinecraft();
				//System.out.println(msg.contents.toString());
				//TODO
				//System.out.println(msg.contents.toString());
				if(msg.contents.hasKey("forTerminal") && mc.currentScreen instanceof GuiContainerTerminal)
				{
					GuiContainerTerminal term = (GuiContainerTerminal) mc.currentScreen;
					if(term.panel instanceof PanelMyInstallation)
					{
						PanelMyInstallation mi = (PanelMyInstallation) term.panel;
						mi.map.ReadNBT(msg.contents);
					}
					else if(term.panel instanceof PanelFluidMyInstallation)
					{
						PanelFluidMyInstallation mi = (PanelFluidMyInstallation) term.panel;
						mi.map.ReadNBT(msg.contents);
					}
				}
				/*else if (mc.currentScreen instanceof GuiContainerSwitch)//TODO
				{
					((GuiContainerSwitch)mc.currentScreen).te_switch.switch_position = msg.contents.getBoolean("pos");
				}*/
				/*else if (mc.currentScreen instanceof GuiContainerRedstoneSwitch)//TODO
				{
					((GuiContainerRedstoneSwitch)mc.currentScreen).te_switch.switch_position = msg.contents.getBoolean("pos");
				}*/
				else if(mc.theWorld.getTileEntity(msg.x, msg.y, msg.z) instanceof Machine_Counter)
				{
					Machine_Counter client_te = (Machine_Counter) mc.theWorld.getTileEntity(msg.x, msg.y, msg.z);
					if(msg.contents.hasKey("GetConsomation"))client_te.InfoFromServer(msg.contents.getLong("GetConsomation"));
					
				}
				else if(mc.theWorld.getTileEntity(msg.x, msg.y, msg.z) instanceof Machine_FluidFlowMeter)
				{
					Machine_FluidFlowMeter client_te = (Machine_FluidFlowMeter) mc.theWorld.getTileEntity(msg.x, msg.y, msg.z);
					if(msg.contents.hasKey("GetFlow"))client_te.InfoFromServer(msg.contents.getInteger("GetFlow"));
					
				}
				else if(mc.theWorld.getTileEntity(msg.x, msg.y, msg.z) instanceof Machine_FluidCounter)
				{
					Machine_FluidCounter client_te = (Machine_FluidCounter) mc.theWorld.getTileEntity(msg.x, msg.y, msg.z);
					if(msg.contents.hasKey("GetConsomation"))client_te.InfoFromServer(msg.contents.getLong("GetConsomation"));
					
				}
				else if(mc.theWorld.getTileEntity(msg.x, msg.y, msg.z) instanceof Machine_FluidName)
				{
					Machine_FluidName client_te = (Machine_FluidName) mc.theWorld.getTileEntity(msg.x, msg.y, msg.z);
					if(msg.contents.hasKey("GetFluidName"))client_te.InfoFromServer(msg.contents.getString("GetFluidName"));
					
				}
				else if(mc.theWorld.getTileEntity(msg.x, msg.y, msg.z) instanceof Machine_FluidStorageInfo)
				{
					Machine_FluidStorageInfo client_te = (Machine_FluidStorageInfo) mc.theWorld.getTileEntity(msg.x, msg.y, msg.z);
					if(msg.contents.hasKey("GetFluidStoredInfo"))client_te.InfoFromServer(msg.contents.getString("GetFluidStoredInfo"));
					
				}
				else if(mc.theWorld.getTileEntity(msg.x, msg.y, msg.z) instanceof Machine_FluidValve)
				{
					Machine_FluidValve client_te = (Machine_FluidValve) mc.theWorld.getTileEntity(msg.x, msg.y, msg.z);
					if(msg.contents.hasKey("ValveGetPosition"))client_te.InfoFromServer(msg.contents.getBoolean("ValveGetPosition"));
					
				}
				else if(mc.theWorld.getTileEntity(msg.x, msg.y, msg.z) instanceof Machine_FluxMeter)
				{
					Machine_FluxMeter client_te = (Machine_FluxMeter) mc.theWorld.getTileEntity(msg.x, msg.y, msg.z);
					if(msg.contents.hasKey("GetFlux"))client_te.InfoFromServer(msg.contents.getInteger("GetFlux"));
					
				}
				else if(mc.theWorld.getTileEntity(msg.x, msg.y, msg.z) instanceof Machine_StorageInfo)
				{
					Machine_StorageInfo client_te = (Machine_StorageInfo) mc.theWorld.getTileEntity(msg.x, msg.y, msg.z);
					client_te.InfoFromServer(msg.contents.hasKey("GetEnergyStored") ? msg.contents.getInteger("GetEnergyStored") : -1, msg.contents.hasKey("GetMaxEnergyStored") ? msg.contents.getInteger("GetMaxEnergyStored") : -1);
					
				}
				else if(mc.theWorld.getTileEntity(msg.x, msg.y, msg.z) instanceof Machine_Switch)
				{
					Machine_Switch client_te = (Machine_Switch) mc.theWorld.getTileEntity(msg.x, msg.y, msg.z);
					if(msg.contents.hasKey("SwitchGetPosition"))client_te.InfoFromServer(msg.contents.getBoolean("SwitchGetPosition"));
					
				}
				
			}
			return null;
		}
		
	}
}
