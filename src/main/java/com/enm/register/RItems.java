package com.enm.register;

import com.enm.item.ItemControlComponent;
import com.enm.item.ItemDebugger;
import com.enm.item.ItemFluidLogicMachineFrame;
import com.enm.item.ItemGasLogicMachineFrame;
import com.enm.item.ItemLinker;
import com.enm.item.ItemRFLogicMachineFrame;
import com.enm.item.ItemScrewdriver;
import com.enm.item.util.ItemENM;

import net.minecraft.item.Item;

public class RItems
{
	public static final Item Item_RFLogicMachineFrame = new ItemRFLogicMachineFrame();
	public static final Item Item_FluidLogicMachineFrame = new ItemFluidLogicMachineFrame();
	public static final Item Item_GasLogicMachineFrame = new ItemGasLogicMachineFrame();
	public static final ItemENM Item_linker = new ItemLinker();
	//public static final ItemENM DEVItem_debugger = new ItemDebugger();
	//public static final Item Item_ControlComponent = new ItemControlComponent();
	//public static final ItemENM Item_Screwdriver = new ItemScrewdriver();
	//public static final Item Item_ScreenConfigurator = new ItemScreenConfigurator();
}
