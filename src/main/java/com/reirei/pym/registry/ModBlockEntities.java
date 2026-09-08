package com.reirei.pym.registry;

import com.reirei.pym.Pym;
import com.reirei.pym.block.entity.PymParticleGeneratorBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Pym.MOD_ID);

    public static final RegistryObject<BlockEntityType<PymParticleGeneratorBlockEntity>> PYM_PARTICLE_GENERATOR = BLOCK_ENTITIES.register(
            "pym_particle_generator",
            () -> BlockEntityType.Builder.of(PymParticleGeneratorBlockEntity::new, ModBlocks.PYM_PARTICLE_GENERATOR.get()).build(null));

    private ModBlockEntities() {}
    public static void register(IEventBus bus) { BLOCK_ENTITIES.register(bus); }
}
