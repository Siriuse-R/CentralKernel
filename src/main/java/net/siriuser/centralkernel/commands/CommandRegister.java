package net.siriuser.centralkernel.commands;

import java.util.HashSet;
import java.util.Set;

/**
 * CommandRegister (CommandRegister.java)
 * @author syam(syamn)
 */
public class CommandRegister {
    private static Set<BaseCommand> getCommands(){
        Set<BaseCommand> cmds = new HashSet<BaseCommand>();

        return cmds;
    }

    public static void registerCommands(final CommandHandler handler){
        Set<BaseCommand> cmds = getCommands();

        for (final BaseCommand cmd : cmds){
            handler.registerCommand(cmd);
        }
    }
}
