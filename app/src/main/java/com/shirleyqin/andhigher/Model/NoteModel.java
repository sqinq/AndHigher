package com.shirleyqin.andhigher.Model;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Pair;

import com.shirleyqin.andhigher.NoteListener;
import com.shirleyqin.andhigher.TileHeightTranslater;
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
    private float noteHeight;
    private float itemHeight;
    private float noteX;
    private float noteY;

    private NoteListener gameInterface;

    public boolean isJumping = false;
    public boolean checkLastTile = false;
    public int jumpCount = 0;
    int points = 0;

    public Tile[]  LastTiles;
    public Tile[]  CurrenTiles;
    public QueueNode<Tile[]> currentTileNode;



    public void setGameInterface(NoteListener gameInterface) {
        this.gameInterface = gameInterface;
    }

    public void setNoteHeight(float noteHeight) {
        this.noteHeight = noteHeight;
    }

    public void setItemHeight(float itemHeight) {
        this.itemHeight = itemHeight;
    }

    public void setInitTiles(QueueNode<Tile[]>  node) {
        currentTileNode = node;
        this.LastTiles = CurrenTiles;
        this.CurrenTiles = node.getValue();
    }

    public void moveForward() {
        currentTileNode = currentTileNode.getNext();
        this.LastTiles = CurrenTiles;
        this.CurrenTiles = currentTileNode.getValue();
        checkLastTile = true;
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

    public void refreshLocation(float x, float y) {
        this.noteX = x;
        this.noteY = y;
    }

    public float getNoteY() {
        return noteY;
    }

    public float getNoteX() {
        return noteX;
    }

    public float landOnHeight(final float Y, float speed, float height) {
        if (checkLastTile) {
            for (Tile t : LastTiles) {
                int h = TileHeightTranslater.levelToHeight(t.getLevel());
                if (Y+height <= h &&
                        Y + height - speed >= h &&
                        speed <= 0) {
                    landOnTile = t.getLevel();
                    return h-height;
                }
            }
        }

        for (Tile t : CurrenTiles) {
            int h = TileHeightTranslater.levelToHeight(t.getLevel());
            if (Y+height <= h &&
                    Y + height - speed >= h &&
                    speed <= 0) {
                landOnTile = t.getLevel();
                return h-height;
            }
        }

        return -1;
    }

    public void fallOffTile() {
        if (!isJumping) {
            boolean hasSameTile = false;
            for (Tile t : CurrenTiles) {
                groundLevel i = t.getLevel();
                if (i == landOn()) {
                    hasSameTile = true;
                    break;
                }
            }
            if (!hasSameTile) {
                checkLastTile = false;
                jump(0);
            }
        }
        checkLastTile = false;
    }

    public void pause() {

    }

    public groundLevel landOn() {
        return landOnTile;
    }

    public void hitItem(int noteLeft, int noteRight, int disToBeginning) {
        if (noteLeft< -disToBeginning) {
            for (Tile t:LastTiles) {
                if (t.hasItem()) {
                    ItemModel item = t.getItem();
                    if (overlap(itemHeight, noteHeight, TileHeightTranslater.levelToHeight(t.getLevel())-itemHeight, noteY)) {
                        item.touched(this);
                        t.removeItem();
                    }
                }
            }
        } else if (noteRight>disToBeginning) {
            for (Tile t:CurrenTiles) {
                if (t.hasItem()) {
                    ItemModel item = t.getItem();
                    if (overlap(itemHeight, noteHeight, TileHeightTranslater.levelToHeight(t.getLevel())-itemHeight, noteY)) {
                        item.touched(this);
                        t.removeItem();
                    }
                }
            }
        }
    }

    private boolean overlap(float heightA, float heightB, float topA, float topB) {
        if (heightA > heightB) {
            return (topA<topB && topB<topA+heightA) || (topA<topB+heightB && topB+heightB<topA+heightA);
        } else {
            return (topB<topA && topA<topB+heightB) || (topB<topA+heightA && topA+heightA<topB+heightB);
        }
    }
}
