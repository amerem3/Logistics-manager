package com.enm.core;

import cpw.mods.fml.client.config.GuiConfig;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class GuiConfigMod extends GuiConfig
{
	public GuiConfigMod(GuiScreen parent)
	{
		super(parent, new ConfigElement(Config.conf.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), CoreMod.MODID, false, false, "ENM Config");
	}
	
	@Override
    public void initGui()
    {
        super.initGui();
    }

    
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        super.actionPerformed(button);
    }
    
    @Override
    public void onGuiClosed()
    {
    	Config.conf.save();
    	Config.onChangment();
    	super.onGuiClosed();
    }

}
