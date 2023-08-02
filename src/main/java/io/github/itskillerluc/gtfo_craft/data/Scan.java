package io.github.itskillerluc.gtfo_craft.data;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;

public class Scan {
    private final int time;
    private final @Nullable BlockPos linkedPos;
    private final BlockPos pos1;
    private final BlockPos pos2;
    private final int color;
    private int timer;
    private final String command;
    private final int playersNeeded;

    public Scan(int time, @Nullable BlockPos linkedPos, BlockPos pos1, BlockPos pos2, int color, String command, int playersNeeded) {
        this.time = time;
        this.linkedPos = linkedPos;
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.color = color;
        this.command = command;
        this.playersNeeded = playersNeeded;
    }

    Scan(int time, @Nullable BlockPos linkedPos, BlockPos pos1, BlockPos pos2, int color, int timer, String command, int playersNeeded) {
        this.time = time;
        this.linkedPos = linkedPos;
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.color = color;
        this.command = command;
        this.playersNeeded = playersNeeded;
    }

    public boolean playerInside(EntityPlayer player) {
        return getAABB().contains(player.getPositionVector());
    }

    public int getTime() {
        return time;
    }

    @Nullable
    public BlockPos getLinkedPos() {
        return linkedPos;
    }

    public int getColor() {
        return color;
    }

    public BlockPos getPos1() {
        return pos1;
    }

    public BlockPos getPos2() {
        return pos2;
    }

    public AxisAlignedBB getAABB() {
        return new AxisAlignedBB(pos1, pos2);
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public int getPlayersNeeded() {
        return playersNeeded;
    }

    public String getCommand() {
        return command;
    }

    public NBTTagCompound toTag() {
        NBTTagCompound tag = new NBTTagCompound();
        NBTTagCompound linkedPos = new NBTTagCompound();

        tag.setInteger("time", time);
        tag.setTag("linkedPos", this.linkedPos == null ? linkedPos : NBTUtil.createPosTag(this.linkedPos));
        tag.setTag("pos1", NBTUtil.createPosTag(this.pos1));
        tag.setTag("pos2", NBTUtil.createPosTag(this.pos2));
        tag.setInteger("color", color);
        tag.setInteger("timer", timer);
        tag.setString("command", command);
        tag.setInteger("playersNeeded", playersNeeded);

        return tag;
    }

    public static Scan fromTag(NBTTagCompound tag) {
        int time, timer, color, playersNeeded;
        BlockPos linkedPos, pos1, pos2;

        time = tag.getInteger("time");
        linkedPos = tag.getCompoundTag("linkedPos").hasNoTags() ? null : NBTUtil.getPosFromTag(tag.getCompoundTag("linkedPos"));
        pos1 = NBTUtil.getPosFromTag(tag.getCompoundTag("pos1"));
        pos2 = NBTUtil.getPosFromTag(tag.getCompoundTag("pos2"));
        color = tag.getInteger("color");
        timer = tag.getInteger("timer");
        String command = tag.getString("command");
        playersNeeded = tag.getInteger("playersNeeded");

        return new Scan(time, linkedPos, pos1, pos2, color, timer, command, playersNeeded);
    }
}
