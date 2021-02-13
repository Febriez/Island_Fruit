package com.splendor.fruit.manager;

import org.bukkit.inventory.ItemStack;

public class Sapling {

    private final SpManager sm;

    private String id;

    private ItemStack item;
    private int head;
    private int period;

    public Sapling(SpManager manager, String id, ItemStack item) {
        sm = manager;
        this.id = id;
        this.item = item;
    }

    public Sapling(SpManager manager, String id, ItemStack item, int head, int period) {
        sm = manager;
        this.item = item;
        this.period = period;
        this.head = head;
        this.id = id;
    }

    public boolean isMakeable() {
        return item != null && period != 0 && head != 0;
    }

    public int getHead() {
        return head;
    }

    public void setHead(int head) {
        this.head = head;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public SpManager getSm() {
        return sm;
    }

    public String getId() {
        return id;
    }
}
