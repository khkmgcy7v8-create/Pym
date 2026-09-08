package com.reirei.pym.block.entity;

import com.reirei.pym.registry.ModBlockEntities;
import com.reirei.pym.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PymParticleGeneratorBlockEntity extends BlockEntity {
    public static final int ENERGY_CAPACITY = 10_000_000;
    public static final int TANK_CAPACITY_MB = 50_000;
    public static final int ENERGY_PER_MB = 1_000;
    public static final int DISC_COST_MB = 250;

    private final EnergyStorage energy = new EnergyStorage(ENERGY_CAPACITY, 100_000, 0);
    private LazyOptional<EnergyStorage> energyOptional = LazyOptional.of(() -> energy);

    private int redParticlesMb;
    private int blueParticlesMb;
    private boolean redMode = true;

    public PymParticleGeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PYM_PARTICLE_GENERATOR.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, PymParticleGeneratorBlockEntity be) {
        if (level.isClientSide) return;
        int stored = be.redMode ? be.redParticlesMb : be.blueParticlesMb;
        if (stored >= TANK_CAPACITY_MB) return;
        if (be.energy.extractEnergy(ENERGY_PER_MB, true) < ENERGY_PER_MB) return;

        be.energy.extractEnergy(ENERGY_PER_MB, false);
        if (be.redMode) be.redParticlesMb++;
        else be.blueParticlesMb++;
        be.setChanged();
    }

    public boolean fillDisc(Player player, InteractionHand hand) {
        ItemStack held = player.getItemInHand(hand);
        if (!held.is(ModItems.EMPTY_PYM_DISC.get())) return false;

        int available = redMode ? redParticlesMb : blueParticlesMb;
        if (available < DISC_COST_MB) return false;

        if (redMode) redParticlesMb -= DISC_COST_MB;
        else blueParticlesMb -= DISC_COST_MB;

        if (!player.getAbilities().instabuild) held.shrink(1);
        ItemStack filled = new ItemStack(redMode ? ModItems.RED_PYM_DISC.get() : ModItems.BLUE_PYM_DISC.get());
        if (!player.getInventory().add(filled)) player.drop(filled, false);
        setChanged();
        return true;
    }

    public void toggleMode() {
        redMode = !redMode;
        setChanged();
    }

    public boolean isRedMode() { return redMode; }
    public int getRedParticlesMb() { return redParticlesMb; }
    public int getBlueParticlesMb() { return blueParticlesMb; }
    public int getEnergyStored() { return energy.getEnergyStored(); }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("Energy", energy.getEnergyStored());
        tag.putInt("RedParticles", redParticlesMb);
        tag.putInt("BlueParticles", blueParticlesMb);
        tag.putBoolean("RedMode", redMode);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        energy.receiveEnergy(tag.getInt("Energy"), false);
        redParticlesMb = tag.getInt("RedParticles");
        blueParticlesMb = tag.getInt("BlueParticles");
        redMode = tag.getBoolean("RedMode");
    }

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY) return energyOptional.cast();
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        energyOptional.invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        energyOptional = LazyOptional.of(() -> energy);
    }
}
