package cc.wumpus.wstaffcmdblockaddon;

import cc.wumpus.wstaff.api.wStaffAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class WStaffCmdBlockAddon extends JavaPlugin implements Listener {

    private static WStaffCmdBlockAddon instance;
    private static wStaffAPI staffAPI;

    @Override
    public void onEnable() {
        instance = this;

        getConfig().options();
        saveDefaultConfig();

        if (Bukkit.getPluginManager().getPlugin("wStaff") != null) {
            staffAPI = wStaffAPI.getInstance();
            WumUtil.tellConsole(" &e&l|> &awStaff &7was &afound &7and has been hooked.");
        } else {
            WumUtil.tellConsole(" &e&l|> &awStaff &7was &cnot found&7.");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        Bukkit.getPluginManager().registerEvents(this, this);

        WumUtil.tellConsole(" &e&l|> &7" + getDescription().getName() + " has been enabled.");
    }

    @Override
    public void onDisable() {
        WumUtil.tellConsole(" &e&l|> &7" + getDescription().getName() + " has been disabled.");
    }


    @EventHandler
    public void onCommandPreProcess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String[] args = event.getMessage().split(" ");
        List<String> whitelisted = getConfig().getStringList("whitelisted-commands");
        List<String> blacklisted = getConfig().getStringList("blacklisted-commands");
        boolean found = false;

        if (!player.hasPermission("staff.staffmode")) return;

        if (getStaffAPI().isStaffmode(player)) {
            for (String command : whitelisted) {
                if (args[0].equalsIgnoreCase("/" + command)) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                WumUtil.tellPlayer(player, getConfig().getString("blocked-staffmode"));
                event.setCancelled(true);
            }
            return;
        }

        if (!getStaffAPI().isStaffmode(player)) {
            for (String command : blacklisted) {
                if (args[0].equalsIgnoreCase("/" + command)) {
                    found = true;
                    break;
                }
            }

            if (found) {
                WumUtil.tellPlayer(player, getConfig().getString("blocked-normal"));
                event.setCancelled(true);
            }
        }
    }


    public static wStaffAPI getStaffAPI() {
        return staffAPI;
    }

    public static WStaffCmdBlockAddon getInstance() {
        return instance;
    }

}
