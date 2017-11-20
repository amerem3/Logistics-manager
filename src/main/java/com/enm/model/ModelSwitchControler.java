package com.enm.model;

import com.enm.api.network.Machine_Switch;
import com.enm.tileEntity.TileEntitySwitchControler;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;

public class ModelSwitchControler extends ModelBase
{
  //fields
    ModelRenderer Shape1;
    ModelRenderer ON;
    ModelRenderer OFF;
  
  public ModelSwitchControler()
  {
    textureWidth = 16;
    textureHeight = 16;
    
      Shape1 = new ModelRenderer(this, 0, 0);
      Shape1.addBox(0F, 0F, 0F, 4, 6, 1);
      Shape1.setRotationPoint(-2F, 13F, 7F);
      Shape1.setTextureSize(64, 32);
      Shape1.mirror = true;
      setRotation(Shape1, 0F, 0F, 0F);
      ON = new ModelRenderer(this, 0, 8);
      ON.addBox(0F, 0F, 0F, 2, 1, 1);
      ON.setRotationPoint(-1F, 14F, 6.5F);
      ON.setTextureSize(64, 32);
      ON.mirror = true;
      setRotation(ON, 0F, 0F, 0F);
      OFF = new ModelRenderer(this, 0, 11);
      OFF.addBox(0F, 0F, 0F, 2, 1, 1);
      OFF.setRotationPoint(-1F, 16F, 6.5F);
      OFF.setTextureSize(64, 32);
      OFF.mirror = true;
      setRotation(OFF, 0F, 0F, 0F);
  }
  
  public void render(TileEntity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(null, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, null);
    Shape1.render(f5);
    if(entity instanceof TileEntitySwitchControler)
    {
    	TileEntity e = entity.getWorldObj().getTileEntity(((TileEntitySwitchControler)entity).ent.x,((TileEntitySwitchControler)entity).ent.y,((TileEntitySwitchControler)entity).ent.z);
    	if(e instanceof Machine_Switch)
    	{
    		if(((Machine_Switch)e).SwitchGetPosition())
    		{
    			OFF.render(f5);
    		}
    		else
    		{
    			ON.render(f5);
    		}
    	}
    }
  }
  
  public void render(float f5)
  {
    Shape1.render(f5);
    ON.render(f5);
    OFF.render(f5);
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
