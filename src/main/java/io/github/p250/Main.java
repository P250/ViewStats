package io.github.p250;

import io.github.p250.commands.StatsCommand;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main extends JavaPlugin {

    private FileConfiguration config;
    private File configFile;

    private void initConfig() {
        configFile = new File(getDataFolder(), "viewstats_config.yml");
        config = YamlConfiguration.loadConfiguration(configFile);

        if (!(configFile.exists())) {
            HashMap<String, Object> defaults = new HashMap<String, Object>();

            List<String> statsFormat = new ArrayList<String>();
            statsFormat.add("         &7&m  ---------------------------------------  ");
            statsFormat.add("                                   &aYour &7Stats");
            statsFormat.add(" ");
            statsFormat.add("               &8- &7Kills: &e%deluxecombat_ranking_kills%");
            statsFormat.add("               &8- &7Deaths: &e%deluxecombat_ranking_deaths%");
            statsFormat.add("               &8- &7K/D: &e%deluxecombat_ranking_kd%");
            statsFormat.add("               &8- &7Combatlogs: &e%deluxecombat_ranking_combatlogs%");
            statsFormat.add("               &8- &7Points: &e%points%");
            statsFormat.add("               &8- &7Streak: &e%deluxecombat_ranking_streak%");
            statsFormat.add("               &8- &7Highest Streak: &e%deluxecombat_ranking_maxstreak%");
            statsFormat.add("               &8- &7Rank: &e#%deluxecombat_ranking_rank%");
            statsFormat.add("          &7&m ---------------------------------------  ");

            List<String> playerStatsFormat = new ArrayList<String>();
            playerStatsFormat.add("         &7&m  ---------------------------------------  ");
            playerStatsFormat.add("                      &a%player_name%&7's &7Stats");
            playerStatsFormat.add(" ");
            playerStatsFormat.add("               &8- &7Kills: &e%deluxecombat_ranking_kills%");
            playerStatsFormat.add("               &8- &7Deaths: &e%deluxecombat_ranking_deaths%");
            playerStatsFormat.add("               &8- &7K/D: &e%deluxecombat_ranking_kd%");
            playerStatsFormat.add("               &8- &7Combatlogs: &e%deluxecombat_ranking_combatlogs%");
            playerStatsFormat.add("               &8- &7Points: &e%points%");
            playerStatsFormat.add("               &8- &7Streak: &e%deluxecombat_ranking_streak%");
            playerStatsFormat.add("               &8- &7Highest Streak: &e%deluxecombat_ranking_maxstreak%");
            playerStatsFormat.add("               &8- &7Rank: &e#%deluxecombat_ranking_rank%");
            playerStatsFormat.add("          &7&m ---------------------------------------  ");

            defaults.put("viewstats.format", statsFormat);
            defaults.put("viewstats.player_format", playerStatsFormat);

            defaults.put("viewstats.command.player_notfound", "&cNo player found. Usage: /stats <player>");
            defaults.put("viewstats.command.usage", "&cUsage: /stats | /stats <player>");

            config.addDefaults(defaults);
            config.options().copyDefaults(true);
        }
        try {
            config.save(configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEnable() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            Bukkit.getLogger().severe("Error. PlaceholderAPI plugin was not found. This is required; shutting down. . .");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        initConfig();
        getCommand("stats").setExecutor(new StatsCommand(this));

    }

    @Override
    public FileConfiguration getConfig() {
        return config;
    }

}
