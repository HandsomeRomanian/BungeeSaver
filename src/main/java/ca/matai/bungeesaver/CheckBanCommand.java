package ca.matai.bungeesaver;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.chat.ComponentBuilder;

import java.util.UUID;

import static ca.matai.bungeesaver.BungeeSaver.plugin;

public class CheckBanCommand extends Command {

    public CheckBanCommand(){
        super("checkban");
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        commandSender.sendMessage(new ComponentBuilder("Works").create());
        if (commandSender.hasPermission("bungeecord.checkban")){
            if (args.length == 1){
                try {
                    UUID uID = UUID.fromString(args[0]);
                    if (plugin.service.checkBAN(uID)){
                        Service.message(commandSender,"UUID is in ban database.");
                    }
                    else{
                        Service.message(commandSender,"UUID is NOT in ban database.");
                    }

                }
                catch (Exception e){
                    ProxyServer.getInstance().getLogger().info(e.getMessage());
                }
            }
        }
        else{
            commandSender.sendMessage(new ComponentBuilder("You do not have permission to execute this command!").color(ChatColor.RED).create());
        }


    }
}
