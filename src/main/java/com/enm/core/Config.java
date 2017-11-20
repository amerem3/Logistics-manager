package com.enm.core;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.enm.guiutil.ResourceLocationRegister;
import com.enm.util.Tools;

import cpw.mods.fml.client.config.IConfigElement;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.common.config.Property.Type;

public class Config
{
	public static Configuration conf;
	public static boolean onPourcent;
	
	//public static int time_machine_update = 100;
	//public static int time_script_update = 100;
	
	
	public static void init(FMLPreInitializationEvent event)
	{
		conf = new Configuration(event.getSuggestedConfigurationFile());
		
		conf.getBoolean("Switch reverse color", "general", false, "");
		conf.getBoolean("FluxMetter info label is always visible", "general", false, "");
		conf.getBoolean("Counter info label is always visible", "general", false, "");
		conf.getBoolean("Switch info label is always visible", "general", false, "");
		conf.getBoolean("Storage Info info label is always visible", "general", false, "");
		conf.getBoolean("storage info on percent", "general", false, "");
		conf.getBoolean("Fluid Name info label is always visible", "general", false, "");
		
		conf.getString("FluxMetter info label Color", "general", "0xFFFF00", "");
		conf.getString("Counter info label Color", "general", "0xFFFF00", "");
		conf.getString("Switch info label Color", "general", "0xFFFF00", "");
		conf.getString("Storage Info info label Color", "general", "0xFFFF00", "");
		conf.getString("Text label Color", "general", "0xFFFF00", "");
		conf.getString("Fluid Name info label Color", "general", "0xFFFF00", "");
		
		conf.getInt("terminal gui scale", "general", 1, 1, 2, "");
		//conf.getInt("Calculator Machine UpdateTime", "general", 30000, 100, 300000, "time on millisecond");
		//conf.getInt("Calculator Script UpdateTime", "general", 200, 10, 60000, "time on millisecond");
		
		conf.load();
		onChangment();
		conf.save();
	}
	
	public static void onChangment()
	{
		onPourcent = conf.getBoolean("storage info on percent", "general", false, "");
		if(conf.getBoolean("Switch reverse color", "general", false, ""))
		{
			ResourceLocationRegister.texture_rfswitch_gui = new ResourceLocation(CoreMod.MODID ,"textures/guis/rfswitch_gui2.png");
			ResourceLocationRegister.texture_guitool2 = new ResourceLocation(CoreMod.MODID ,"textures/guis/guitool2.2.png");
			ResourceLocationRegister.texture_terminal = new ResourceLocation(CoreMod.MODID+":textures/blocks/models/terminal2.png");
			ResourceLocationRegister.texture_switchcontroler = new ResourceLocation(CoreMod.MODID+":textures/blocks/models/switchcontroler2.png");
			ResourceLocationRegister.texture_fluid_terminal = new ResourceLocation(CoreMod.MODID+":textures/blocks/models/fluidterminal2.png");
		}
		else
		{
			ResourceLocationRegister.texture_rfswitch_gui = new ResourceLocation(CoreMod.MODID ,"textures/guis/rfswitch_gui.png");
			ResourceLocationRegister.texture_guitool2 = new ResourceLocation(CoreMod.MODID ,"textures/guis/guitool2.png");
			ResourceLocationRegister.texture_terminal = new ResourceLocation(CoreMod.MODID+":textures/blocks/models/terminal.png");
			ResourceLocationRegister.texture_switchcontroler = new ResourceLocation(CoreMod.MODID+":textures/blocks/models/switchcontroler.png");
			ResourceLocationRegister.texture_fluid_terminal = new ResourceLocation(CoreMod.MODID+":textures/blocks/models/fluidterminal.png");
		}
		
		//time_machine_update = conf.getInt("Calculator Machine UpdateTime", "general", 30000, 100, 300000, "time on millisecond");
		//time_script_update = conf.getInt("Calculator Script UpdateTime", "general", 200, 10, 60000, "time on millisecond");
	}
}
