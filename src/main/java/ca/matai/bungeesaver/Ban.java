package ca.matai.bungeesaver;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.Timestamp;
import java.util.UUID;

public class Ban {

    public UUID uuid;
    public String username;
    public String reason;
    public Timestamp bandate;
    public Timestamp endate;
    public String bannedby;
    public String server;

    //`UUID`, `USERNAME`, `BANDATE`, `BANEND`, `BANNEDBY`, `REASON`, `SERVER`

    public Ban(String uuid, String user, String reason,String bannedby, String server){
        this.uuid = UUID.fromString(uuid);
        this.username = user;
        this.reason = reason;
        this.bandate = new Timestamp(System.currentTimeMillis());
        this.bannedby = bannedby;
        this.server = server;
    }

    public Ban(ProxiedPlayer player , String reason,String bannedby, String server){
        this.uuid = player.getUniqueId();
        this.username = player.getName();
        this.reason = reason;
        this.bandate = new Timestamp(System.currentTimeMillis());
        this.bannedby = bannedby;
        this.server = server;
    }

    public String toString(){
        return this.username + ":" + this.uuid.toString() +"\n" + this.bannedby;
    }
}
