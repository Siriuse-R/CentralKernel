package net.siriuser.centralkernel.permission;

import net.syamn.utils.Util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permissible;

public enum Perms {
    REIS_DEFAULT ("reisminimap.default"),
    ;

    // ノードヘッダー
    final String HEADER = "centralkernel.";
    private String node;

    /**
     * コンストラクタ
     *
     * @param node
     *            権限ノード
     */
    Perms(final String node) {
        this.node = HEADER + node;
    }

    /**
     * 指定したプレイヤーが権限を持っているか
     *
     * @param perm Permissible. Player, CommandSender etc
     * @return boolean
     */
    public boolean has(final Permissible perm) {
        if (perm == null) {
            return false;
        }
        return PermissionManager.hasPerm(perm, this);
    }

    /**
     * 指定したプレイヤーが権限を持っているか
     *
     * @param perm Permissible. Player, CommandSender etc
     * @param subPerm subPermission without head period
     * @return boolean
     */
    public boolean has(final Permissible perm, final String subPerm) {
        if (perm == null) {
            return false;
        }
        return PermissionManager.hasPerm(perm, this.getNode().concat("." + subPerm));
    }

    /**
     * Send message to players has this permission.
     * @param message send message.
     */
    public void message(final String message){
        for (final Player player : Bukkit.getServer().getOnlinePlayers()){
            if (this.has(player)){
                Util.message(player, message);
            }
        }
    }

    public String getNode(){
        return this.node;
    }
}