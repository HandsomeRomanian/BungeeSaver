package ca.matai.bungeesaver;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.Time;
import java.util.Date;

public class Ban {
    public ProxiedPlayer banned;
    public String reason;
    public long bandate;
    public long endate;
    public String bannedby;
    public String server;

    public Ban(ProxiedPlayer banned, String reason,String bannedby, String server){
        this.banned = banned;
        this.reason = reason;
        this.bandate = System.currentTimeMillis();
        this.bannedby = bannedby;
        this.server = server;
    }

    public String toString(){
        return this.banned.getName() + ":" + this.banned.getUniqueId() +"\n" + this.bannedby;
    }
}
