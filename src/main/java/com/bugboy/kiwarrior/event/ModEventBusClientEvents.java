package com.bugboy.kiwarrior.event;

import com.bugboy.kiwarrior.KiWarrior;
import com.bugboy.kiwarrior.entitites.ModEntities;
import com.bugboy.kiwarrior.entitites.client.BigBlastModel;
import com.bugboy.kiwarrior.entitites.client.BigBlastRenderer;
import com.bugboy.kiwarrior.entitites.client.ModModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = KiWarrior.MODID,bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.BIG_BLAST_LAYER, BigBlastModel::createBodyLayer);
    }
}
