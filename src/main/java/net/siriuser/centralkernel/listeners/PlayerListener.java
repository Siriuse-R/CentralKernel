package net.siriuser.centralkernel.listeners;

import static net.siriuser.centralkernel.storage.I18n.*;
import net.siriuser.centralkernel.CentralKernelCore;
import net.siriuser.centralkernel.PluginHelper;
import net.siriuser.centralkernel.feature.GeoIP;
import net.siriuser.centralkernel.storage.I18n;
import net.syamn.utils.Util;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
    private CentralKernelCore plugin;
    public PlayerListener (final CentralKernelCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin (final PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        //TODO: Rei'sMinimap対応

        String msg = _(((player.hasPlayedBefore()) ? "JoinMessage" : "firstJoinMessage"), I18n.PLAYER, player.getName());
        if (msg.length() < 1) msg = null;
        event.setJoinMessage(msg);


        if (PluginHelper.getInstance().getConfig().getUseGeoIP()){
            msg = event.getJoinMessage();
            if (msg != null){
                String geoStr = GeoIP.getInstance().getGeoIpString(player, PluginHelper.getInstance().getConfig().getUseSimpleFormatOnJoin());
                event.setJoinMessage(msg + Util.coloring("&7") + " (" + geoStr + ")");
            }
        }

        if (!player.hasPlayedBefore()) {
            final int unique = plugin.getServer().getOfflinePlayers().length;
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    Util.broadcastMessage(_("VisitorMessage", I18n.VISITOR, unique));
                }
            }, 5L); // 0.25 secs after

        }
    }

    @EventHandler
    public void onPlayerQuit (final PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        String msg = _("QuitMessage", I18n.PLAYER, player.getName());
        if (msg.length() < 1) msg = null;
        event.setQuitMessage(msg);
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerKick (final PlayerKickEvent event) {
        final Player player = event.getPlayer();
        final String reason = event.getReason();
        String msg = _("KickMessage", I18n.PLAYER, player.getName(), I18n.REASON, reason);
        if (msg.length() < 1) msg = null;
        event.setLeaveMessage(msg);
    }
}
