package com.splendor.fruit.manager;

import org.bukkit.Location;
import org.bukkit.Material;

import java.util.UUID;

public class Tree {

    private final TreeManager manager;
    private final UUID owner;
    private final Location middle;
    private final Location sign;
    private UUID id;
    private int timer;
    private State state;
    private String sapling;

    public Tree(TreeManager m, Location loc, Location loc2, UUID owner, String sp) {
        manager = m;
        id = UUID.randomUUID();
        while (manager.isKey(id))
            id = UUID.randomUUID();
        middle = loc;
        sign = loc2;
        this.owner = owner;
        timer = 10;
        state = State.SEED;
    }

    public Tree(TreeManager m, String id, Location loc, Location loc2, UUID owner, int timer, String state, String sp) {
        manager = m;
        this.id = UUID.fromString(id);
        middle = loc;
        sign = loc2;
        this.owner = owner;
        this.timer = timer;
        this.state = getState(state);
        sapling = sp;
    }

    public State getState() {
        return state;
    }

    public UUID getOwner() {
        return owner;
    }

    public Location getMiddle() {
        return middle;
    }

    public Location getSign() {
        return sign;
    }

    public String getSapling() {
        return sapling;
    }

    public void change(State state) {
        switch (state) {
            case SEED:

                this.state = State.PLANT;
                return;
            case PLANT:
                this.state = State.FRUIT_1;
                return;
            case FRUIT_1:
                this.state = State.FRUIT_2;
                return;
            case FRUIT_2:
                this.state = State.FRUIT_3;
                return;
            case FRUIT_3:
                this.state = State.FRUIT_4;
                return;
            default:
                throw new EnumConstantNotPresentException(State.class, state.toString());
        }
    }

    public int getTimer() {
        return timer;
    }

    public void time() {
        timer--;
        if (timer >= 0) {
            update();
        }
    }

    private void update() {
        change(state);
    }

    public UUID getId() {
        return id;
    }

    public void remove() {
        for (int a = -1; a < 2; a++)
            for (int b = 0; b < 8; b++)
                for (int c = -1; c < 2; c++)
                    middle.clone().add(a, b, c).getBlock().setType(Material.AIR);
        sign.getBlock().setType(Material.AIR);
    }

    public TreeManager getManager() {
        return manager;
    }

    private State getState(String s) {
        for (State state : State.values())
            if (state.getName().equals(s))
                return state;
        return null;
    }

    public enum State {

        SEED("seed"), PLANT("plant"), FRUIT_1("fruit1"), FRUIT_2("fruit2"), FRUIT_3("fruit3"), FRUIT_4("fruit4");

        private String s;

        State(String seed) {
            this.s = seed;
        }

        public String getName() {
            return s;
        }
    }
}
