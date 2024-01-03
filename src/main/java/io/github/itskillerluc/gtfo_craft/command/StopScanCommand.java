package io.github.itskillerluc.gtfo_craft.command;

import io.github.itskillerluc.gtfo_craft.data.ScanWorldSavedData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class StopScanCommand extends CommandBase {
    @Override
    public String getName() {
        return "stopscan";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/stopscan";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        ScanWorldSavedData.get(sender.getEntityWorld()).scanList.clear();
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 1;
    }
}
