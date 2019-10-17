package ca.matai.bungeesaver;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import static ca.matai.bungeesaver.BungeeSaver.plugin;

public class BanCommand extends Command {

    BanCommand(){
        super("netban");
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (commandSender.hasPermission("bungeecord.ban")){
            if (args.length == 1){
                try {
                    for (ProxiedPlayer test: plugin.getProxy().getPlayers()) {

                        String name = test.getName().toLowerCase();
                        if (name.contains(args[0].toLowerCase())) {
                            String msg = "You have been banned by " +commandSender + "without a reason.";
                            Ban ban = new Ban(test, msg, commandSender.getName(), test.getServer().getInfo().getName());
                            String id = plugin.service.banPlayer(ban);
                            msg += "\nBan ID: " + id;

                            test.disconnect(new TextComponent(msg));
                            Service.message(commandSender, test.getDisplayName() + " has been banned!");
                            return;
                        }
                    }
                    Service.message(commandSender, "No player found with name similar to: " + args[0]);
                }catch (Exception e){
                    Service.message(commandSender, "An error occurred, the player: " + args[0] + " couldn't be banned");

                }
            }
            else if (args.length >= 2){
                try {
                    for (ProxiedPlayer test: plugin.getProxy().getPlayers()) {
                        String name = test.getName().toLowerCase();
                        if (name.contains(args[0].toLowerCase())) {
                            String msg = "You have been banned by: \n" +
                                         ChatColor.BOLD + ChatColor.RED +commandSender.getName()+ChatColor.RESET+"\n\n";
                            msg += "Reason: \n";
                            for (int i = 1; i < args.length; i++) {
                               msg += args[i] + " ";
                            }

                            Ban ban = new Ban(test, msg, commandSender.getName(), test.getServer().getInfo().getName());
                            String bID = plugin.service.banPlayer(ban);
                            msg += "\n\nBan ID: " + bID;
                            test.disconnect(new TextComponent(msg));
                            Service.message(commandSender, test.getDisplayName() + " has been banned!");
                            return;
                        }
                    }
                    Service.message(commandSender, "No player found with name similar to: " + args[0]);
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
