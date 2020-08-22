package io.github.p250.commands;

import io.github.p250.Main;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.List;

public class StatsCommand implements CommandExecutor {

    private Main plugin;

    public StatsCommand(Main instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player to use this plugin's command.");
            return true;
        }
        Player pl = (Player) sender;


        if (args.length == 0) {
            List<String> statsMessage = plugin.getConfig().getStringList("viewstats.format");

            List<String> finalMessage = PlaceholderAPI.setPlaceholders(pl, statsMessage);
            pl.sendMessage(finalMessage.toArray(new String[finalMessage.size()]));

            return true;
        } else if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null) {
                List<String> statsPlayerMessage = plugin.getConfig().getStringList("viewstats.player_format");

                for (int i = 0; i < statsPlayerMessage.size(); i++) {
                    String line = statsPlayerMessage.get(i);
                    statsPlayerMessage.set(i, line.replace("%player_name%", target.getName()));
                }

                List<String> finalMessage = PlaceholderAPI.setPlaceholders(pl, statsPlayerMessage);
                pl.sendMessage(finalMessage.toArray(new String[finalMessage.size()]));

                return true;
            } else {

                String error = plugin.getConfig().getString("viewstats.command.player_notfound");
                pl.sendMessage(cc(error));

                return true;
            }
        }

        String usage = plugin.getConfig().getString("viewstats.command.usage");
        pl.sendMessage(cc(usage));

        return true;
    }

    private String cc(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

}
