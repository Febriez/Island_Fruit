package com.splendor.fruit.guis;

import com.splendor.fruit.FruitMain;
import com.splendor.fruit.manager.TreeManager;
import com.splendor.fruit.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class TreeRemover implements Listener {

    private final TreeManager manager = FruitMain.getTmanager();

    private Inventory inv;

    public Inventory getInv() {
        inv = Bukkit.createInventory(null, InventoryType.HOPPER, "정말 제거 하시겠습니까?");
        inv.setItem(1, new ItemBuilder(Material.STAINED_GLASS_PANE).setData((short) 5).setDisplayName("§a예").hideATTRIBUTES(true).build());
        inv.setItem(3, new ItemBuilder(Material.STAINED_GLASS_PANE).setData((short) 14).setDisplayName("§c아니오").hideATTRIBUTES(true).build());

        for (int a = 0; a < 5; a++)
            if (inv.getItem(a) == null)
                inv.setItem(a, new ItemBuilder(Material.STAINED_GLASS_PANE).setDisplayName("§f").hideATTRIBUTES(true).build());

        return inv;
    }

    @EventHandler
    public void Event(InventoryClickEvent e) {

        if (e.getClickedInventory() == null || !e.getClickedInventory().getTitle().equals("정말 제거 하시겠습니까?"))
            return;

        e.setCancelled(true);

        Player p = (Player) e.getWhoClicked();

        switch (e.getSlot()) {
            case 1:
                manager.unregisterTree(p.getUniqueId());
                p.closeInventory();
                p.sendMessage("§a제거되었습니다");
                break;
            case 3:
                p.closeInventory();
                p.sendMessage("§c취소되었습니다");
                break;
        }
    }

}
