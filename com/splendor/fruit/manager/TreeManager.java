package com.splendor.fruit.manager;

import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TreeManager {

    private static TreeManager instance;

    private Map<UUID, Tree> map;

    private Map<UUID, Tree> Pmap;
    private Map<Location, Tree> Tmap;

    private TreeManager() {
        instance = this;
        map = new HashMap<>();
        Tmap = new HashMap<>();
        Pmap = new HashMap<>();
    }

    public static TreeManager getInstance() {
        if (instance == null)
            new TreeManager();
        return instance;
    }

    public boolean isTree(Location loc) {
        return Tmap.containsKey(loc);
    }

    public void unregisterTree(UUID uuid) {
        Tree tree = Pmap.get(uuid);
        tree.remove();
        map.remove(tree.getId());
        Pmap.remove(uuid);
        Tmap.remove(tree.getMiddle());
    }

    public void registerTree(Location middle, Location sign, UUID owner, String sp) {
        Tree tree = new Tree(this, middle, sign, owner, sp);
        map.put(tree.getId(), tree);
        Pmap.put(owner, tree);
        Tmap.put(middle, tree);
    }

    public void registerTree(String id, Location middle, Location sign, UUID owner, int timer, String state, String sp) {
        Tree tree = new Tree(this, id, middle, sign, owner, timer, state, sp);
        map.put(tree.getId(), tree);
        Pmap.put(owner, tree);
        Tmap.put(middle, tree);
    }

    public boolean isKey(UUID id) {
        return map.containsKey(id);
    }

    public Tree getTree(Location loc) {
        return Tmap.get(loc);
    }

    public Map<Location, Tree> getTmap() {
        return Tmap;
    }
}
