package de.paul.miscs;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private static File config = new File("plugins/TimberPlugin", "config.yml");
    private static FileConfiguration configFile = YamlConfiguration.loadConfiguration(config);

    public static File getConfig() {
        return config;
    }

    public static void reloadConfig() {
        configFile = YamlConfiguration.loadConfiguration(config);
    }

    public static FileConfiguration getConfigFile() {
        return configFile;
    }

    public static void saveConfigFile() {
        try {
            configFile.save(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
