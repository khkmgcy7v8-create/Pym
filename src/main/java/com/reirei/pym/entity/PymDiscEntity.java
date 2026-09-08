package com.reirei.pym.entity;

import com.reirei.pym.item.PymDiscItem;
import com.reirei.pym.registry.ModEntities;
import com.reirei.pym.registry.ModItems;
import com.reirei.pym.util.ScaleHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class PymDiscEntity extends ThrowableItemProjectile {
    public PymDiscEntity(EntityType<? extends PymDiscEntity> type, Level level) {
        super(type, level);
    }

    public PymDiscEntity(Level level, LivingEntity owner, boolean shrinking) {
        super(ModEntities.PYM_DISC.get(), owner, level);
        setItem(new ItemStack(shrinking ? ModItems.RED_PYM_DISC.get() : ModItems.BLUE_PYM_DISC.get()));
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.EMPTY_PYM_DISC.get();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!level().isClientSide) {
            ItemStack stack = getItem();
            if (stack.getItem() instanceof PymDiscItem disc) {
                ScaleHelper.step(result.getEntity(), disc.isShrinking());
            }
            discard();
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!level().isClientSide && result.getType() != HitResult.Type.ENTITY) {
            discard();
        }
    }
}
