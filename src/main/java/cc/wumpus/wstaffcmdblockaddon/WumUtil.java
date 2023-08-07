package cc.wumpus.wstaffcmdblockaddon;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
public class WumUtil extends WStaffCmdBlockAddon {

    public static String colorify(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void tellPlayer(Player player, String message) {
        if (message.equalsIgnoreCase("none")) return;
        player.sendMessage(WumUtil.colorify(WumUtil.prefix() + message));
    }

    public static void tellConsole(String message) {
        if (message.equalsIgnoreCase("none")) return;
        Bukkit.getConsoleSender().sendMessage(colorify(message));
    }

    public static String prefix() {
        if (getInstance().getConfig().getString("settings.prefix").equalsIgnoreCase("none")) {
            return "";
        }
        return WumUtil.colorify(getInstance().getConfig().getString("settings.prefix"));
    }

}