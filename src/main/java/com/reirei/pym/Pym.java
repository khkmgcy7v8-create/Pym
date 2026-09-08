package com.reirei.pym;

import com.reirei.pym.registry.ModBlockEntities;
import com.reirei.pym.registry.ModBlocks;
import com.reirei.pym.registry.ModEntities;
import com.reirei.pym.registry.ModItems;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Pym.MOD_ID)
public final class Pym {
    public static final String MOD_ID = "pym";

    public Pym() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.register(bus);
        ModBlocks.register(bus);
        ModBlockEntities.register(bus);
        ModEntities.register(bus);
    }
}
