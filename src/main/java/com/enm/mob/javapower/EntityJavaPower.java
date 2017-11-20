package com.enm.mob.javapower;

import com.enm.core.CoreMod;
import com.enm.util.Tools;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;

public class EntityJavaPower
{
	public static void mainRegistry()
	{
		registerEntity();
	}
	
	public static void registerEntity()
	{
		
		createEntity(EntityJavaPowerMob.class, "Java_Power", 0xFF8000, 0xE9AFE);
		
		if(Tools.isClient())render();
		
		
	}
	
	@SideOnly(Side.CLIENT)
	private static void render()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityJavaPowerMob.class, new RenderJavaPowerMob(new ModelBiped(), 0));
	}
	
	public static void createEntity(Class entityClass, String entityName, int solidColor, int spotColor)
	{
		int randomId = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(entityClass, entityName, randomId);
		EntityRegistry.registerModEntity(entityClass, entityName, randomId, CoreMod.instance, 64, 1, true);
		EntityRegistry.addSpawn(entityClass, 2, 0, 1, EnumCreatureType.creature, BiomeGenBase.forest);
		
		createEgg(randomId, solidColor, spotColor);
		
	}
	
	private static void createEgg(int randomId, int solidColor, int spotColor)
	{
		EntityList.entityEggs.put(Integer.valueOf(randomId), new EntityList.EntityEggInfo(randomId, solidColor, spotColor));
	}
}
