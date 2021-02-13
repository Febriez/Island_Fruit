package com.splendor.fruit.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder extends ItemStack {

    private Material material;
    private ItemMeta itemmeta;

    private List<String> lore;

    public ItemBuilder(Material material) {
        this.material = material;
        this.setType(material);
        this.itemmeta = this.getItemMeta();
        this.setAmount(1);
        this.lore = new ArrayList<>();
    }

    public ItemBuilder(ItemStack item) {
        this.material = item.getType();
        this.setType(material);
        this.itemmeta = item.getItemMeta();
        this.setItemMeta(itemmeta);
        this.setAmount(item.getAmount());
        this.lore = item.getItemMeta().hasLore() ? item.getLore() : new ArrayList<>();
    }

    public ItemBuilder setDisplayName(String s) {
        this.itemmeta.setDisplayName(s);
        return this;
    }

    public ItemBuilder setData(short s) {
        this.setDurability(s);
        return this;
    }

    public ItemBuilder setAmounts(int amount) {
        this.setAmount(amount);
        return this;
    }

    public ItemBuilder addLore(String s) {
        this.lore.add(s);
        return this;
    }

    public ItemBuilder setLores(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchant, int a) {
        this.itemmeta.addEnchant(enchant, a, false);
        return this;
    }

    public ItemBuilder hideATTRIBUTES(boolean bol) {
        if (bol)
            this.itemmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        return this;
    }

    public ItemBuilder hideENCHANTS(boolean bol) {
        if (bol)
            this.itemmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public ItemBuilder build() {
        this.itemmeta.setLore(this.lore);
        this.setItemMeta(itemmeta);
        return this;
    }
}
