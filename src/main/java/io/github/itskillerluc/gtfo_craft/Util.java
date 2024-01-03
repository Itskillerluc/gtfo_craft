package io.github.itskillerluc.gtfo_craft;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class Util {
    public static BlockPos offsetPosTo(BlockPos towards, BlockPos pos, double offset) {
        Vec3d posVec = new Vec3d(pos.getX(), pos.getY(), pos.getZ());
        Vec3d towardsVec = new Vec3d(towards.getX(), towards.getY(), towards.getZ());

        Vec3d direction = posVec.subtract(towardsVec);
        double scalar = offset / direction.lengthVector();

        return new BlockPos(towardsVec.add(direction.scale(scalar)));
    }
}
