package com.enm.guipanel;

import com.enm.guicontainer.GuiContainerTerminal;

import net.minecraft.client.gui.inventory.GuiContainer;

public interface GuiPanel
{
	public void init(GuiContainer gui);
	public void draw();
	public void eventMouse(int x,int y, int click);
	public void eventKeyBoard(char c, int cid);
	public void screenResized(int width, int height);
	public void update();
	public void panelClose();
}
