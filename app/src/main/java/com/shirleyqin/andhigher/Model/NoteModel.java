package com.shirleyqin.andhigher.Model;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Pair;

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
    private groundLevel landOnTile;

    private NoteListener gameInterface;

    public boolean isJumping = false;
    public boolean checkLastTile = false;
    public int jumpCount = 0;
    int points = 0;

    public Tile[]  LastTiles;
    public Tile[]  CurrenTiles = null;



    public void setGameInterface(NoteListener gameInterface) {
        this.gameInterface = gameInterface;
    }

    public void setTiles(Tile[]  tiles) {
        this.LastTiles = CurrenTiles;
        this.CurrenTiles = tiles;
    }

    public void addPoints(int pointNum) {
        points += pointNum;
    }

    public boolean checkOffScreen(float y) {
        return gameInterface.offScreen(y);
    }

    public void stopGame() {
        gameInterface.stopGame();
    }

    public void jump(final float initSpeed) {

        if (jumpCount == 0 || jumpCount == 1) {
            setChanged();
            notifyObservers(initSpeed);
            jumpCount ++;
        }
    }

    public void jump() {
        if (jumpCount == 0 || jumpCount == 1) {
            setChanged();
            notifyObservers();
            jumpCount ++;
        }
    }


    public void checkHitItem() {

    }

    public float landOnHeight(final float Y, float speed, float height) {
        if (checkLastTile) {
            for (Tile t : LastTiles) {
                int h = t.getLevel().getHeight(GroundView.BASELINE, height);
                if (Y+110 <= h &&
                        Y + 110 - speed >= h &&
                        speed <= 0) {
                    landOnTile = t.getLevel();
                    return h-110;
                }
            }
        }

        for (Tile t : CurrenTiles) {
            int h = t.getLevel().getHeight(GroundView.BASELINE, height);
            if (Y+110 <= h &&
                    Y + 110 - speed >= h &&
                    speed <= 0) {
                landOnTile = t.getLevel();
                return h-110;
            }
        }

        return -1;
    }

    public void pause() {

    }

    public groundLevel landOn() {
        return landOnTile;
    }



}
