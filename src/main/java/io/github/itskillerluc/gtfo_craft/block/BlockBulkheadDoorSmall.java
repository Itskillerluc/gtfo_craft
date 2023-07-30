package io.github.itskillerluc.gtfo_craft.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.util.IStringSerializable;

public abstract class BlockBulkheadDoorSmall extends Block {
    public static final PropertyDirection DIRECTION = BlockHorizontal.FACING;
    public static final PropertyEnum<SlideSide> SLIDE_SIDE = PropertyEnum.create("slide_side", SlideSide.class);
    public static final PropertyBool POWERED = PropertyBool.create("powered");
    public static final PropertyBool LOCKED = PropertyBool.create("locked");
    public static final PropertyInteger WIDTH = PropertyInteger.create("width",0, 4);
    public static final PropertyInteger HEIGHT = PropertyInteger.create("height",0, 4);
    public static final PropertyEnum<Status> STATUS = PropertyEnum.create("status", Status.class);
    public static final PropertyInteger SLIDE_COUNT = PropertyInteger.create("slide_count", 0, 4);

    public BlockBulkheadDoorSmall(Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
    }

    public abstract int getWidth();

    enum SlideSide implements IStringSerializable {
        LEFT,
        RIGHT;

        @Override
        public String getName() {
            if (this == LEFT) {
                return "left";
            } else if (this == RIGHT) {
                return "right";
            } else {
                return null;
            }
        }
    }

    enum Status implements IStringSerializable {
        OPEN,
        OPENING,
        CLOSING,
        CLOSED;

        @Override
        public String getName() {
            switch (this) {
                case OPEN:
                    return "open";
                case OPENING:
                    return "opening";
                case CLOSING:
                    return "closing";
                case CLOSED:
                    return "closed";
            }
            return null;
        }
    }
}
