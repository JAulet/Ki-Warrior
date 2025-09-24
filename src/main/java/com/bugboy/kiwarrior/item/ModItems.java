package com.bugboy.kiwarrior.item;

import com.bugboy.kiwarrior.KiWarrior;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, KiWarrior.MODID);

    public static final RegistryObject<Item> NUGGET_ITEM = ITEMS.register("chicken_nugget", () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .alwaysEat().nutrition(1).saturationMod(3f).build())));
    public static final RegistryObject<Item> COOKED_NUGGET_ITEM = ITEMS.register("cooked_chicken_nugget", () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .alwaysEat().nutrition(3).saturationMod(5f).build())));
}
