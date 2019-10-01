package ca.matai.bungeesaver;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static ca.matai.bungeesaver.BungeeSaver.plugin;

public class Events implements Listener {

    @EventHandler
    public void onKick(final ServerKickEvent event) {

        final ProxiedPlayer p = event.getPlayer();

        if (event.getPlayer().getServer() != null) {
            String reason = event.getKickReasonComponent().toString();
            plugin.getLogger().info(reason);
            if (!event.getPlayer().getServer().getInfo().getName().equalsIgnoreCase("lobby")) {

                event.setCancelled(true);
                plugin.getProxy().getScheduler().schedule(plugin, new Runnable() {
                    @Override
                    public void run() {
                        p.connect(plugin.getProxy().getServerInfo("lobby"));
                        p.sendMessage(new TextComponent(ChatColor.RED + "Disconnected: " + ChatColor.RESET + event.getKickedFrom().getName() +" will be back shortly."));
                    }
                }, 1l, TimeUnit.MICROSECONDS);
            }
        }
    }

    @EventHandler
    public void onJoin(PreLoginEvent event) {
        UUID pID = event.getConnection().getUniqueId();
        if(plugin.service.checkBAN(pID)){
            ProxiedPlayer player = ProxyServer.getInstance().getPlayer(pID);
            player.disconnect(new ComponentBuilder("You are banned on this network.").color(ChatColor.RED).create());
        }
    }



}
