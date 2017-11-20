package com.enm.core;

//import org.luaj.vm2.Globals;
//import org.luaj.vm2.lib.jse.JsePlatform;

import com.enm.guiutil.ResourceLocationRegister;
import com.enm.mob.javapower.EntityJavaPower;
import com.enm.network.NetWorkTileEntityNBT;
import com.enm.util.NEI;
import com.enm.util.Tools;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = CoreMod.MODID, version = CoreMod.VERSION, guiFactory= "com.enm.core.GuiFactoryMod")
public class CoreMod
{
    public static final String MODID = "networksmanager";
    public static final String VERSION = "11.0R";
    
    @Instance(MODID)
    public static CoreMod instance;
    
    //Network
    public static SimpleNetworkWrapper network_TileEntityNBT = NetworkRegistry.INSTANCE.newSimpleChannel("ENMNBT");
    public static ClassLoader classLoader = null;
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	classLoader = this.getClass().getClassLoader();
    	NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
    	new ENMResource();
    	
    	RecipeRegister.Register();
    	
    	if(Loader.isModLoaded("NotEnoughItems") && Tools.isClient())NEI.Load();
    	
    	if(Tools.isClient())
    	{
    		ResourceLocationRegister.register();
    	}
    	
    }
    
    @EventHandler
    public void preinit(FMLPreInitializationEvent event)
    {
    	//network_TileEntityNBT = NetworkRegistry.INSTANCE.newSimpleChannel(MODID+"NWTENBT");
    	network_TileEntityNBT.registerMessage(NetWorkTileEntityNBT.Handler.class, NetWorkTileEntityNBT.class, 0, Side.SERVER);
    	network_TileEntityNBT.registerMessage(NetWorkTileEntityNBT.HandlerClient.class, NetWorkTileEntityNBT.class, 1, Side.CLIENT);
    	
    	Config.init(event);
    	EntityJavaPower.mainRegistry();
    }

	@EventHandler
    public void load(FMLInitializationEvent event)
    {
    	FMLCommonHandler.instance().bus().register(new EventHandlerRegister());
    	MinecraftForge.EVENT_BUS.register(new EventHandlerRegister());
    	//Lua.load();
    }
}
