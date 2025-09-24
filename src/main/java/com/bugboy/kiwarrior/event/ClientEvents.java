package com.bugboy.kiwarrior.event;

import com.bugboy.kiwarrior.KiWarrior;
import com.bugboy.kiwarrior.client.gui.KiHudOverlay;
import com.bugboy.kiwarrior.commands.ModCommands;
import com.bugboy.kiwarrior.networking.ModMessages;
import com.bugboy.kiwarrior.networking.packets.C2SBlastSpawnEntityPacket;
import com.bugboy.kiwarrior.util.KeyBinding;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

public class ClientEvents {
    private static final Logger LOGGER = LogUtils.getLogger();
    @Mod.EventBusSubscriber(modid = KiWarrior.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents{
        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("ki", KiHudOverlay.HUD_KI);
        }

        @SubscribeEvent
        public static void registerKeys(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.INSTANCE.SHOOTING_KEY);
        }
    }

    @Mod.EventBusSubscriber(modid = KiWarrior.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ClientForgeBusEvents{
        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {
            Minecraft minecraft = Minecraft.getInstance();
            if (KeyBinding.INSTANCE.SHOOTING_KEY.consumeClick() && minecraft.player != null) {
                minecraft.player.displayClientMessage(Component.literal("Blast Fired!"), true);
                ModMessages.sendToServer(new C2SBlastSpawnEntityPacket(minecraft.player.blockPosition(), minecraft.player.getLookAngle()));
            }
        }
    }
}
