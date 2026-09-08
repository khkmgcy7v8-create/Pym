package com.reirei.pym.block;

import com.reirei.pym.block.entity.PymParticleGeneratorBlockEntity;
import com.reirei.pym.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class PymParticleGeneratorBlock extends BaseEntityBlock {
    public PymParticleGeneratorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PymParticleGeneratorBlockEntity(pos, state);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!(level.getBlockEntity(pos) instanceof PymParticleGeneratorBlockEntity generator)) {
            return InteractionResult.PASS;
        }

        if (!level.isClientSide && generator.fillDisc(player, hand)) {
            player.displayClientMessage(Component.literal(generator.isRedMode() ? "Filled a Red Pym Disc" : "Filled a Blue Pym Disc"), true);
            return InteractionResult.CONSUME;
        }

        if (player.isShiftKeyDown()) {
            if (!level.isClientSide) {
                generator.toggleMode();
                player.displayClientMessage(Component.literal(generator.isRedMode() ? "Pym Generator: RED / SHRINK" : "Pym Generator: BLUE / GROW"), true);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        if (!level.isClientSide) {
            player.displayClientMessage(Component.literal(
                    "Energy: " + generator.getEnergyStored() + "/" + PymParticleGeneratorBlockEntity.ENERGY_CAPACITY +
                    " FE | Red: " + generator.getRedParticlesMb() + " mB | Blue: " + generator.getBlueParticlesMb() + " mB"), true);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntities.PYM_PARTICLE_GENERATOR.get(), PymParticleGeneratorBlockEntity::tick);
    }
}
