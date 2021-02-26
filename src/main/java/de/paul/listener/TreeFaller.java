package de.paul.listener;

import de.paul.miscs.ConfigManager;
import org.bukkit.GameMode;
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

    @EventHandler
    public void treeFall(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Location l = e.getBlock().getLocation();
        Block b = e.getBlock();

        int y = 1;


        boolean timberModEnabled = (boolean) config.get("timber.enable");

        int max = (int) config.get("timber.maxHeight");

        if(timberModEnabled) {
            if (checkAllowedGamemode(p)){
                if (checkAllowedTools(p)) {
                    if (p.hasPermission("timberPlugin.fellTrees")) {
                        if(checkSneaking(p)) {
                            if (b.getType() == Material.LOG || b.getType() == Material.LOG_2) {
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
                        }
                        } else {
                            p.sendMessage("§4You don't have enough Permission to do that!");
                    }
                }
            }
        }
    }

    private boolean checkAllowedTools(Player p) {
        Material playersTool = p.getItemInHand().getType();

        final Material m1 = Material.getMaterial((String) config.get("timber.allowed-tools.0"));

        final Material m2 = Material.getMaterial((String) config.get("timber.allowed-tools.1"));

        final Material m3 = Material.getMaterial((String) config.get("timber.allowed-tools.2"));

        final Material m4 = Material.getMaterial((String) config.get("timber.allowed-tools.3"));

        final Material m5 = Material.getMaterial((String) config.get("timber.allowed-tools.4"));

        if(p.getItemInHand().getType() == Material.AIR) {
            return false;
        } else if(playersTool == m1 || playersTool == m2 || playersTool == m3 || playersTool == m4 || playersTool == m5) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkAllowedGamemode(Player p) {
        GameMode pgm = p.getGameMode();
        GameMode gm0 = getGamemodeByString((String) config.get("timber.allowed-gamemodes.0"));
        GameMode gm1 = getGamemodeByString((String) config.get("timber.allowed-gamemodes.1"));

        if(pgm == gm0) {
            return true;
        } else if (pgm == gm1) {
            return true;
        } else {
            return false;
        }

    }

    private GameMode getGamemodeByString(String s) {
        if(s.equalsIgnoreCase("SURVIVAL")) {
            return GameMode.SURVIVAL;
        } else if(s.equalsIgnoreCase("CREATIVE")) {
            return GameMode.CREATIVE;
        } else if(s.equalsIgnoreCase("ADVENTURE")) {
            return GameMode.ADVENTURE;
        } else if(s.equalsIgnoreCase("SPECTATOR")) {
            return GameMode.SPECTATOR;
        } else {
            return null;
        }
    }

    private boolean checkSneaking(Player p) {
        boolean b = (boolean) config.get("timber.disable-when-sneaking");

        if(b) {
            if(p.isSneaking()) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }


}
