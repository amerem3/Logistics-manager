package com.enm.guiutil;

import com.enm.core.Config;
import com.enm.core.CoreMod;

import net.minecraft.util.ResourceLocation;

public class ResourceLocationRegister
{
	public static ResourceLocation texture_pixel255;
	public static ResourceLocation texture_rfswitch_gui;
	public static ResourceLocation texture_rsswitch_gui;
	public static ResourceLocation texture_fluxmetter_gui;
	//public static ResourceLocation texture_soldering_station_gui;
	public static ResourceLocation texture_guiside;
	//public static ResourceLocation texture_material_formater_gui;
	public static ResourceLocation texture_guitool1;
	public static ResourceLocation texture_guitool2;
	public static ResourceLocation texture_fluid_guitool2;
	public static ResourceLocation texture_switchcontroler;
	public static ResourceLocation texture_terminal;
	public static ResourceLocation texture_fluid_terminal;
	public static ResourceLocation texture_valve_gui;
	public static ResourceLocation texture_flowmeter_gui;
	public static ResourceLocation texture_gas_valve_gui;
	public static ResourceLocation texture_gas_flowmeter_gui;
	public static ResourceLocation texture_terminal_icon;
	public static ResourceLocation texture_number;
	
	public static void register()
	{
		texture_pixel255 = new ResourceLocation(CoreMod.MODID ,"textures/guis/pixel255.png");
		texture_rfswitch_gui = new ResourceLocation(CoreMod.MODID ,"textures/guis/rfswitch_gui.png");
		//texture_soldering_station_gui = new ResourceLocation(CoreMod.MODID ,"textures/guis/soldering_station_gui.png");
		texture_guiside = new ResourceLocation(CoreMod.MODID ,"textures/guis/guiside.png");
		//texture_material_formater_gui = new ResourceLocation(CoreMod.MODID ,"textures/guis/material_formater_gui.png");
		texture_guitool1 = new ResourceLocation(CoreMod.MODID ,"textures/guis/guitool1.png");
		texture_guitool2 = new ResourceLocation(CoreMod.MODID ,"textures/guis/guitool2.png");
		texture_fluxmetter_gui = new ResourceLocation(CoreMod.MODID , "textures/guis/fluxmetter_gui.png");
		texture_terminal = new ResourceLocation(CoreMod.MODID+":textures/blocks/models/terminal.png");
		texture_switchcontroler = new ResourceLocation(CoreMod.MODID+":textures/blocks/models/switchcontroler.png");
		texture_valve_gui = new ResourceLocation(CoreMod.MODID+":textures/guis/valve_gui.png");
		texture_flowmeter_gui = new ResourceLocation(CoreMod.MODID+":textures/guis/flowmeter_gui.png");
		texture_fluid_terminal = new ResourceLocation(CoreMod.MODID+":textures/blocks/models/fluidterminal.png");
		texture_fluid_guitool2 = new ResourceLocation(CoreMod.MODID ,"textures/guis/Fluidguitool2.png");
		texture_gas_valve_gui = new ResourceLocation(CoreMod.MODID+":textures/guis/gas_valve_gui.png");
		texture_gas_flowmeter_gui = new ResourceLocation(CoreMod.MODID+":textures/guis/gas_flowmeter_gui.png");
		texture_terminal_icon = new ResourceLocation(CoreMod.MODID+":textures/guis/terminal_icon.png");
		texture_number = new ResourceLocation(CoreMod.MODID+":textures/guis/number.png");
		texture_rsswitch_gui = new ResourceLocation(CoreMod.MODID ,"textures/guis/rsswitch_gui.png");
		Config.onChangment();
	}
}
