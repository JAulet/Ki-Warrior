package com.bugboy.kiwarrior.entitites;

import com.bugboy.kiwarrior.KiWarrior;
import com.bugboy.kiwarrior.entitites.custom.BigBlast;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.swing.*;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, KiWarrior.MODID);

    public static final RegistryObject<EntityType<BigBlast>> BIG_BLAST =
            ENTITY_TYPES.register("big_blast", () -> EntityType.Builder.<BigBlast>of(BigBlast::new, MobCategory.MISC)
                    .sized(2.5f,2.5f)
                    .build("big_blast"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
