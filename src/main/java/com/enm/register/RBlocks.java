package com.enm.register;

import com.enm.block.BlockCableFacade;
import com.enm.block.BlockControlPanel;
import com.enm.block.BlockCounter;
import com.enm.block.BlockFlowMeter;
import com.enm.block.BlockFluidCounter;
import com.enm.block.BlockFluidNameInfo;
import com.enm.block.BlockFluidStorageInfo;
import com.enm.block.BlockFluidTerminal;
import com.enm.block.BlockFluidValve;
import com.enm.block.BlockFluxMeter;
import com.enm.block.BlockGasCounter;
import com.enm.block.BlockGasFlowMeter;
import com.enm.block.BlockGasValve;
import com.enm.block.BlockMonitor;
import com.enm.block.BlockNetWorkCable;
import com.enm.block.BlockOCInterface;
import com.enm.block.BlockRFStorageInfo;
import com.enm.block.BlockRedstoneOut;
import com.enm.block.BlockRedstoneSwitch;
import com.enm.block.BlockSwitch;
import com.enm.block.BlockTerminal;
import com.enm.block.util.BlockENM;

import net.minecraft.block.material.Material;

public class RBlocks
{
	public static final BlockENM Block_NetWorkCable = new BlockNetWorkCable(Material.rock);
	public static final BlockENM Block_CableFacade = new BlockCableFacade(Material.rock);
	//public static final BlockENM Block_SwitchControler = new BlockSwitchControler(Material.iron);
	
	public static final BlockENM Block_RFTerminal = new BlockTerminal(Material.iron);
	public static final BlockENM Block_RFSwitch = new BlockSwitch(Material.iron);
	public static final BlockENM Block_RFFluxMeter = new BlockFluxMeter(Material.iron);
	public static final BlockENM Block_RFCounter = new BlockCounter(Material.iron);
	public static final BlockENM Block_RFStorageInfo = new BlockRFStorageInfo(Material.iron);
	
	public static final BlockENM Block_FluidTerminal = new BlockFluidTerminal(Material.iron);
	public static final BlockENM Block_FluidValve = new BlockFluidValve(Material.iron);
	public static final BlockENM Block_FlowMeter = new BlockFlowMeter(Material.iron);
	public static final BlockENM Block_FluidCounter = new BlockFluidCounter(Material.iron);
	public static final BlockENM Block_FluidStorageInfo = new BlockFluidStorageInfo(Material.iron);
	public static final BlockENM Block_FluidNameInfo = new BlockFluidNameInfo(Material.iron);
	
	public static final BlockENM Block_GasValve = new BlockGasValve(Material.iron);
	public static final BlockENM Block_GasFlowMeter = new BlockGasFlowMeter(Material.iron);
	public static final BlockENM Block_GasFluidCounter = new BlockGasCounter(Material.iron);
	
	public static final BlockENM Block_Monitor = new BlockMonitor(Material.iron);
	
	public static final BlockENM Block_OCI = new BlockOCInterface(Material.iron);
	
	public static final BlockENM Block_RedstoneOut = new BlockRedstoneOut(Material.iron);
	public static final BlockENM Block_RedstoneSwitch = new BlockRedstoneSwitch(Material.iron);
	//public static final BlockENM Block_ControlPanel = new BlockControlPanel(Material.iron);
	/* reactirve for Block_ControlPanel this:
	  	BlockControlPanel ln:116
		ItemControlComponent ln:49
		TileEntitySRControlPanel ln:70
	 */
	//public static final BlockENM Block_Testing = new BlockTesting();
}
