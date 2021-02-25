package de.paul.Main;


import de.paul.listener.*;
import de.paul.miscs.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

        public static final ConsoleCommandSender console = Bukkit.getConsoleSender();

        @Override
        public void onEnable() {
            if(!ConfigManager.getConfig().exists()) {
                saveDefaultConfig();
                ConfigManager.reloadConfig();
            }

            console.sendMessage(ChatColor.GREEN + "Timber Plugin activated!");
            //loadCommands();
            loadEvents();
        }

        @Override
        public void onDisable() {
            console.sendMessage(ChatColor.GREEN + "Timber Plugin deactivated!");
        }

        //private void loadCommands() { }

        private void loadEvents() {
            getServer().getPluginManager().registerEvents(new TreeFaller(), this);
        }

}
