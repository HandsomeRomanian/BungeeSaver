package ca.matai.bungeesaver;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.api.chat.ComponentBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class Service {

    static void message(CommandSender commandSender, String msg){
        commandSender.sendMessage(new ComponentBuilder(msg).create());
    }

    Connection connectDB(){
        try {
            synchronized (this) {
                return DriverManager.getConnection("jdbc:mysql://web.martin:3306/netbans","user","passw0rd");
            }
        } catch (SQLException e) {
            ProxyServer.getInstance().getLogger().info("Connection error!\n"+e.getMessage());
            e.printStackTrace();
            System.out.println("SQL might not be set up correctly please be sure to modify the config");
        }
        return null;
    }

    static boolean checkBAN(UUID uuid){

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

    static String getBan(UUID uuid){

        String out = "You are banned on this network.";
        try {
            Connection DB = connectDB();
            if (DB == null){
                return out+"1";
            }
            Statement req = DB.createStatement();
            ResultSet rep = req.executeQuery("select REASON,ID from bans where UUID = '"+uuid+"';");
            if (rep != null){

                while (rep.next()){
                    return rep.getString(1)+"\n"+"Ban ID: "+rep.getString(2);
                }
                return out+"2";
            }
            DB.close();
        }catch (Exception e){

            BungeeSaver.plugin.getLogger().info(e.getMessage());
            return out+"3";
        }
        return out+"4";
    }

    static List<Ban> getBans(){
        List<Ban> out = new ArrayList<>();
        try {
                Connection DB = connectDB();
                if (DB == null){
                    out.add(new Ban(null,"","SQL error.","No DB received.", ""));
                    return out;
                }
                Statement req = DB.createStatement();
                ResultSet rep = req.executeQuery("select * from bans;");
                if (rep != null){
                //`UUID`, `USERNAME`, `BANDATE`, `BANEND`, `BANNEDBY`, `REASON`, `SERVER`
                    while (rep.next()){
                        out.add(new Ban(rep.getString("UUID"),rep.getString("USERNAME"),rep.getString("BANNEDBY"),rep.getString("REASON"),"SERVER"));
                    }
                    return out;
                }
                DB.close();
        }catch (Exception e){
            out.add(new Ban(null,"SQL error.",e.getMessage(),""));
        }
        return out;
    }

    static String banPlayer(Ban ban){
        String out;
        try {
            Connection DB = connectDB();
            if (DB == null){
                BungeeSaver.plugin.getLogger().info("No connection recieved during ban attempt");
                return "";
            }
            Statement req = DB.createStatement();
            ban.reason = ban.reason.replace("\'","\\\'");
            String sqlstatement = "INSERT INTO `bans` (`UUID`, `USERNAME`, `BANDATE`, `BANEND`, `BANNEDBY`, `REASON`, `SERVER`)" +
                    " VALUES ('"+ban.uuid.toString()+"', '"+ban.username+"', '"+ban.bandate+"', NULL, '"+ban.bannedby+"', '"+ban.reason+"', '"+ban.server+"');";
            req.executeUpdate(sqlstatement,Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = req.getGeneratedKeys();
            rs.next();
            out = Integer.toString(rs.getInt(1));
            DB.close();
            return out;
        }catch (Exception e){
            BungeeSaver.plugin.getLogger().info("An error occured during ban insertion.");
            BungeeSaver.plugin.getLogger().info(e.getMessage());
            return "";
        }
    }



}

