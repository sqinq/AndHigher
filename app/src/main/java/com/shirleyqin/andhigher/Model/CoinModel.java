package com.shirleyqin.andhigher.Model;

import com.shirleyqin.andhigher.R;

/**
 * Created by shirleyqin on 2017-10-24.
 */

public class CoinModel extends ItemModel {

    public CoinModel() {
        imageId = R.drawable.coin_gold;
    }

    @Override
    public void touched(NoteModel note) {
        super.touched(note);
        note.addPoints(5);

    }
}
