package com.shirleyqin.andhigher.Model;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import java.util.Observable;

/**
 * Created by shirleyqin on 2017-10-23.
 */

public abstract class ItemModel extends Observable {
    NoteModel note;

    int imageId;

    public void setNote(NoteModel note) {
        this.note = note;
    }

    public void touched() {
    }

    public int getImageId() {
        return imageId;
    }
}
