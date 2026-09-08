package com.reirei.pym.registry;

import com.reirei.pym.Pym;
import com.reirei.pym.item.PymDiscItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Pym.MOD_ID);

    public static final RegistryObject<Item> EMPTY_PYM_DISC = ITEMS.register("empty_pym_disc", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> RED_PYM_DISC = ITEMS.register("red_pym_disc", () -> new PymDiscItem(true, new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> BLUE_PYM_DISC = ITEMS.register("blue_pym_disc", () -> new PymDiscItem(false, new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> PYM_PARTICLE_GENERATOR = ITEMS.register("pym_particle_generator", () -> new BlockItem(ModBlocks.PYM_PARTICLE_GENERATOR.get(), new Item.Properties()));

    private ModItems() {}
    public static void register(IEventBus bus) { ITEMS.register(bus); }
}
