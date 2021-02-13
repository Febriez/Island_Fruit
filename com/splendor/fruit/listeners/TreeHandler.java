package com.splendor.fruit.listeners;

import com.splendor.fruit.FruitMain;
import com.splendor.fruit.manager.Sapling;
import com.splendor.fruit.manager.SpManager;
import com.splendor.fruit.manager.TreeManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

public class TreeHandler implements Listener {

    private final TreeManager manager = FruitMain.getTmanager();
    private final SpManager spmanager = FruitMain.getSpmanager();

    @EventHandler
    public void Protected(BlockBreakEvent e) {
        for (int a = -2; a < 3; a++)
            for (int b = -2; b < 10; b++)
                for (int c = -2; c < 3; c++)
                    if (manager.isTree(e.getBlock().getLocation().clone().add(a, b, c))) {
                        e.getPlayer().sendMessage("§c나무를 손상시킬 수 없습니다");
                        e.setCancelled(true);
                        return;
                    }
    }

    @EventHandler
    public void RegisterTree(BlockPlaceEvent e) {

        Player p = e.getPlayer();

        Location eloc = e.getBlock().getLocation();
        Location loc = e.getBlock().getLocation().clone();

        loc.add(0, -1, 0);

        if (!spmanager.isSpItem(e.getItemInHand()))
            return;

        Sapling sp = spmanager.getSapling(e.getItemInHand());

        if (e.getBlock().getWorld().getBlockAt(loc).getType() != Material.GRASS)
            return;

        for (int a = -6; a < 7; a++)
            for (int b = -6; b < 7; b++)
                for (int c = -6; c < 7; c++)
                    if (manager.isTree(loc.clone().add(a, c, b))) {
                        p.sendMessage("§c다른 과일나무 근처에 심을수 없습니다");
                        e.setCancelled(true);
                        return;
                    }

        new BukkitRunnable() {
            public void run() {

                Set<Location> locs = new HashSet<>();

                for (int a = -2; a < 2; a++) {
                    locs.add(loc.clone().add(a, 1, -2));
                    locs.add(loc.clone().add(a, 1, 2));
                }

                for (int a = -1; a < 1; a++) {
                    locs.add(loc.clone().add(-2, 1, a));
                    locs.add(loc.clone().add(2, 1, a));
                }

                for (Location l : locs)
                    if (l.getBlock().getType() != Material.STEP)
                        return;

                for (int a = -1; a < 1; a++)
                    for (int b = -1; b < 1; b++)
                        if (loc.clone().add(a, 0, b).getBlock().getType() != Material.GRASS)
                            return;

                manager.registerTree(eloc, eloc.clone().add(0, 0, -3), p.getUniqueId(), sp.getId());
                new BukkitRunnable() {
                    public void run() {
                        Block b = manager.getTree(eloc).getSign().getBlock();
                        b.setType(Material.WALL_SIGN);
                        Sign sign = (Sign) b.getState();
                        sign.setLine(0, "[" + p.getName() + "]");
                        sign.setLine(1, "사과나무");
                        sign.setLine(2, "여기를 우클릭하여");
                        sign.setLine(3, "제거합니다");
                        sign.update();
                    }
                }.runTask(FruitMain.getInstance());
                p.sendMessage("§a나무가 설치 되었습니다");
            }
        }.runTaskAsynchronously(FruitMain.getInstance());
    }
}
