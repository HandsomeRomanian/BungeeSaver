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
                    for (ProxiedPlayer test: plugin.getProxy().getPlayers()) {

                        String name = test.getName().toLowerCase();
                        if (name.contains(args[0].toLowerCase())) {
                            Ban ban = new Ban(test, "You have been banned!", commandSender.getName(), test.getServer().getInfo().getName());
                            Service.message(commandSender,ban.toString());
                            plugin.service.banPlayer(ban);
                            test.disconnect(new ComponentBuilder(ban.reason).color(ChatColor.RED)
                                    .append("\n Banned by:" + commandSender.getName()).color(ChatColor.RESET).create());
                            Service.message(commandSender, test.getDisplayName() + " has been banned!");
                            return;
                        }
                    }
                    Service.message(commandSender, "No player found with name similar to: " + args[0]);
                    return;
                }catch (Exception e){
                    Service.message(commandSender, "An error occurred, the player: " + args[0] + " couldn't be banned");
                    return;

                }
            }
            else if (args.length == 2){
                try {
                    for (ProxiedPlayer test: plugin.getProxy().getPlayers()) {
                        String name = test.getName().toLowerCase();
                        if (name.contains(args[0].toLowerCase())) {
                            Ban ban = new Ban(test, args.toString(), commandSender.getName(), test.getServer().getInfo().getName());
                            Service.message(commandSender,ban.toString());
                            plugin.service.banPlayer(ban);
                            test.disconnect(new ComponentBuilder(ban.reason)
                                    .append("\n Banned by:" + commandSender.getName()).create());
                            Service.message(commandSender, test.getDisplayName() + " has been banned!");
                            return;
                        }
                    }
                    Service.message(commandSender, "No player found with name similar to: " + args[0]);
                    return;
                }catch (Exception e){
                    Service.message(commandSender, "An error occurred, the player: " + args[0] + " couldn't be banned");
                    return;
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
