package com.splendor.fruit.listeners;

import com.splendor.fruit.FruitMain;
import com.splendor.fruit.guis.TreeRemover;
import com.splendor.fruit.manager.Tree;
import com.splendor.fruit.manager.TreeManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.StructureGrowEvent;

public class TreeSet implements Listener {

    private final TreeManager manager = FruitMain.getTmanager();

    private final TreeRemover gui = new TreeRemover();

    @EventHandler
    public void Tree(StructureGrowEvent e) {
        if (manager.isTree(e.getLocation())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void UnregisterTree(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;

        Location loc = e.getClickedBlock().getLocation();
        Player p = e.getPlayer();

        if (!manager.isTree(loc.clone().add(0, 0, 3)))
            return;

        Tree tree = manager.getTree(loc.clone().add(0, 0, 3));

        if (!tree.getOwner().toString().equals(p.getUniqueId().toString()))
            return;

        if (!tree.getSign().equals(loc))
            return;

        p.openInventory(gui.getInv());
    }

}
