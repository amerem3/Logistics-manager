package com.enm.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTerminal extends ModelBase
{
	
	ModelRenderer box;
    ModelRenderer bar1;
    ModelRenderer keyboard;
    ModelRenderer bar2;
    ModelRenderer bar3;
  
  public ModelTerminal()
  {
    textureWidth = 128;
    textureHeight = 128;
    
      box = new ModelRenderer(this, 0, 0);
      box.addBox(0F, 0F, 0F, 16, 16, 10);
      box.setRotationPoint(-8F, 8F, -2F);
      box.setTextureSize(128, 128);
      box.mirror = true;
      setRotation(box, 0F, 0F, 0F);
      bar1 = new ModelRenderer(this, 0, 35);
      bar1.addBox(0F, 0F, 0F, 16, 1, 1);
      bar1.setRotationPoint(-8F, 8F, -3F);
      bar1.setTextureSize(128, 128);
      bar1.mirror = true;
      setRotation(bar1, 0F, 0F, 0F);
      keyboard = new ModelRenderer(this, 0, 27);
      keyboard.addBox(0F, 0F, 0F, 16, 1, 6);
      keyboard.setRotationPoint(-8F, 23F, -8F);
      keyboard.setTextureSize(128, 128);
      keyboard.mirror = true;
      setRotation(keyboard, 0F, 0F, 0F);
      bar2 = new ModelRenderer(this, 53, 0);
      bar2.addBox(0F, 0F, 0F, 1, 14, 1);
      bar2.setRotationPoint(-8F, 9F, -3F);
      bar2.setTextureSize(128, 128);
      bar2.mirror = true;
      setRotation(bar2, 0F, 0F, 0F);
      bar3 = new ModelRenderer(this, 53, 0);
      bar3.addBox(0F, 0F, 0F, 1, 14, 1);
      bar3.setRotationPoint(7F, 9F, -3F);
      bar3.setTextureSize(128, 128);
      bar3.mirror = true;
      setRotation(bar3, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    box.render(f5);
    bar1.render(f5);
    keyboard.render(f5);
    bar2.render(f5);
    bar3.render(f5);
  }
  
  public void render(float f5)
  {
    box.render(f5);
    bar1.render(f5);
    keyboard.render(f5);
    bar2.render(f5);
    bar3.render(f5);
  }
  
  public void setRotation(ModelRenderer model, float x, float y, float z)
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
