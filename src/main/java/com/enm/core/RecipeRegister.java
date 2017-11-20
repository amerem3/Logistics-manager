package com.enm.core;

import com.enm.register.RBlocks;
import com.enm.register.RItems;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import mekanism.api.ItemRetriever;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class RecipeRegister
{
	public static void Register()
	{
		GameRegistry.addRecipe(new ItemStack(RItems.Item_RFLogicMachineFrame, 1), "qiq","iri","qiq", 'q', Items.quartz, 'i', Items.iron_ingot, 'r', Blocks.redstone_block);
		
		GameRegistry.addRecipe(new ItemStack(RBlocks.Block_NetWorkCable, 8), "ppp","rlr","ppp", 'p', Items.paper, 'r', Items.redstone, 'l', RItems.Item_RFLogicMachineFrame);
		GameRegistry.addRecipe(new ItemStack(RBlocks.Block_RFTerminal, 1), "gdg","rlr","bbb", 'g', Items.gold_ingot, 'd', Items.diamond, 'r', Items.redstone, 'l', RItems.Item_RFLogicMachineFrame, 'b', Blocks.stone_button);
		GameRegistry.addRecipe(new ItemStack(RBlocks.Block_RFSwitch, 1), " s "," l "," r ", 's', Blocks.lever, 'l', RItems.Item_RFLogicMachineFrame, 'r', Items.redstone);
		GameRegistry.addRecipe(new ItemStack(RBlocks.Block_RFFluxMeter, 1), " s "," l "," r ", 's', Blocks.redstone_block, 'l', RItems.Item_RFLogicMachineFrame, 'r', Items.redstone);
		GameRegistry.addRecipe(new ItemStack(RBlocks.Block_RFCounter, 1), " s ","olo"," r ", 's', Blocks.redstone_block, 'l', RItems.Item_RFLogicMachineFrame, 'r', Items.redstone, 'o', Items.repeater);
		GameRegistry.addRecipe(new ItemStack(RBlocks.Block_RFStorageInfo, 1), " s ","glg"," r ", 's', Blocks.redstone_block, 'l', RItems.Item_RFLogicMachineFrame, 'r', Items.redstone, 'g', Items.gold_ingot);
		GameRegistry.addRecipe(new ItemStack(RBlocks.Block_CableFacade, 1), "ppp","pcp","ppp", 'p', Items.paper, 'c', RBlocks.Block_NetWorkCable);
		//GameRegistry.addRecipe(new ItemStack(RBlocks.Block_SwitchControler, 4), "l","b", 'l', Blocks.lever, 'b', RItems.Item_RFLogicMachineFrame);
		
		GameRegistry.addRecipe(new ItemStack(RItems.Item_FluidLogicMachineFrame, 1), " l ","lcl"," l ", 'c', RItems.Item_RFLogicMachineFrame, 'l', new ItemStack(Items.dye, 1, 4));
		
		GameRegistry.addRecipe(new ItemStack(RBlocks.Block_FluidTerminal, 1), "gdg","rlr","bbb", 'g', Items.gold_ingot, 'd', Items.diamond, 'r', Items.redstone, 'l', RItems.Item_FluidLogicMachineFrame, 'b', Blocks.stone_button);
		GameRegistry.addRecipe(new ItemStack(RBlocks.Block_FluidValve, 1), " s "," l "," r ", 's', Blocks.lever, 'l', RItems.Item_FluidLogicMachineFrame, 'r', Items.bucket);
		GameRegistry.addRecipe(new ItemStack(RBlocks.Block_FlowMeter, 1), " r "," l "," r ", 'l', RItems.Item_FluidLogicMachineFrame, 'r', Items.bucket);
		GameRegistry.addRecipe(new ItemStack(RBlocks.Block_FluidCounter, 1), " r ","olo"," r ", 'l', RItems.Item_FluidLogicMachineFrame, 'r', Items.bucket, 'o', Items.repeater);
		GameRegistry.addRecipe(new ItemStack(RBlocks.Block_FluidStorageInfo, 1), " r ","glg"," r ", 'l', RItems.Item_FluidLogicMachineFrame, 'r', Items.bucket, 'g', Items.gold_ingot);
		
		GameRegistry.addRecipe(new ItemStack(RBlocks.Block_Monitor, 1), "l","g", 'g', Blocks.glass_pane, 'l', RItems.Item_RFLogicMachineFrame);
		GameRegistry.addRecipe(new ItemStack(RBlocks.Block_FluidNameInfo, 1), "l","p", 'l', RBlocks.Block_FluidStorageInfo, 'p', Items.book);
		
		GameRegistry.addRecipe(new ItemStack(RBlocks.Block_RedstoneSwitch, 1), "rsr","rlr","rbr", 's', Blocks.lever, 'l', RItems.Item_RFLogicMachineFrame, 'r', Items.redstone, 'b', Items.repeater);
		GameRegistry.addRecipe(new ItemStack(RBlocks.Block_RedstoneOut, 1), "rdr","rlr","rbr", 'd', Items.diamond, 'l', RItems.Item_RFLogicMachineFrame, 'r', Items.redstone, 'b', Items.comparator);
		GameRegistry.addRecipe(new ItemStack(RItems.Item_linker, 1), "rd","sc","rd", 'd', Items.repeater, 'r', Items.redstone, 's', Items.sign, 'c', RBlocks.Block_NetWorkCable);
		
		//GameRegistry.addRecipe(new ItemStack(RBlocks.Block_RedstoneIO, 1), "rrr","rlr","rrr", 'r', Items.redstone, 'l', RItems.Item_RFLogicMachineFrame);
		if(Loader.isModLoaded("OpenComputers"))
		{
			GameRegistry.addRecipe(new ItemStack(RBlocks.Block_OCI, 1), "drd","qlq", "drd",'d', Items.diamond, 'r', Items.redstone, 'q', Blocks.quartz_block, 'l', RItems.Item_RFLogicMachineFrame);
		}
		
		if(Loader.isModLoaded("Mekanism"))
		{
			ItemStack osmium = new ItemStack(ItemRetriever.getItem("Ingot").getItem(), 1, 1);
			ItemStack enrichedAlloy = ItemRetriever.getItem("EnrichedAlloy");
			
			GameRegistry.addRecipe(new ItemStack(RItems.Item_GasLogicMachineFrame, 1), " l ","lcl"," l ", 'c', RItems.Item_RFLogicMachineFrame, 'l', osmium);
			GameRegistry.addRecipe(new ItemStack(RBlocks.Block_GasValve, 1), " s "," l "," r ", 's', Blocks.lever, 'l', RItems.Item_GasLogicMachineFrame, 'r', enrichedAlloy);
			GameRegistry.addRecipe(new ItemStack(RBlocks.Block_GasFlowMeter, 1), " r "," l "," r ", 'l', RItems.Item_GasLogicMachineFrame, 'r', enrichedAlloy);
			GameRegistry.addRecipe(new ItemStack(RBlocks.Block_GasFluidCounter, 1), " r ","olo"," r ", 'l', RItems.Item_GasLogicMachineFrame, 'r', enrichedAlloy, 'o', Items.repeater);
		}
	}
}
