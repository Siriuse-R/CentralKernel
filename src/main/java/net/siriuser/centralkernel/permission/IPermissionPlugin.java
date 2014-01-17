package net.siriuser.centralkernel.permission;

import java.util.Set;

import net.siriuser.centralkernel.exception.NotSupportedException;

import org.bukkit.entity.Player;
import org.bukkit.permissions.Permissible;

public interface IPermissionPlugin {
    public abstract String getPrefix(Player player);
    public abstract String getSuffix(Player player);

    public abstract Set<Player> getPlayers(String groupName) throws NotSupportedException;
    public abstract String getGroupName(Player player) throws NotSupportedException;
    public abstract boolean isInGroup(Player player, String groupName) throws NotSupportedException;

    public abstract boolean hasPerm(Permissible sender, Perms perm);
    public abstract boolean hasPerm(Permissible sender, String node);
}
