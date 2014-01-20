package net.siriuser.centralkernel.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.siriuser.centralkernel.CentralKernelCore;
import net.syamn.utils.LogUtil;
import net.syamn.utils.Util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

/**
 * CommandHandler (CommandHandler.java)
 * @author syam(syamn)
 */
public class CommandHandler implements TabExecutor{
    private final CentralKernelCore plugin;
    private Map<String, BaseCommand> commands = new HashMap<String, BaseCommand>();

    public CommandHandler(final CentralKernelCore plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args){
        final String commandName = command.getName().toLowerCase(Locale.ENGLISH);
        BaseCommand cmd = commands.get(commandName);
        if (cmd == null){
            Util.message(sender, "&cCommand not found!");
            return true;
        }

        // run command
        cmd.run(plugin, sender, commandLabel, args, false);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String commandLabel, String[] args){
        final String commandName = command.getName().toLowerCase(Locale.ENGLISH);
        BaseCommand cmd = commands.get(commandName);
        if (cmd == null){
            return null;
        }

        // check perms
        if (sender != null && !cmd.permission(sender)){
            return null;
        }

        // get tab complete
        return cmd.tabComplete(plugin, sender, commandLabel, args);
    }

    public void registerCommand(final BaseCommand bc){
        if (bc.name != null){
            commands.put(bc.name, bc);
        }else{
            LogUtil.warning("Invalid command not registered! " + bc.getClass().getName());
        }
    }

    public BaseCommand getCommand(final String commandName){
        return commands.get(commandName);
    }
}
