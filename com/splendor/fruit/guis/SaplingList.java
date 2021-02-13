package com.splendor.fruit.guis;

import com.splendor.fruit.FruitMain;
import com.splendor.fruit.manager.SpManager;
import com.splendor.fruit.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class SaplingList implements Listener {

    private final SpManager manager = FruitMain.getSpmanager();

    private Inventory inv;

    public Inventory getInv() {
        inv = Bukkit.createInventory(null, 54, "묘목 목록 1페이지");

        return inv;
    }

    private Inventory getInv(int page) {
        if (page == 1)
            return getInv();
        inv = Bukkit.createInventory(null, 54, "묘목 목록 " + page + "페이지");
        new BukkitRunnable() {
            public void run() {
                List<String> keys = new ArrayList<>(manager.getData().keySet());
                inv.setItem(45, new ItemBuilder(Material.ARROW).setDisplayName("§f이전 페이지").build());
                if (manager.getData().size() > page * 45)
                    inv.setItem(53, new ItemBuilder(Material.ARROW).setDisplayName("§f다음 페이지").build());
                for (int a = (page - 1) * 45 + 1; a < page * 45; a++)
                    inv.setItem(a - ((page - 1) * 45 + 1), new ItemBuilder(manager.getData().get(keys.get(a - ((page - 1) * 45 + 1))).getItem()).addLore("").addLore("§fid: " + keys.get(a - ((page - 1) * 45 + 1))));
            }
        }.runTaskAsynchronously(FruitMain.getInstance());
        return inv;
    }

    @EventHandler
    public void Click(InventoryClickEvent e) {
        if (e.getClickedInventory() == null || !e.getClickedInventory().getTitle().contains("묘목 목록") || e.getCurrentItem() == null)
            return;

        e.setCancelled(true);

        int page = Integer.parseInt(e.getClickedInventory().getTitle().replace("묘목 목록 ", "").replace("페이지", ""));

        switch (e.getSlot()) {
            case 45:
                e.getWhoClicked().openInventory(getInv(page - 1));
                break;
            case 53:
                e.getWhoClicked().openInventory(getInv(page + 1));
                break;
            default:
                break;
        }
    }
}
