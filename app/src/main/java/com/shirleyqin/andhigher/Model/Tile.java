package com.shirleyqin.andhigher.Model;

/**
 * Created by shirleyqin on 2017-10-26.
 */

public class Tile {
    groundLevel level;
    ItemModel item;

    public Tile(groundLevel l, ItemModel i) {
        this.level = l;
        this.item = i;
    }

    public ItemModel getItem() {
        return item;
    }

    public groundLevel getLevel() {
        return level;
    }

    public boolean hasItem() {
        return item != null;
    }
}
