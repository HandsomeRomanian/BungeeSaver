package ca.matai.bungeesaver;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.Arrays;

import static ca.matai.bungeesaver.BungeeSaver.plugin;

public class YeetCommand extends Command {

    YeetCommand(){
        super("yeet");
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (commandSender.hasPermission("bungeecord.kick")){
            if (args.length == 1){
                try {
                    for (ProxiedPlayer test: plugin.getProxy().getPlayers()) {
                        String name = test.getName().toLowerCase();
                        if (name.contains(args[0].toLowerCase())) {
                            test.disconnect(new TextComponent(ChatColor.RED + "Yeeted by "+commandSender.getName()+"!"));
                            DBService.message(commandSender, test.getDisplayName() + " has been yeeted out!");
                            return;
                        }
                    }
                    DBService.message(commandSender, "No player found with name similar to: " + args[0]);
                }catch (Exception e){
                    DBService.message(commandSender, "An error occurred, the player: " + args[0] + " couldn't be yeeted");

                }
            }
            else if (args.length == 2){
                try {
                    for (ProxiedPlayer test: plugin.getProxy().getPlayers()) {
                        String name = test.getName().toLowerCase();
                        if (name.contains(args[0].toLowerCase())) {
                            test.disconnect(new TextComponent(ChatColor.RED + Arrays.deepToString(args) + ChatColor.RESET +"\n Yeeted by: " + commandSender.getName()));
                            DBService.message(commandSender, test.getDisplayName() + " has been yeeted out!");
                            return;
                        }
                    }
                    DBService.message(commandSender, "No player found with name similar to: " + args[0]);
                }catch (Exception e){
                    DBService.message(commandSender, "An error occurred, the player: " + args[0] + " couldn't be yeeted");

                }
            }
            else{
                DBService.message(commandSender, "Invalid command format! Please use the following:\n" +
                        "/yeet <player> {message}");
            }
        }
        else{
            commandSender.sendMessage(new ComponentBuilder("You do not have permission to execute this command!").color(ChatColor.RED).create());
        }


    }
}
