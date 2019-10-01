package ca.matai.bungeesaver;

        import net.md_5.bungee.api.plugin.Plugin;

public class BungeeSaver extends Plugin  {

    static BungeeSaver plugin;
    Service service = new Service();

    @Override
    public void onEnable() {
        plugin = this;
        getProxy().getPluginManager().registerListener(this, new Events());
        getProxy().getPluginManager().registerCommand(this, new BanCommand());
        getProxy().getPluginManager().registerCommand(this, new KickCommand());
        getProxy().getPluginManager().registerCommand(this, new YeetCommand());
        getProxy().getPluginManager().registerCommand(this, new CheckBanCommand());
    }
}
