package ca.matai.bungeesaver;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.*;
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
            if (!event.getPlayer().getServer().getInfo().getName().equalsIgnoreCase("lobby")) {

                event.setCancelled(true);
                plugin.getProxy().getScheduler().schedule(plugin, new Runnable() {
                    @Override
                    public void run() {
                        p.connect(plugin.getProxy().getServerInfo("lobby"));
                        p.sendMessage(new TextComponent(ChatColor.RED + "Disconnected: " + ChatColor.RESET + event.getKickedFrom().getName() +" will be back shortly."));
                    }
                }, 1L, TimeUnit.MICROSECONDS);
            }
        }
    }

    @EventHandler
    public void onJoin(final LoginEvent  event) {
        UUID pID;
        try{

            String name = event.getConnection().getName();
            plugin.getLogger().info(name);
        }
        catch (Exception e){
            plugin.getLogger().info("name error");
        }
        try{
            pID = event.getConnection().getUniqueId();
            plugin.getLogger().info(pID.toString());
        }
        catch (Exception e){
            plugin.getLogger().info("UUID error");
            return;

        }
        if(plugin.service.checkBAN(pID)){
            event.setCancelReason(new TextComponent(plugin.service.getBan(pID)));
            event.setCancelled(true);
        }

    }

}
