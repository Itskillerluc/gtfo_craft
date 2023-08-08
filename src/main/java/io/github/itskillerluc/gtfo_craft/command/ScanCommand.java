package io.github.itskillerluc.gtfo_craft.command;

import io.github.itskillerluc.gtfo_craft.data.Scan;
import io.github.itskillerluc.gtfo_craft.data.ScanWorldSavedData;
import net.minecraft.command.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import org.apache.commons.lang3.math.NumberUtils;
import scala.Int;

import javax.annotation.Nullable;
import java.util.List;

public class ScanCommand extends CommandBase {
    @Override
    public String getName() {
        return "scan";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/scan <x> <y> <z> <x> <y> <z> <time> <players needed> [color] [<x> <y> <z>] <command>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length >= 9) {
            int color = 0x00DD00;
            int commandIndex = 8;
            @Nullable BlockPos linkedPos = null;
            if (NumberUtils.isCreatable(args[8])) {
                color = Integer.decode(args[8]);
                commandIndex = 9;
            }

            try {
                linkedPos = new BlockPos(
                        parseCoordinate(sender.getPosition().getX(), args[9],true).getResult(),
                        parseCoordinate(sender.getPosition().getY(), args[10], true).getResult(),
                        parseCoordinate(sender.getPosition().getZ(), args[11], true).getResult());
                commandIndex = 12;
            } catch (NumberInvalidException | ArrayIndexOutOfBoundsException ignored) {}

            double x1 = parseCoordinate(sender.getPosition().getX(), args[0],true).getResult();
            double y1 = parseCoordinate(sender.getPosition().getY(), args[1], true).getResult();
            double z1 = parseCoordinate(sender.getPosition().getZ(), args[2], true).getResult();

            double x2 = parseCoordinate(sender.getPosition().getX(), args[3],true).getResult();
            double y2 = parseCoordinate(sender.getPosition().getY(), args[4], true).getResult();
            double z2 = parseCoordinate(sender.getPosition().getZ(), args[5], true).getResult();

            int time = parseInt(args[6]);
            int playersNeeded = parseInt(args[7]);

            String command = buildString(args, commandIndex);

            ScanWorldSavedData.get(sender.getEntityWorld()).scanList.add(new Scan(time, linkedPos, new BlockPos(x1, y1, z1), new BlockPos(x2, y2, z2), color, command, playersNeeded));
        } else {
            throw new WrongUsageException(getUsage(sender));
        }
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length > 0 && args.length < 4) {
            return getTabCompletionCoordinate(args, 0, targetPos);
        } else if (args.length >= 4 && args.length < 7) {
            return getTabCompletionCoordinate(args, 3, targetPos);
        }
        return super.getTabCompletions(server, sender, args, targetPos);
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 1;
    }
}
