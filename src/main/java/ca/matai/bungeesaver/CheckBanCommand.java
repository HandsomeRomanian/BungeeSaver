package ca.matai.bungeesaver;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;

import java.util.List;
import java.util.UUID;

public class CheckBanCommand extends Command {

    public CheckBanCommand() {
        super("checkban");
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (commandSender.hasPermission("bungeecord.checkban")) {
            if (args.length == 0) {
                List<Ban> bans = DBService.getBans();
                if (bans == null) {
                    DBService.message(commandSender, "No bans on record.");
                } else {
                    for (Ban ban : bans) {
                        DBService.message(commandSender, "User: " + ban.username + " got banned by " + ban.bannedby + " on server " + ban.server + "\n" + ban.uuid.toString());
                    }
                }
            } else if (args.length == 1) {
                try {
                    UUID uID = UUID.fromString(args[0]);
                    if (DBService.checkBAN(uID)) {
                        DBService.message(commandSender, "UUID is in ban database.");
                    } else {
                        DBService.message(commandSender, "UUID is NOT in ban database.");
                    }

                } catch (Exception e) {
                    ProxyServer.getInstance().getLogger().info(e.getMessage());
                }
            } else {
                commandSender.sendMessage(new ComponentBuilder("/checkban [uuid]").create());
            }
        } else {
            commandSender.sendMessage(new ComponentBuilder("You do not have permission to execute this command!").color(ChatColor.RED).create());
        }


    }
}
