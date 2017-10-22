package com.shirleyqin.andhigher.Model;

import android.os.AsyncTask;

import com.shirleyqin.andhigher.View.GroundView;
import com.shirleyqin.andhigher.View.WalkingNoteView;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by shirleyqin on 2017-10-04.
 */

public class NoteModel extends Observable {
    private float noteX = 200;
    private float noteY = GroundView.BASELINE;
    public final int width = 100;
    public final int height = 120;

    public static float initSpeed = 15;
    float verticalSpeed = 0;
    public boolean isJumping;

    int[] tiles;

    private ArrayList<Observer> observers;

    public NoteModel() {
        noteY -= height-10;
        isJumping = false;
    }


    public float getNoteX() {
        return noteX;
    }

    public float getNoteY() {
        return noteY;
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


                for (int height : tiles) {
                        if (noteY+110 <= height &&
                                noteY + 110 - verticalSpeed >= height &&
                                verticalSpeed <= 0) {
                            noteY = height - 110;
                            verticalSpeed = 0;
                            setChanged();
                            notifyObservers(null);
                            isJumping = false;
                            this.cancel();
                        }
                    }


                if (noteY < 0) {
                    noteY = 0;
                    verticalSpeed = 0;
                    setChanged();
                    notifyObservers(null);
                } else {
                    noteY -= verticalSpeed ;
                    setChanged();
                    notifyObservers(null);
                }
            }
        };

        if (verticalSpeed == 0) {
            verticalSpeed = initSpeed;
            isJumping = true;
            new Timer().schedule(task, 0, 1000 / 30);
        } else
            verticalSpeed = initSpeed;

    }

    public void pause() {

    }

    public float landOn() {
        return noteY+110;
    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
        setChanged();
        notifyObservers(null);
    }
}
