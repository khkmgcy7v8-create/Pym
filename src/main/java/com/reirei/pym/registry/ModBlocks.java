package com.reirei.pym.registry;

import com.reirei.pym.Pym;
import com.reirei.pym.block.PymParticleGeneratorBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Pym.MOD_ID);

    public static final RegistryObject<Block> PYM_PARTICLE_GENERATOR = BLOCKS.register("pym_particle_generator",
            () -> new PymParticleGeneratorBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(8.0F, 1200.0F).requiresCorrectToolForDrops()));

    private ModBlocks() {}
    public static void register(IEventBus bus) { BLOCKS.register(bus); }
}
