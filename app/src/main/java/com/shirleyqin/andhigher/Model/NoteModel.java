package com.shirleyqin.andhigher.Model;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.shirleyqin.andhigher.NoteListener;
import com.shirleyqin.andhigher.View.GroundView;

import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by shirleyqin on 2017-10-04.
 */

public class NoteModel extends Observable {
    private float noteX = 250;
    private float noteY;
    private float width;
    private float height;

    float verticalSpeed = 0;
    public boolean isJumping = false;
    int jumpCount = 0;

    int[] tiles;

    private NoteListener gameInterface;


    public NoteModel(float width, float height) {
        this.width = width;
        this.height = height;
        noteY = GroundView.BASELINE - height+10;
    }

    public void setGameInterface(NoteListener gameInterface) {
        this.gameInterface = gameInterface;
    }

    public float getNoteX() {
        return noteX;
    }

    public float getNoteY() {
        return noteY;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setNoteY(float noteY) {
        this.noteY = noteY;
    }

    public void setNoteX(float noteX) {
        this.noteX = noteX;
    }

    public void setTiles(int [] tiles) {
        this.tiles = tiles;
    }

    public void jump(float initSpeed) {

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                verticalSpeed -= 0.5;

                //check if there is a tile to land on
                for (int height : tiles) {
                        if (noteY+110 <= height &&
                                noteY + 110 - verticalSpeed >= height &&
                                verticalSpeed <= 0) {
                            noteY = height - 110;
                            verticalSpeed = 0;
                            setChanged();
                            notifyObservers(null);
                            isJumping = false;
                            jumpCount = 0;
                            this.cancel();
                        }
                }

                //if the note is jumping out of the screen
                if (noteY < 0) {
                    noteY = 0;
                    verticalSpeed = 0;
                    setChanged();
                    notifyObservers(null);

                //if the note falls off the screen
                } else if (gameInterface.offScreen(noteY)) {
                    Looper.prepare();
                    Handler mHandler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            gameInterface.stopGame();
                        }
                    };
                     mHandler.sendEmptyMessage(1);
                    Looper.loop();
                } else {
                    noteY -= verticalSpeed ;
                    setChanged();
                    notifyObservers(null);
                }
            }
        };

        if (jumpCount == 0) {
            verticalSpeed = initSpeed;
            isJumping = true;
            new Timer().schedule(task, 0, 1000 / 30);
            jumpCount ++;
        } else if (jumpCount == 1) {
            verticalSpeed = initSpeed;
            jumpCount++;
        }

    }

    public void pause() {

    }

    public float landOn() {
        return noteY+110;
    }

    @Override
    public void addObserver(Observer o) {
        super.addObserver(o);
        setChanged();
        notifyObservers(null);
    }
}
