package com.reirei.pym.item;

import com.reirei.pym.entity.PymDiscEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PymDiscItem extends Item {
    private final boolean shrinking;

    public PymDiscItem(boolean shrinking, Properties properties) {
        super(properties);
        this.shrinking = shrinking;
    }

    public boolean isShrinking() {
        return shrinking;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 1.3F);
        if (!level.isClientSide) {
            PymDiscEntity disc = new PymDiscEntity(level, player, shrinking);
            disc.setItem(stack);
            disc.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.6F, 0.5F);
            level.addFreshEntity(disc);
        }
        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.getAbilities().instabuild) stack.shrink(1);
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }
}
