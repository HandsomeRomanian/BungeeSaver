package ca.matai.bungeesaver;

import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;

public class BungeeSaver extends Plugin {

    static BungeeSaver plugin;

    @Override
    public void onEnable() {
        generateConfig();
        plugin = this;
        getProxy().getPluginManager().registerListener(this, new Events());
        getProxy().getPluginManager().registerCommand(this, new BanCommand());
        getProxy().getPluginManager().registerCommand(this, new KickCommand());
        getProxy().getPluginManager().registerCommand(this, new YeetCommand());
        getProxy().getPluginManager().registerCommand(this, new CheckBanCommand());
    }

    private void generateConfig() {
        if (!getDataFolder().exists())
            getDataFolder().mkdir();
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                try (InputStream is = getResourceAsStream("config.yml");
                     OutputStream os = new FileOutputStream(configFile)) {
                    ByteStreams.copy(is, os);
                }
            } catch (IOException e) {
                throw new RuntimeException("Unable to create configuration file", e);
            }
        }
    }

    static Configuration getConfig() {
        try {
            return ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File("./plugins/BungeeSaver/config.yml"));
        } catch (IOException e) {
            return new Configuration();
        }
    }
}
