package com.shirleyqin.andhigher.View;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Pair;
import android.view.View;

import com.shirleyqin.andhigher.Model.ItemModel;
import com.shirleyqin.andhigher.Model.Model;
import com.shirleyqin.andhigher.Model.QueueNode;
import com.shirleyqin.andhigher.Model.NoteModel;
import com.shirleyqin.andhigher.Model.Tile;
import com.shirleyqin.andhigher.Model.groundLevel;
import com.shirleyqin.andhigher.Model.myLinkedList;
import com.shirleyqin.andhigher.R;
import com.shirleyqin.andhigher.TileHeightTranslater;


import java.lang.reflect.Array;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by shirleyqin on 2017-10-05.
 */


public class GroundView extends View implements Observer {
    private float tileLength;
    private float HEIGHT;
    private float tileThickness;
    private float speed;

    float noteWidth;

    public static float BASELINE;
    float bitmapX;
    int itemEdge;

    private Model model;
    private NoteModel noteModel;

    Timer moveForwardTimer;

    public GroundView(Context context, float initX) {
        super(context);

        model = Model.getInstance();
        model.addObserver(this);

        this.bitmapX = initX;
        this.itemEdge = (int)getResources().getDimension(R.dimen.item_height);

        Resources r = getResources();
        BASELINE = r.getDimension(R.dimen.baseline);
        tileLength = r.getDimension(R.dimen.tile_length);
        HEIGHT = r.getDimension(R.dimen.tile_height);
        tileThickness = r.getDimension(R.dimen.tile_thickness);
        speed = r.getDimension(R.dimen.hori_normal_speed);
        noteWidth = r.getDimension(R.dimen.item_width);


        moveForwardTimer = new Timer();
    }

    public void setNoteModel(NoteModel note) {
        this.noteModel = note;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Resources r = getResources();
        Paint paint = new Paint();
        Bitmap tileImage = BitmapFactory.decodeResource(r, R.drawable.tile);
        tileImage = Bitmap.createScaledBitmap(tileImage, (int)tileLength, (int)tileThickness, false);

        Bitmap CoinImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.coin_gold);
        CoinImage = Bitmap.createScaledBitmap(CoinImage, itemEdge,
                itemEdge, false);
        int disToBeginning = (int) (tileLength-itemEdge)/2;

        int blockNum = 0;
        for (QueueNode<Tile[]> block = model.tiles.getFirst(); block.hasNext(); block = block.getNext()) {
            Tile [] tiles = block.getValue();
            for (Tile t: tiles) {
                int height = TileHeightTranslater.levelToHeight(t.getLevel());
                canvas.drawBitmap(tileImage, bitmapX + tileLength * blockNum, height, paint);

                if (t.hasItem()) {
                    canvas.drawBitmap(CoinImage, bitmapX + tileLength * blockNum + disToBeginning, height - itemEdge, paint);
                }
            }
            ++ blockNum;
        }


    }

    public void runBackground() {
        noteModel.setInitTiles(model.tiles.getFirst());

        moveForwardTimer.schedule(new TimerTask() {
            int disPassed = 0;

            @Override
            public void run() {

                bitmapX -= speed;
                disPassed += speed;
                if (bitmapX <= -3*tileLength) {
                    model.generateNewTiles();
                    bitmapX = 0;
                }

                postInvalidate();


                if ((disPassed+noteWidth)%tileLength < speed) {
                    noteModel.moveForward();
                }

                if (disPassed % tileLength < speed) {
                    model.addDistance();
                    noteModel.fallOffTile();
                }

                if ((disPassed+noteWidth/2) % tileLength < speed) {
                }

                int rightToEdge = (int)((disPassed+noteWidth)%tileLength);
                int leftToEdge = rightToEdge - (int)noteWidth;

                noteModel.hitItem(leftToEdge, rightToEdge, (int) (tileLength-itemEdge)/2);
            }
        }, 500, 1000/30);
    }



    @Override
    public void update(Observable observable, Object o) {
    }

    public void stopGame() {
        moveForwardTimer.cancel();
        model.clearGame();
    }
}
