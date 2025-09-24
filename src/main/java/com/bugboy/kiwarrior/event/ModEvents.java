package com.bugboy.kiwarrior.event;

import com.bugboy.kiwarrior.KiWarrior;
import com.bugboy.kiwarrior.commands.ModCommands;
import com.bugboy.kiwarrior.item.ModItems;
import com.bugboy.kiwarrior.ki.PlayerKi;
import com.bugboy.kiwarrior.ki.PlayerKiProvider;
import com.bugboy.kiwarrior.networking.ModMessages;
import com.bugboy.kiwarrior.networking.packets.KiS2CSyncPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

public class ModEvents {
    @Mod.EventBusSubscriber(modid = KiWarrior.MODID)
    public static class ForgeEvents {
        @SubscribeEvent
        public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof Player) {
                if (!event.getObject().getCapability(PlayerKiProvider.PLAYER_KI).isPresent()) {
                    event.addCapability(new ResourceLocation(KiWarrior.MODID, "properties"), new PlayerKiProvider());
                }
            }
        }

        @SubscribeEvent
        public static void onPlayerCloned(PlayerEvent.Clone event) {
            if (event.isWasDeath()) {
                if(event.getOriginal() instanceof ServerPlayer playerOld && event.getEntity() instanceof ServerPlayer playerNew)
                {
                    playerOld.reviveCaps();
                    playerOld.getCapability(PlayerKiProvider.PLAYER_KI).ifPresent(oldStore -> {
                        event.getEntity().sendSystemMessage(Component.literal("Previous ki:" + oldStore.getKi() + "Previous Max Ki" + oldStore.getMaxKi()));
                        playerNew.getCapability(PlayerKiProvider.PLAYER_KI).ifPresent(newStore -> {
                            newStore.copyFrom(oldStore);
                        });
                    });
                    playerOld.invalidateCaps();
                }
            }
        }

        @SubscribeEvent
        public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
            event.register(PlayerKi.class);
        }

        @SubscribeEvent
        public static void onLivingHurt(LivingHurtEvent event) {
            if (event.getSource().getEntity() instanceof Player player) {
                if (player.getMainHandItem() == ItemStack.EMPTY) {
                    player.getCapability(PlayerKiProvider.PLAYER_KI).ifPresent(ki ->
                            event.setAmount(event.getAmount() + ki.getKi())
                    );
                }
                player.sendSystemMessage(Component.literal(player.getName().getString() + " punched " + event.getEntity().getName().getString() + " for " + event.getAmount()));
            }
        }

        @SubscribeEvent
        public static void onEat(LivingEntityUseItemEvent.Finish event) {
            if (event.getEntity() instanceof ServerPlayer player) {
                if (ModItems.COOKED_NUGGET_ITEM.get() == event.getItem().getItem()) {
                    player.getCapability(PlayerKiProvider.PLAYER_KI).ifPresent(ki -> {
                            ki.addMaxKi(1);
                            ki.setKiRegen(ki.getMaxKi()/20);
                    });
                }
                if (ModItems.NUGGET_ITEM.get() == event.getItem().getItem()) {
                    player.getCapability(PlayerKiProvider.PLAYER_KI).ifPresent(ki -> {
                            ki.subMaxKi(1);
                    });
                }
                player.getCapability(PlayerKiProvider.PLAYER_KI).ifPresent(ki -> {
                            player.sendSystemMessage(Component.literal(player.getName().getString() + "'s max ki is now " + ki.getMaxKi()));
                            ModMessages.sendToPlayer(new KiS2CSyncPacket(ki.getKi(), ki.getMaxKi()), player);
                        }
                );
            }
        }

        @SubscribeEvent
        static void onPlayerTick(TickEvent.PlayerTickEvent event){
            int regenMod = 0;

            final int regen;
            if(event.side == LogicalSide.SERVER){
                if(event.player.getAbilities().flying && !event.player.isCreative()) { regenMod -= 20; }
                event.player.getCapability(PlayerKiProvider.PLAYER_KI).ifPresent(ki -> {
                            event.player.getAbilities().mayfly = (ki.getKi() > 10);
                            event.player.onUpdateAbilities();
                            if(!event.player.getAbilities().mayfly && !event.player.isCreative()) {
                                event.player.getAbilities().flying = false;
                            }
                        }
                );
                regen = regenMod;
                if(event.player instanceof ServerPlayer player) {
                    event.player.getCapability(PlayerKiProvider.PLAYER_KI).ifPresent(ki -> {
                        if (event.player.tickCount % 20 == 0) {
                            ki.addKi(ki.getKiRegen() + regen);
                            ModMessages.sendToPlayer(new KiS2CSyncPacket(ki.getKi(), ki.getMaxKi()), player);
                        }
                    });
                }
            }
        }


        @SubscribeEvent
        public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
            if(!event.getLevel().isClientSide) {
                if(event.getEntity() instanceof ServerPlayer player){
                    player.getCapability(PlayerKiProvider.PLAYER_KI).ifPresent(ki -> {
                        ModMessages.sendToPlayer(new KiS2CSyncPacket(ki.getKi(), ki.getMaxKi()), player);
                    });
                }
            }
        }
    }
}
