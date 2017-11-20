package com.enm.item;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.enm.core.CoreMod;
import com.enm.core.ENMResource;
import com.enm.item.ItemRFLogicMachineFrame.CItemRender;
import com.enm.item.util.ItemIdentity;
import com.enm.model.ModelLogicMachineFrame;
import com.enm.register.RItems;
import com.enm.tileEntity.TileEntityControlPanel;
import com.enm.tileEntityutil.ISR;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;

public class ItemControlComponent extends Item implements ItemIdentity, ISR
{

	public ItemControlComponent()
	{
		setCreativeTab(ENMResource.ENMCreativeTab);
		setUnlocalizedName(ItemClassName());
	}
	@Override
	public String DisplayItemName(){return "Control Component";}
	@Override
	public String ItemClassName(){return getClass().getSimpleName();}
	
	@Override
	public void addInformation(ItemStack item, EntityPlayer player, List info, boolean bool)
	{
		/*if(item != null && item.getItem().equals(RItems.Item_ControlComponent))
		{
			int icon = item.stackTagCompound.getInteger("icon");
			info.add("§eshift + Right Click");
			info.add("§eto scroll items");
			info.add("icon id:"+icon);
		}*/
	}
	
	@Override
	public void onUpdate(ItemStack item, World world, Entity entity, int meta, boolean bool)
	{
		if(item != null)
		{
			if(item.stackTagCompound == null)
			{
				item.stackTagCompound = new NBTTagCompound();
			}
		}
		super.onUpdate(item, world, entity, meta, bool);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player)
	{
		if(item != null)
		{
			if(player.isSneaking())
			{
				int icon = item.stackTagCompound.getInteger("icon");
				if(icon < 14)++icon;else icon = 0;
				item.stackTagCompound.setInteger("icon", icon);
			}
		}
		return super.onItemRightClick(item, world, player);
	}
	
	@Override
	public boolean onItemUse(ItemStack item, EntityPlayer player, World world, int x, int y, int z, int side, float fx, float fy, float fz)
	{
		if(world.getTileEntity(x, y, z) instanceof TileEntityControlPanel)
		{
			TileEntityControlPanel cp = (TileEntityControlPanel) world.getTileEntity(x, y, z);
			if(cp.inputdir.ordinal() == side && item.stackTagCompound != null && !player.isSneaking())
			{
				int by = 1- (int)(fy*2);
				int bx = 0;
				int icon = item.stackTagCompound.getInteger("icon");
				
				if(side == 2)bx = 1-(int)(fx*2);
				if(side == 3)bx = (int)(fx*2);
				if(side == 4)bx = (int)(fz*2);
				if(side == 5)bx = 1-(int)(fz*2);
				
				cp.nbt_map[bx+(by*2)] = null;
				
				if(icon == 0)cp.map[bx+(by*2)] = 1;
				else if(icon == 1)cp.map[bx+(by*2)] = 2;
				else if(icon == 2)cp.map[bx+(by*2)] = 12;
				else if(icon == 3)cp.map[bx+(by*2)] = 3;
				else if(icon == 4)cp.map[bx+(by*2)] = 4;
				else if(icon == 5)cp.map[bx+(by*2)] = 5;
				else if(icon == 6)cp.map[bx+(by*2)] = 0;
				else if(icon == 7)cp.map[bx+(by*2)] = 13;
				else if(icon == 8)cp.map[bx+(by*2)] = 10;
				else if(icon == 9)cp.map[bx+(by*2)] = 11;
				else if(icon == 10)cp.map[bx+(by*2)] = 14;
				else if(icon == 11)cp.map[bx+(by*2)] = 15;
				else if(icon == 12)cp.map[bx+(by*2)] = 16;
				else if(icon == 13)cp.map[bx+(by*2)] = 17;
				else if(icon == 14)cp.map[bx+(by*2)] = 18;
				
			}
		}
		return false;
	}
	
	@Override
	public int getItemStackLimit()
	{
		return 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IItemRenderer ItemRender()
	{
		return new CItemRender();
	}
	
	public class CItemRender implements IItemRenderer
	{
		private final ModelControlComponent model;
	    ResourceLocation textures;
	    
		public CItemRender()
		{
			model = new ModelControlComponent();
			textures = new ResourceLocation(CoreMod.MODID+":textures/items/models/ControlComponent.png");
		}
		
		@Override public boolean handleRenderType(ItemStack item, ItemRenderType type){return true;}
		@Override public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper){return true;}
		
		@Override
		public void renderItem(ItemRenderType type, ItemStack item, Object... data)
		{
			if(item != null && item.stackTagCompound != null)
			textures = new ResourceLocation(CoreMod.MODID+":textures/items/models/ControlComponent"+item.stackTagCompound.getInteger("icon")+".png");
			
			 GL11.glPushMatrix();
			 if(type == ItemRenderType.EQUIPPED)
			 {
				 GL11.glTranslatef(0.6F, 0.6F, 0.7F);
				 GL11.glScalef(2f, 2f, 2f);
			 }
			 else
			 {
				 if(type == ItemRenderType.EQUIPPED_FIRST_PERSON)
				 {
					 GL11.glScalef(2f, 2f, 2f);
					 GL11.glTranslatef(1f, 0.3f, 0.2f);
					 GL11.glRotatef(310f, 0, 1, 0);
				 }
				 else GL11.glScalef(1.6f, 1.6f, 1.6f); 
			 }
			 GL11.glTranslatef(0, 1, 0);
			 Minecraft.getMinecraft().renderEngine.bindTexture(textures);
			 GL11.glRotatef(180F, 1.0F, 0F, 0F);
			 model.render(0.0625F);
			 /*if(Minecraft.getMinecraft().currentScreen != null)
			 {
				 Minecraft.getMinecraft().currentScreen.drawTexturedModalRect(0, 0, 0, 0, 16, 16);
			 }*/
			 GL11.glPopMatrix();
		}
	}
	
	public class ModelControlComponent extends ModelBase
	{
	  //fields
	    ModelRenderer Shape1;
	  
	  public ModelControlComponent()
	  {
	    textureWidth = 32;
	    textureHeight = 16;
	    
	      Shape1 = new ModelRenderer(this, 0, 0);
	      Shape1.addBox(0F, 0F, 0F, 10, 10, 1);
	      Shape1.setRotationPoint(-5F, 13F, -0.5F);
	      Shape1.setTextureSize(64, 64);
	      Shape1.mirror = true;
	      setRotation(Shape1, 0F, 0F, 0F);
	  }
	  
	  public void render(float f5)
	  {
	    Shape1.render(f5);
	  }
	  
	  private void setRotation(ModelRenderer model, float x, float y, float z)
	  {
	    model.rotateAngleX = x;
	    model.rotateAngleY = y;
	    model.rotateAngleZ = z;
	  }
	  
	  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e)
	  {
	    super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
	  }

	}

}
