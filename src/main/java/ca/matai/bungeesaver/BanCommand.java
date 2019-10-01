package ca.matai.bungeesaver;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import static ca.matai.bungeesaver.BungeeSaver.plugin;

public class BanCommand extends Command {

    public BanCommand(){
        super("netban");
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (commandSender.hasPermission("bungeecord.ban")){
            if (args.length == 1){
                try {
                    ProxiedPlayer player = plugin.getProxy().getPlayer(args[0]);
                    plugin.service.banPlayer(new Ban(player,args.toString(),commandSender.getName(),player.getServer().getInfo().getName()));
                    player.disconnect(new ComponentBuilder("You have been banned!").color(ChatColor.RED)
                            .append("\n Banned by:" + commandSender.getName()).color(ChatColor.RESET).create());
                    Service.message(commandSender, player.getDisplayName() + " has been banned!");
                }catch (Exception e){
                    Service.message(commandSender, "An error occurred, the player: " + args[0] + " couldn't be banned");

                }
            }
            else if (args.length == 2){
                try {
                    ProxiedPlayer player = plugin.getProxy().getPlayer(args[0]);
                    plugin.service.banPlayer(new Ban(player,args.toString(),commandSender.getName(),player.getServer().getInfo().getName()));
                    player.disconnect(new ComponentBuilder(args[1])
                            .append("\n Banned by:" + commandSender.getName()).create());
                    Service.message(commandSender, player.getDisplayName() + " has been banned!");
                }catch (Exception e){
                    Service.message(commandSender, "An error occurred, the player: " + args[0] + " couldn't be banned");
                }
            }
            else{
                Service.message(commandSender, "Invalid command format! Please use the following:\n" +
                        "/ban <player>");
            }
        }
        else{
            commandSender.sendMessage(new ComponentBuilder("You do not have permission to execute this command!").color(ChatColor.RED).create());
        }


    }
}
