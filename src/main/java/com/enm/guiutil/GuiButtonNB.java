package com.enm.guiutil;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class GuiButtonNB extends GuiButton
{
	int _p;
	public GuiButtonNB(int id, int x, int y, int p)
    {
        super(id, x, y, 40, 40, "");
        _p = p;
    }
	
    public void drawButton(Minecraft mc, int x, int y)
    {
        if (this.visible)
        {
            mc.getTextureManager().bindTexture(ResourceLocationRegister.texture_guitool1);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            boolean flag = x >= this.xPosition && y >= this.yPosition && x < this.xPosition + this.width && y < this.yPosition + this.height;
            int k = 0;

            if (flag)
            {
                k += 80;
            }

            this.drawTexturedModalRect(this.xPosition, this.yPosition, _p*40, k, this.width, this.height);
        }
    }

}
