package com.shirleyqin.andhigher.View;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.shirleyqin.andhigher.Model.Model;
import com.shirleyqin.andhigher.Model.QueueNode;
import com.shirleyqin.andhigher.Model.NoteModel;
import com.shirleyqin.andhigher.Model.groundLevel;
import com.shirleyqin.andhigher.Model.myLinkedList;
import com.shirleyqin.andhigher.R;


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

    public static float BASELINE;

    myLinkedList<int[]> heights;
    float bitmapX;
    float initX;

    private NoteModel noteModel;
    Model model;

    Timer moveForwardTimer;
    QueueNode<int[]> currentTile;

    public GroundView(Context context, float initX) {
        super(context);
        model = Model.getInstance();
        model.addObserver(this);
        heights = new myLinkedList<>();

        this.bitmapX = initX;
        this.initX = initX;

        Resources r = getResources();
        BASELINE = r.getDimension(R.dimen.baseline);
        tileLength = r.getDimension(R.dimen.tile_length);
        HEIGHT = r.getDimension(R.dimen.tile_height);
        tileThickness = r.getDimension(R.dimen.tile_thickness);

        for (groundLevel[] block: model.tiles) {
            int [] height = new int[block.length];
            int i = 0;
            for (groundLevel tile: block) {
                height[i] = translateHeight(tile);
                ++ i;
            }
            heights.add(height);
        }

        moveForwardTimer = new Timer();
    }

    public void setNoteModel(NoteModel note) {
        this.noteModel = note;
    }

    public NoteModel getNoteModel() {
        return noteModel;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.tile);
        bitmap = Bitmap.createScaledBitmap(bitmap, (int)tileLength, (int)tileThickness, false);

        int blockNum = 0;
        for (QueueNode<int[]> block = heights.getFirst(); block.hasNext(); block = block.getNext()) {
            int [] tiles = block.getValue();
            for (int height: tiles) {
                canvas.drawBitmap(bitmap, bitmapX + tileLength * blockNum, height, paint);
            }
            ++ blockNum;
        }


    }

    public void runBackground() {
        currentTile = heights.getFirst();
        noteModel.setTiles(currentTile.getValue());

        moveForwardTimer.schedule(new TimerTask() {
            int disPassed = 0;

            @Override
            public void run() {

                bitmapX -= 5;
                disPassed += 5;
                if (bitmapX <= -3*tileLength) {
                    model.tiles.remove();
                    model.tiles.remove();
                    model.tiles.remove();
                    model.generateNewTiles();
                    bitmapX = 0;
                }

                postInvalidate();


                if ((disPassed+noteModel.getWidth()-15)%tileLength == 0 && currentTile.hasNext()) {
                    currentTile = currentTile.getNext();
                    noteModel.setTiles(currentTile.getValue());
                }

                if (disPassed % tileLength == 0) {
                    model.addDistance();
                }

                if ((disPassed+noteModel.getWidth()/2-15) % tileLength == 0) {
                    if (!noteModel.isJumping) {
                        boolean hasSameTile = false;
                        for (int i : currentTile.getValue()) {
                            if (i == noteModel.landOn()) {
                                hasSameTile = true;
                                break;
                            }
                        }
                        if (!hasSameTile)
                            noteModel.jump(0);
                    }
                }
            }
        }, 500, 1000/30);
    }

    private int translateHeight(groundLevel g) {
        int height;
        switch (g) {
            case UG:
                height = (int)(BASELINE + HEIGHT);
                break;
            case GD:
                height = (int)BASELINE;
                break;
            case L1:
                height = (int)(BASELINE - HEIGHT);
                break;
            case L2:
                height = (int)(BASELINE - HEIGHT * 2);
                break;
            default:
                height = (int)BASELINE;
        }
        return height;
    }

    @Override
    public void update(Observable observable, Object o) {
        Queue<groundLevel[]> newTiles = (Queue<groundLevel[]>) o;

        for (groundLevel[] block:newTiles) {
            int i=0;
            int [] height = new int[block.length];

            for (groundLevel tile: block) {
                height[i] = translateHeight(tile);
                ++ i;
            }
            heights.removeFirst();
            heights.add(height);
        }
    }

    public void stopGame() {
        moveForwardTimer.cancel();
        model.clearGame();
    }
}
