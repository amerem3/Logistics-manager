package com.enm.guiutil;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class GuiButtonTerminal extends GuiButton
{
	GuiButtonTerminal.Icon _icon;
	public GuiButtonTerminal(int id, int x, int y, GuiButtonTerminal.Icon icon)
    {
        super(id, x, y, 40, 20, "");
        _icon = icon;
    }
	
    public void drawButton(Minecraft mc, int x, int y)
    {
        if (this.visible)
        {
            mc.getTextureManager().bindTexture(ResourceLocationRegister.texture_terminal_icon);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            boolean flag = x >= this.xPosition && y >= this.yPosition && x < this.xPosition + this.width && y < this.yPosition + this.height;
            int k = 0;

            if (flag)
            {
                k += 20;
            }

            this.drawTexturedModalRect(this.xPosition, this.yPosition, _icon.x, (_icon.y)+k, this.width, this.height);
        }
    }
    
    public enum Icon
    {
    	Back(200,0),
    	Exit(200,40),
    	Edit(200,80),
    	Save(200,120),
    	Load(200,160),
    	New(200,200),
    	Down(0,200),
    	Up(40,200),
    	Ok(80,200),
    	Next(120, 200),
    	Next2(160, 200);
    	
    	public int x, y;
    	Icon(int _x, int _y)
    	{
    		x = _x;
    		y = _y;
    	}
    }
}
