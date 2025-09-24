package com.bugboy.kiwarrior.item;

import com.bugboy.kiwarrior.KiWarrior;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = KiWarrior.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeModeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, KiWarrior.MODID);

    // Creates a creative tab with the id "examplemod:example_tab" for the example item, that is placed after the combat tab
    public static final RegistryObject<CreativeModeTab> KIWARRIOR_TAB = CREATIVE_MODE_TABS.register("kiwarrior_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("Ki Warrior"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ModItems.COOKED_NUGGET_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ModItems.NUGGET_ITEM.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
                output.accept(ModItems.COOKED_NUGGET_ITEM.get());
            }).build());



}
