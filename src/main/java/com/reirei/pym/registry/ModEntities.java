package com.reirei.pym.registry;

import com.reirei.pym.Pym;
import com.reirei.pym.entity.PymDiscEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Pym.MOD_ID);

    public static final RegistryObject<EntityType<PymDiscEntity>> PYM_DISC = ENTITIES.register("pym_disc",
            () -> EntityType.Builder.<PymDiscEntity>of(PymDiscEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(8)
                    .updateInterval(10)
                    .build(Pym.MOD_ID + ":pym_disc"));

    private ModEntities() {}
    public static void register(IEventBus bus) { ENTITIES.register(bus); }
}
