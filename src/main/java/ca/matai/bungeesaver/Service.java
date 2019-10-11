package ca.matai.bungeesaver;

import java.sql.*;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;

import java.util.UUID;

class Service {

    static void message(CommandSender commandSender, String msg){
        commandSender.sendMessage(new ComponentBuilder(msg).create());
    }

    Connection connectDB(){
        try {
            synchronized (this) {
                return DriverManager.getConnection("jdbc:mysql://localhost:3306/netbans","netbans","Passw0rd");
            }
        } catch (SQLException e) {
            ProxyServer.getInstance().getLogger().info("Connection error!\n"+e.getMessage());
            e.printStackTrace();
            System.out.println("SQL might not be set up correctly please be sure to modify the config");
        }
        return null;
    }

    boolean checkBAN(UUID uuid){

        try {
            Connection DB = connectDB();
            if (DB == null){
                return true;
            }
            Statement req = DB.createStatement();
            ResultSet rep = req.executeQuery("select * from bans where UUID = '"+uuid+"';");
            if (rep != null){
                while (rep.next()){
                    if(rep.getString(0).equalsIgnoreCase(uuid.toString())){
                        return true;
                    }
                }
                return false;
            }
            DB.close();
        }catch (Exception e){
            return true;
        }
        return true;
    }

    void banPlayer(Ban ban){

        try {
            Connection DB = connectDB();
            if (DB == null){
            }
            Statement req = DB.createStatement();
            ResultSet rep = req.executeQuery("INSERT INTO `bans` (`UUID`, `USERNAME`, `BANDATE`, `BANEND`, `BANNEDBY`, `REASON`, `SERVER`) " +
                    "VALUES ('"+ban.banned.getUniqueId()+"', '"+ban.banned.getName()+"', '"+ban.bandate+"', NULL, '"+ban.bannedby+"', '"+ban.reason+"', '"+ban.server+"');");
            DB.close();
        }catch (Exception e){}
    }

}

