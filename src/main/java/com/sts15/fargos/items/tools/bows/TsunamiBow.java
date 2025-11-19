package com.sts15.fargos.items.tools.bows;

import com.sts15.fargos.items.tools.BasicBow;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import static com.sts15.fargos.items.tools.bows.ArrowHelpers.copyArrowProps;
import static com.sts15.fargos.items.tools.bows.ArrowHelpers.makeArrowLikeBase;
import static com.sts15.fargos.items.tools.bows.BowTags.*;

public class TsunamiBow extends BasicBow {
    public TsunamiBow(float drawSpeed, float range, Rarity rarity, int durability) {
        super(drawSpeed, range, rarity, durability);
    }

    @Override
    public void onArrowFired(Level level, LivingEntity shooter, AbstractArrow baseArrow, float power) {
        if (level.isClientSide) return;

        BowTags.tagArrow(baseArrow, KIND_TSUNAMI);

        float[] yawOffsets = { -6f, 0f, +6f }; // three extras around the base arrow
        for (float off : yawOffsets) {
            AbstractArrow extra = makeArrowLikeBase(baseArrow, shooter);
            if (extra == null) continue;

            BowTags.tagArrow(extra, KIND_TSUNAMI);
            copyArrowProps(baseArrow, extra);

            // slight per-arrow nerf for balance
            extra.setBaseDamage(baseArrow.getBaseDamage() * 0.85);

            extra.setOwner(shooter);
            extra.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot() + off, 0.0F, power * 3.0F, 1.0F);
            ((ServerLevel) level).addFreshEntity(extra);
        }
    }
}
