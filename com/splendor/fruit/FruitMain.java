package com.splendor.fruit;

import com.splendor.fruit.commands.MainCommand;
import com.splendor.fruit.data.MysqlGetter;
import com.splendor.fruit.data.MysqlSetter;
import com.splendor.fruit.data.MysqlSetup;
import com.splendor.fruit.guis.SaplingList;
import com.splendor.fruit.guis.TreeRemover;
import com.splendor.fruit.listeners.TreeHandler;
import com.splendor.fruit.listeners.TreeSet;
import com.splendor.fruit.manager.Sapling;
import com.splendor.fruit.manager.SpManager;
import com.splendor.fruit.manager.Tree;
import com.splendor.fruit.manager.TreeManager;
import com.splendor.fruit.utils.Converter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class FruitMain extends JavaPlugin {

    private static FruitMain instance;
    private static TreeManager Tmanager;
    private static SpManager Spmanager;

    private Converter con;
    private MysqlSetup mysql;
    private MysqlGetter mysqlGetter;
    private MysqlSetter mysqlSetter;
    private FileConfiguration config;

    public static SpManager getSpmanager() {
        return Spmanager;
    }

    public static TreeManager getTmanager() {
        return Tmanager;
    }

    public static FruitMain getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        init();
        registerCommands();
        registerEvents();
        saveResources();
        registerTree();
        registerSapling();
        startTimer();
    }

    @Override
    public void onDisable() {
        for (Tree tree : Tmanager.getTmap().values())
            mysqlSetter.saveTree(tree);
        for (Sapling sp : Spmanager.getData().values()) {
            if (!sp.isMakeable())
                continue;
            mysqlSetter.saveSapling(sp);
        }
    }

    public void reloadData() {
        for (Tree tree : Tmanager.getTmap().values())
            mysqlSetter.saveTree(tree);
        for (Sapling sp : Spmanager.getData().values()) {
            if (!sp.isMakeable())
                continue;
            mysqlSetter.saveSapling(sp);
        }
        registerTree();
        registerSapling();
    }

    private void saveResources() {
        if (!new File(getDataFolder() + "/config.yml").exists()) {
            saveResource("config.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(new File(getDataFolder() + "/config.yml"));
    }

    public FileConfiguration getConfigs() { return config; }

    private void init() {
        Tmanager = TreeManager.getInstance();

        Spmanager = SpManager.getInstance();

        con = new Converter();

        mysql = MysqlSetup.getInstance(this);

        try {
            mysql.init();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        mysqlGetter = MysqlGetter.getInstance();

        mysqlSetter = MysqlSetter.getInstance();
    }

    private void registerCommands() {
        getCommand("채집").setExecutor(new MainCommand());
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new TreeHandler(), this);
        Bukkit.getPluginManager().registerEvents(new TreeRemover(), this);
        Bukkit.getPluginManager().registerEvents(new TreeSet(), this);
        Bukkit.getPluginManager().registerEvents(new SaplingList(), this);
    }

    public MysqlSetup getMysql() {
        return mysql;
    }

    private void registerTree() {
        boolean hasdata = false;
        try {
            String sql = "SELECT * FROM TreeTbl";
            PreparedStatement s = getStatement(sql);
            ResultSet set = s.executeQuery();
            while (set.next())
                hasdata = true;
        } catch (SQLException ignored) {
        }
        if (hasdata) {
            try {
                List<String> idlist = new ArrayList<>();
                String sql = "SELECT id FROM TreeTbl";
                PreparedStatement s1 = getStatement(sql);
                ResultSet set = s1.executeQuery();
                while (set.next()) {
                    idlist.add(set.getString("id"));
                }
                for (String id : idlist) {
                    if (id != null) {
                        Map<String, String> dataMap = mysqlGetter.getTree(id);
                        getTmanager().registerTree(id, con.getLocation(dataMap.get("middle")), con.getLocation(dataMap.get("sign")), UUID.fromString(dataMap.get("owner")), Integer.parseInt(dataMap.get("timer")), dataMap.get("state"), dataMap.get("sapling"));
                    }
                }
            } catch (SQLException ignored) {
            }
        }
    }

    private void registerSapling() {
        boolean hasdata = false;
        try {
            String sql = "SELECT * FROM TreeTbl";
            PreparedStatement s = getStatement(sql);
            ResultSet set = s.executeQuery();
            while (set.next())
                hasdata = true;
        } catch (SQLException ignored) {
        }
        if (hasdata) {
            try {
                List<String> idlist = new ArrayList<>();
                String sql = "SELECT id FROM SpTbl";
                PreparedStatement s1 = getStatement(sql);
                ResultSet set = s1.executeQuery();
                while (set.next()) {
                    idlist.add(set.getString("id"));
                }
                for (String id : idlist) {
                    if (id != null) {
                        Map<String, String> dataMap = mysqlGetter.getSapling(id);
                        getSpmanager().registerSapling(id, con.getItem(dataMap.get("item")), Integer.parseInt(dataMap.get("head")), Integer.parseInt(dataMap.get("period")));
                    }
                }
            } catch (SQLException ignored) {
            }
        }
    }

    public void startTimer() {
        new BukkitRunnable() {
            long start = System.currentTimeMillis();

            public void run() {
                if (start + 1000 <= System.currentTimeMillis()) {
                    for (Tree tree : getTmanager().getTmap().values())
                        tree.time();
                    start = System.currentTimeMillis();
                }

            }
        }.runTaskTimerAsynchronously(this, 0, 1L);
    }

    private PreparedStatement getStatement(String sql) throws SQLException {
        mysql.checkConnection();
        return mysql.getConnection().prepareStatement(sql);
    }
}
