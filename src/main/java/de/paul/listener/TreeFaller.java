package de.paul.listener;

import de.paul.miscs.ConfigManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

//Materials: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html

public class TreeFaller implements Listener {


    private final FileConfiguration config = ConfigManager.getConfigFile();

    private boolean checkAllowedTools(Player p) {
        Material playersTool = p.getItemInHand().getType();

        final Material m1 = Material.getMaterial((String) config.get("timber.allowed-tools.1"));

        final Material m2 = Material.getMaterial((String) config.get("timber.allowed-tools.2"));

        final Material m3 = Material.getMaterial((String) config.get("timber.allowed-tools.3"));

        final Material m4 = Material.getMaterial((String) config.get("timber.allowed-tools.4"));

        final Material m5 = Material.getMaterial((String) config.get("timber.allowed-tools.5"));

        if(p.getItemInHand().getType() == Material.AIR) {
            return false;
        } else if(playersTool == m1 || playersTool == m2 || playersTool == m3 || playersTool == m4 || playersTool == m5) {
            return true;
        } else {
            return false;
        }
    }

    @EventHandler
    public void treeFall(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Location l = e.getBlock().getLocation();
        Block b = e.getBlock();

        int y = 1;



        boolean timberModEnabled = (boolean) config.get("timber.enable");

        int max = (int) config.get("timber.maxHeight");

        if(timberModEnabled) {
            if(checkAllowedTools(p)) {
                if (p.hasPermission("timberPlugin.fellTrees")) {
                    if(b.getType() == Material.LOG || b.getType() == Material.LOG_2) {
                        while (y < max) {
                            Location l2 = new Location(l.getWorld(), l.getX(), l.getY() + y, l.getZ());
                            Block b2 = l2.getBlock();
                            if (b2.getType() == Material.LOG || b2.getType() == Material.LOG_2) {
                                b2.breakNaturally();
                                y++;
                            } else {
                                break;
                            }
                        }
                        y = 0;

                        //y minus (must be enabled in config.yml)
                        if ((boolean) config.get("timber.enable-downwards")) {
                            while (y < max) {
                                Location l3 = new Location(l.getWorld(), l.getX(), l.getY() - y, l.getZ());
                                Block b3 = l3.getBlock();
                                if (b3.getType() == Material.LOG || b3.getType() == Material.LOG_2) {
                                    b3.breakNaturally();
                                    y++;
                                } else {
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    p.sendMessage("§4You don't have enough Permission to do that!");
                }
            }
        }
    }


}
