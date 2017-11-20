package com.enm.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import org.lwjgl.input.Mouse;

import com.enm.api.util.Vector2;
import com.enm.core.CoreMod;
import com.enm.guipanel.myinstallation.Map;
import com.enm.network.NetWorkTileEntityNBT;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class Tools
{
	public static boolean isClient()
	{
		return FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT;
	}
	
	public static boolean isServer()
	{
		return FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER;
	}
	
	public static ForgeDirection EntityLivingBaseToForgeDirection(EntityLivingBase player, boolean enableUpDownSide)
	{
		float pitch = 0;
		if(enableUpDownSide)pitch = player.rotationPitch;
		
		if(pitch > 60)
		{
			return ForgeDirection.UP;
		}
		else if(pitch < -60)
		{
			return ForgeDirection.DOWN;
		}
		else
		{
			switch(MathHelper.floor_double((double)((player.rotationYaw * 4F) / 360F) + 2.5D) & 3)
			{
				default: return ForgeDirection.UNKNOWN;
				case 0: return ForgeDirection.SOUTH;
				case 1: return ForgeDirection.WEST;
				case 2: return ForgeDirection.NORTH;
				case 3: return ForgeDirection.EAST;
			}
			
		}
	}
	
	public static int ForgeDirectionToIntegerSide(ForgeDirection in)
	{
		if(in == null)return -1;
		
		if(in == ForgeDirection.DOWN){return 0;}
		else if(in == ForgeDirection.EAST){return 5;}
		else if(in == ForgeDirection.NORTH){return 2;}
		else if(in == ForgeDirection.SOUTH){return 3;}
		else if(in == ForgeDirection.UP){return 1;}
		else if(in == ForgeDirection.WEST){return 4;}
		else{return 0;}
	}
	
	public static ForgeDirection IntegerSideToForgeDirection(int in)
	{
		if(in == 0){return ForgeDirection.DOWN;}
		else if(in == 5){return ForgeDirection.EAST;}
		else if(in == 2){return ForgeDirection.NORTH;}
		else if(in == 3){return ForgeDirection.SOUTH;}
		else if(in == 1){return ForgeDirection.UP;}
		else if(in == 4){return ForgeDirection.WEST;}
		else{return ForgeDirection.UNKNOWN;}
	}
	
	public static TileEntity GetTileEntityOnServer(int x, int y, int z)
	{
		World w = GetWolrdOnServer();
		if(w != null) return w.getTileEntity(x, y, z);
		return null;
	}
	
	
	public static World GetWolrdOnServer()
	{
		if(isClient())
			{
				if(Minecraft.getMinecraft().isSingleplayer())return Minecraft.getMinecraft().getIntegratedServer().getEntityWorld();
			}
		if(isServer()) return MinecraftServer.getServer().getEntityWorld();
			return null;
	}
	
	public static EntityPlayer GetPlayerOnServer(String name)
	{
		if(isClient()) return Minecraft.getMinecraft().getIntegratedServer().getEntityWorld().getPlayerEntityByName(name);
		if(isServer()) return MinecraftServer.getServer().getEntityWorld().getPlayerEntityByName(name);
			return null;
	}
	
	public static void GetMouseLocalisationNoMC(Vector2 mouspos, int vect)
	{
		mouspos.x = Mouse.getX()/vect;
		mouspos.y = (Minecraft.getMinecraft().displayHeight-Mouse.getY())/vect;
	}
	
	public static void GetMouseLocalisation(Vector2 mouspos, int width, int height, int xSize, int ySize)
	{
		int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        
        float guiMPX = (float)Minecraft.getMinecraft().displayWidth/ (float)width;
        float guiMPY = (float)Minecraft.getMinecraft().displayHeight/ (float)height;
        float guiMP = 0;
        if(guiMPX < guiMPY)guiMP = guiMPX;
        else guiMP = guiMPY;
        
        mouspos.x = (int) ((Mouse.getX()-x*guiMP)/guiMP);
        mouspos.y = (int) ((Minecraft.getMinecraft().displayHeight-Mouse.getY()-y*guiMP)/guiMP);
	}
	
	public static int GetIdFromST(String[] list, String string)
	{
		int id = 0;
		for(String s : list)
		{
			if(s == string) return id;
			++id;
		}
		return -1;
	}
	
	public static int HTMLColorStringToInt(String HtmlColor)
	{
		return Integer.parseInt(HtmlColor.split("[x|X]")[1], 16);
	}
	
	public static void RequestClientMapInfo(int x, int y, int z, EntityPlayer pl)
	{
		NBTTagCompound contents = new NBTTagCompound();
		contents.setInteger("bk01", 1);
		contents.setString("player", pl.getDisplayName());
		CoreMod.network_TileEntityNBT.sendToServer(new NetWorkTileEntityNBT(x, y, z, contents));
	}
	
	public static void RequestMapExist(int x, int y, int z, EntityPlayer pl)
	{
		NBTTagCompound contents = new NBTTagCompound();
		contents.setInteger("bk01", 10);
		contents.setString("player", pl.getDisplayName());
		contents.setInteger("worldId", pl.worldObj.provider.dimensionId);
		CoreMod.network_TileEntityNBT.sendToServer(new NetWorkTileEntityNBT(x, y, z, contents));
	}
	
	public static void SendClientMapInfo(int x, int y, int z, Map map)
	{
		NBTTagCompound contents = new NBTTagCompound();
		map.SaveToNBT(contents);
		contents.setInteger("bk01", 2);
		CoreMod.network_TileEntityNBT.sendToServer(new NetWorkTileEntityNBT(x, y, z, contents));
	}
	
	public static void RequestClientCmd(int x, int y, int z,String ... o)
	{
		String out = "";
		for(String s : o)
		{
			out += s+":";
		}
		out = out.substring(0, out.length()-1);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("bk01", 3);
		tag.setString("cmd", out);
		tag.setString("player", Minecraft.getMinecraft().thePlayer.getDisplayName());
		CoreMod.network_TileEntityNBT.sendToServer(new NetWorkTileEntityNBT(x,y,z, tag));
	}
	
	public static void downloadFile(String filename, String urlString)
	{
		try
		{
			saveUrl(filename, urlString);
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private static void saveUrl(String filename, String urlString) throws MalformedURLException, IOException
	{
	    BufferedInputStream in = null;
	    FileOutputStream fout = null;
	    try
	    {
	        in = new BufferedInputStream(new URL(urlString).openStream());
	        fout = new FileOutputStream(filename);

	        final byte data[] = new byte[1024];
	        int count;
	        while ((count = in.read(data, 0, 1024)) != -1)
	        {
	            fout.write(data, 0, count);
	        }
	    }
	    finally
	    {
	        if (in != null)
	        {
	            in.close();
	        }
	        if (fout != null)
	        {
	            fout.close();
	        }
	    }
	}
	
	public static void loadJar(File jar)
	{
		try
		{
			priv_loadJar(jar);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private static void priv_loadJar(File jar) throws IOException
	{
		JarFile jf = new JarFile(jar);

        List<URL> urls = new ArrayList<URL>(5);
        urls.add(jar.toURI().toURL());
        Manifest mf = jf.getManifest();
        if (mf != null)
        {
            String cp = mf.getMainAttributes().getValue("class-path");
            if (cp != null)
            {
                for (String cpe : cp.split("\\s+"))
                {
                    File lib = new File(jar.getParentFile(), cpe);
                    urls.add(lib.toURI().toURL());
                }
            }
        }
        ClassLoader cl = new URLClassLoader(urls.toArray(new URL[urls.size()]), ClassLoader.getSystemClassLoader());
        
	}
	
	@SideOnly(Side.CLIENT)
	public static boolean ItemHeldIsEquals(Minecraft mc, Item i)
	{
		if(mc != null && mc.thePlayer != null)
		{
			if(i == null && mc.thePlayer.getHeldItem() == null)return true;
			if(mc.thePlayer.getHeldItem() != null)
				return mc.thePlayer.getHeldItem().getItem() == i;
		}
		return false;
	}
	
	public static ForgeDirection DirBlockAt(ForgeDirection orientation, Direction direction)
	{
		ForgeDirection ofset = ForgeDirection.UNKNOWN;
		if(orientation == ForgeDirection.EAST)
		{
			if(direction == Direction.LEFT) ofset = ForgeDirection.NORTH;
			else if(direction == Direction.RIGHT) ofset = ForgeDirection.SOUTH;
			else if(direction == Direction.UP) ofset = ForgeDirection.UP;
			else if(direction == Direction.DOWN) ofset = ForgeDirection.DOWN;
		}
		else if(orientation == ForgeDirection.NORTH)
		{
			if(direction == Direction.LEFT) ofset = ForgeDirection.WEST;
			else if(direction == Direction.RIGHT) ofset = ForgeDirection.EAST;
			else if(direction == Direction.UP) ofset = ForgeDirection.UP;
			else if(direction == Direction.DOWN) ofset = ForgeDirection.DOWN;
		}
		else if(orientation == ForgeDirection.WEST)
		{
			if(direction == Direction.LEFT) ofset = ForgeDirection.SOUTH;
			else if(direction == Direction.RIGHT) ofset = ForgeDirection.NORTH;
			else if(direction == Direction.UP) ofset = ForgeDirection.UP;
			else if(direction == Direction.DOWN) ofset = ForgeDirection.DOWN;
		}
		else if(orientation == ForgeDirection.SOUTH)
		{
			if(direction == Direction.LEFT) ofset = ForgeDirection.EAST;
			else if(direction == Direction.RIGHT) ofset = ForgeDirection.WEST;
			else if(direction == Direction.UP) ofset = ForgeDirection.UP;
			else if(direction == Direction.DOWN) ofset = ForgeDirection.DOWN;
		}
		return ofset;
	}
	
	public static Block GetBlockAt(World w, int x, int y, int z, ForgeDirection orientation, Direction direction)
	{
		ForgeDirection dir = DirBlockAt(orientation, direction);
		return w.getBlock(x+dir.offsetX, y+dir.offsetY, z+dir.offsetZ);
	}
	
	public static TileEntity GetTileEntityAt(World w, int x, int y, int z, ForgeDirection orientation, Direction direction)
	{
		ForgeDirection dir = DirBlockAt(orientation, direction);
		return w.getTileEntity(x+dir.offsetX, y+dir.offsetY, z+dir.offsetZ);
	}
}
