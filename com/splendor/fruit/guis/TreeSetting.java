package com.splendor.fruit.guis;

import com.splendor.fruit.FruitMain;
import com.splendor.fruit.manager.TreeManager;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class TreeSetting implements Listener {

    private final TreeManager manager = FruitMain.getTmanager();

    private Inventory inv;

    public Inventory getInv() {
        inv = Bukkit.createInventory(null, InventoryType.DROPPER,"묘목 설정");

        return inv;
    }
}
