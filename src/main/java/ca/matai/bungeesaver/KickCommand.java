package ca.matai.bungeesaver;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import static ca.matai.bungeesaver.BungeeSaver.plugin;

public class KickCommand extends Command {

    public KickCommand(){
        super("netkick");
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (commandSender.hasPermission("bungeecord.kick")){
            if (args.length == 1){
                try {
                    for (ProxiedPlayer test: plugin.getProxy().getPlayers()) {
                        String name = test.getName().toLowerCase();
                        if (name.contains(args[0].toLowerCase())) {
                            test.disconnect(new ComponentBuilder("You have been Kicked!").color(ChatColor.RED).create());
                            Service.message(commandSender, test.getDisplayName() + " has been Kicked!");
                            return;
                        }
                    }
                    Service.message(commandSender, "No player found with name similar to: " + args[0]);
                    return;
                }catch (Exception e){
                    Service.message(commandSender, "An error occurred, the player: " + args[0] + " couldn't be kicked");
                    return;

                }
            }
            else if (args.length == 2){
                try {
                    for (ProxiedPlayer test: plugin.getProxy().getPlayers()) {
                        String name = test.getName().toLowerCase();
                        if (name.contains(args[0].toLowerCase())) {
                            test.disconnect(new ComponentBuilder(args[1]).create());
                            Service.message(commandSender, test.getDisplayName() + " has been kicked!");
                            return;
                        }
                    }
                    Service.message(commandSender, "No player found with name similar to: " + args[0]);
                    return;
                }catch (Exception e){
                    Service.message(commandSender, "An error occurred, the player: " + args[0] + " couldn't be kicked");
                    return;

                }
            }
            else{
                Service.message(commandSender, "Invalid command format! Please use the following:\n" +
                        "/Kick <player> {message}");
            }
        }
        else{
            commandSender.sendMessage(new ComponentBuilder("You do not have permission to execute this command!").color(ChatColor.RED).create());
        }


    }
}
