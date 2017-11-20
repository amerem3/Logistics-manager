package com.enm.model;

import com.enm.api.network.NetWork;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;

public class ModelNetWorkCable extends ModelBase
{
    ModelRenderer core;
    ModelRenderer left;
    ModelRenderer right;
    ModelRenderer front;
    ModelRenderer back;
    ModelRenderer down;
    ModelRenderer up;
  
  public ModelNetWorkCable()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      core = new ModelRenderer(this, 0, 0);
      core.addBox(0F, 0F, 0F, 4, 4, 4);
      core.setRotationPoint(-2F, 14F, -2F);
      core.setTextureSize(64, 32);
      core.mirror = true;
      setRotation(core, 0F, 0F, 0F);
      left = new ModelRenderer(this, 0, 18);
      left.addBox(0F, 0F, 0F, 6, 4, 4);
      left.setRotationPoint(2F, 14F, -2F);
      left.setTextureSize(64, 32);
      left.mirror = true;
      setRotation(left, 0F, 0F, 0F);
      right = new ModelRenderer(this, 0, 18);
      right.addBox(0F, 0F, 0F, 6, 4, 4);
      right.setRotationPoint(-8F, 14F, -2F);
      right.setTextureSize(64, 32);
      right.mirror = true;
      setRotation(right, 0F, 0F, 0F);
      front = new ModelRenderer(this, 16, 0);
      front.addBox(0F, 0F, 0F, 4, 4, 6);
      front.setRotationPoint(-2F, 14F, -8F);
      front.setTextureSize(64, 32);
      front.mirror = true;
      setRotation(front, 0F, 0F, 0F);
      back = new ModelRenderer(this, 16, 0);
      back.addBox(0F, 0F, 0F, 4, 4, 6);
      back.setRotationPoint(-2F, 14F, 2F);
      back.setTextureSize(64, 32);
      back.mirror = true;
      setRotation(back, 0F, 0F, 0F);
      down = new ModelRenderer(this, 0, 8);
      down.addBox(0F, 0F, 0F, 4, 6, 4);
      down.setRotationPoint(-2F, 18F, -2F);
      down.setTextureSize(64, 32);
      down.mirror = true;
      setRotation(down, 0.0F, 0F, 0F);
      up = new ModelRenderer(this, 0, 8);
      up.addBox(0F, 0F, 0F, 4, 6, 4);
      up.setRotationPoint(-2F, 8F, -2F);
      up.setTextureSize(64, 32);
      up.mirror = true;
      setRotation(up, 0F, 0F, 0F);
  }
  
  public void render(TileEntity t, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render((Entity)null, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, (Entity)null);
    core.render(f5);
    if(t.getWorldObj().getTileEntity(t.xCoord -1, t.yCoord, t.zCoord) instanceof NetWork)left.render(f5);
    if(t.getWorldObj().getTileEntity(t.xCoord +1, t.yCoord, t.zCoord) instanceof NetWork)right.render(f5);
    if(t.getWorldObj().getTileEntity(t.xCoord, t.yCoord, t.zCoord -1) instanceof NetWork)front.render(f5);
    if(t.getWorldObj().getTileEntity(t.xCoord, t.yCoord, t.zCoord +1) instanceof NetWork)back.render(f5);
    if(t.getWorldObj().getTileEntity(t.xCoord, t.yCoord -1, t.zCoord) instanceof NetWork)down.render(f5);
    if(t.getWorldObj().getTileEntity(t.xCoord, t.yCoord +1, t.zCoord) instanceof NetWork)up.render(f5);
  }
  
  public void render(float f5)
  {
    core.render(f5);
    down.render(f5);
    up.render(f5);
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
