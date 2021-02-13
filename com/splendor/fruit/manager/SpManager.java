package com.splendor.fruit.manager;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class SpManager {

    private static SpManager instance;
    private final Map<String, Sapling> data;
    private final Map<ItemStack, Sapling> items;

    private SpManager() {
        instance = this;
        data = new HashMap<>();
        items = new HashMap<>();
    }

    public static SpManager getInstance() {
        if (instance == null)
            new SpManager();
        return instance;
    }

    public void registerSapling(String s, ItemStack item) {
        data.put(s, new Sapling(this, s, item));
    }

    public void registerSapling(String id, ItemStack item, int head, int period) {
        data.put(id, new Sapling(this, id, item, head, period));
    }

    public void unregisterSapling(String s) {
        data.remove(s);
    }

    public boolean isSapling(String s) {
        return data.containsKey(s);
    }

    public Sapling getSapling(String s) {
        return data.get(s);
    }

    public Sapling getSapling(ItemStack item) {
        return data.get(item);
    }

    public boolean isSpItem(ItemStack item) {
        return items.containsKey(item);
    }

    public Map<String, Sapling> getData() {
        return data;
    }
}
