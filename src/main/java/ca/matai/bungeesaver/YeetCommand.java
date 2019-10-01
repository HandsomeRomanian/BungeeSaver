package ca.matai.bungeesaver;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import static ca.matai.bungeesaver.BungeeSaver.plugin;

public class YeetCommand extends Command {

    public YeetCommand(){
        super("yeet");
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (commandSender.hasPermission("bungeecord.kick")){
            if (args.length == 1){
                try {
                    ProxiedPlayer player = plugin.getProxy().getPlayer(args[0]);
                    player.disconnect(new ComponentBuilder("Yeeted by: "+commandSender.getName()+"!").color(ChatColor.RED).create());
                    Service.message(commandSender, player.getDisplayName() + " has been yeeted out!");
                }catch (Exception e){
                    Service.message(commandSender, "An error occurred, the player: " + args[0] + " couldn't be yeeted");

                }
            }
            else if (args.length == 2){
                try {
                    ProxiedPlayer player = plugin.getProxy().getPlayer(args[0]);
                    player.disconnect(new ComponentBuilder(args.toString()).append("\n Yeeted by: "+commandSender.getName()).create());
                    Service.message(commandSender, player.getDisplayName() + " has been yeeted out!");
                }catch (Exception e){
                    Service.message(commandSender, "An error occurred, the player: " + args[0] + " couldn't be yeeted");

                }
            }
            else{
                Service.message(commandSender, "Invalid command format! Please use the following:\n" +
                        "/yeet <player> {message}");
            }
        }
        else{
            commandSender.sendMessage(new ComponentBuilder("You do not have permission to execute this command!").color(ChatColor.RED).create());
        }


    }
}
