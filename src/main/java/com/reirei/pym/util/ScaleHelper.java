package com.reirei.pym.util;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import org.joml.Vector3f;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleTypes;

public final class ScaleHelper {
    private static final float[] TIERS = {0.125F, 0.25F, 0.5F, 1.0F, 2.0F, 4.0F, 8.0F};

    private ScaleHelper() {}

    public static void step(Entity target, boolean shrinking) {
        ScaleData data = ScaleTypes.BASE.getScaleData(target);
        if (data == null) return;

        float current = data.getTargetScale();
        int closest = 0;
        float bestDistance = Float.MAX_VALUE;
        for (int i = 0; i < TIERS.length; i++) {
            float distance = Math.abs(TIERS[i] - current);
            if (distance < bestDistance) {
                bestDistance = distance;
                closest = i;
            }
        }

        int next = shrinking ? Math.max(0, closest - 1) : Math.min(TIERS.length - 1, closest + 1);
        if (next == closest) return;

        data.setScaleTickDelay(12);
        data.setPersistence(true);
        data.setTargetScale(TIERS[next]);

        if (target.level() instanceof ServerLevel level) {
            Vector3f rgb = shrinking ? new Vector3f(1.0F, 0.08F, 0.08F) : new Vector3f(0.08F, 0.35F, 1.0F);
            DustParticleOptions dust = new DustParticleOptions(rgb, 1.1F);
            level.sendParticles(dust, target.getX(), target.getY(0.5D), target.getZ(), 28,
                    target.getBbWidth() * 0.5D, target.getBbHeight() * 0.35D, target.getBbWidth() * 0.5D, 0.02D);
        }
    }
}
