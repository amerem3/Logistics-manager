package com.enm.item;

import java.util.List;

import org.lwjgl.input.Keyboard;

import com.enm.api.network.MachineNetWork;
import com.enm.api.network.NetWorkUtil;
import com.enm.api.util.ILinker;
import com.enm.api.util.Vector3;
import com.enm.core.CoreMod;
import com.enm.item.util.ItemENM;
import com.enm.tileEntity.TileEntityMonitor;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public class ItemLinker extends ItemENM
{
	public ItemLinker()
	{
		super();
		setTextureName(CoreMod.MODID+":linker");
		setMaxStackSize(1);
	}

	@Override
	public String DisplayItemName()
	{
		return "Linker";
	}

	@Override
	public String ItemClassName()
	{
		return getClass().getSimpleName();
	}
	
	@Override
	public void addInformation(ItemStack item, EntityPlayer player, List info, boolean bol)
	{
		if(item != null && item.stackTagCompound != null && item.stackTagCompound.hasKey("posX"))
		{
			Vector3 pos = new Vector3();
			pos.ReadFromNBT(item.stackTagCompound, "pos");
			info.add("linked at: "+pos.GetOutPutConsole());
			info.add("is: "+item.stackTagCompound.getString("name"));
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)||Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
		{
			info.add("use item on receptor (Switch, FlowMeter ...) to save config in the linker.");
			info.add("and use this item on controller (Monitor ...) to apply configuration.");
		}
		else
		{
			info.add("[press shift to prompt more info]");
		}
	}
	
	public void onUpdate(ItemStack item, World world, Entity entity, int meta, boolean bool)
	{
		if(item != null)
		{
			if(item.stackTagCompound == null)
			{
				item.stackTagCompound = new NBTTagCompound();
			}
		}
		super.onUpdate(item, world, entity, meta, bool);
	}
	
	public boolean onItemUse(ItemStack item, EntityPlayer player, World world, int x, int y, int z, int side, float fx, float fy, float fz)
	{
		TileEntity te = world.getTileEntity(x, y, z);
		if(item != null && te instanceof MachineNetWork)
		{
			Class cl = NetWorkUtil.GetType(te);
			if(cl != null)
			{
				player.addChatMessage(new ChatComponentText("config saved: "+cl.getSimpleName()));
				Vector3 posm = new Vector3(x,y,z);
				posm.SaveToNBT(item.stackTagCompound, "pos");
				item.stackTagCompound.setString("name", NetWorkUtil.GetType(te).getSimpleName());
			}
			else
			{
				if(!item.stackTagCompound.hasKey("posX"))return false;
				
				if(te instanceof TileEntityMonitor)
				{
					Vector3 posm = new Vector3();
					posm.ReadFromNBT(item.stackTagCompound, "pos");
					
					((TileEntityMonitor)te).pos = posm;
					((TileEntityMonitor)te).updateInventory();
					
					player.addChatMessage(new ChatComponentText("config applied"));
				}
				else if(te instanceof ILinker)
				{
					Vector3 posm = new Vector3();
					posm.ReadFromNBT(item.stackTagCompound, "pos");
					boolean b = ((ILinker)te).useLinker(player.getDisplayName(), world, x, y, z, side, fx, fy, fz, item.stackTagCompound.getString("name"), posm);
					if(b)
					{
						player.addChatMessage(new ChatComponentText("config applied"));
					}
					return b;
				}
			}
		}
		return true;
	}

}
