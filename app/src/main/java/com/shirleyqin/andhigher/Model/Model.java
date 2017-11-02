package com.shirleyqin.andhigher.Model;

import android.util.Pair;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;
import java.util.TimerTask;

/**
 * Created by shirleyqin on 2017-10-05.
 */

public class Model extends Observable {

    public int difficulity = 1;
    public int distance = 0;

    public myLinkedList<Tile[]> tiles;

    private static Model instance;
    private NoteModel noteModel;

    QueueNode<Tile[]> currentTile;

    private Model() {
        tiles = new myLinkedList<>();
        for (int i=0; i<5; i++)
            generateTiles();
    }

    public static synchronized Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }

    public void setNoteModel(NoteModel noteModel) {
        this.noteModel = noteModel;
    }

    public void generateTiles() {
        int tileStyle = (int)(Math.random()*difficulity);
        myLinkedList<Tile[]> newTiles = new myLinkedList<>();

        System.out.println("dif"+tileStyle);
        switch (tileStyle) {
            case 0:
                newTiles.add(new Tile[]{new Tile(groundLevel.GD, null)});
                newTiles.add(new Tile[]{new Tile(groundLevel.GD, new CoinModel())});
                newTiles.add(new Tile[]{new Tile(groundLevel.GD, null)});
                break;
            case 1:
                newTiles.add(new Tile[]{new Tile(groundLevel.GD, null)});
                newTiles.add(new Tile[]{new Tile(groundLevel.GD, null), new Tile(groundLevel.L1, new CoinModel())});
                newTiles.add(new Tile[]{new Tile(groundLevel.GD, null)});
                break;
            case 2:
                newTiles.add(new Tile[]{new Tile(groundLevel.GD, null)});
                newTiles.add(new Tile[]{new Tile(groundLevel.L1, null)});
                newTiles.add(new Tile[]{new Tile(groundLevel.GD, null)});
                break;
            case 3:
                newTiles.add(new Tile[]{new Tile(groundLevel.GD, null)});
                newTiles.add(new Tile[]{});
                newTiles.add(new Tile[]{new Tile(groundLevel.GD, null)});
                break;
            case 4:
                newTiles.add(new Tile[]{new Tile(groundLevel.GD, null)});
                newTiles.add(new Tile[]{});
                newTiles.add(new Tile[]{});
                break;
            case 5:
                newTiles.add(new Tile[]{new Tile(groundLevel.UG, null)});
                newTiles.add(new Tile[]{});
                newTiles.add(new Tile[]{new Tile(groundLevel.GD, null)});
                break;
            case 6:
                newTiles.add(new Tile[]{new Tile(groundLevel.GD, null)});
                newTiles.add(new Tile[]{new Tile(groundLevel.L1, null)});
                newTiles.add(new Tile[]{new Tile(groundLevel.L1, null)});
                break;
            case 7:
                newTiles.add(new Tile[]{new Tile(groundLevel.UG, null)});
                newTiles.add(new Tile[]{});
                newTiles.add(new Tile[]{new Tile(groundLevel.UG, null)});
                break;
            case 8:
                newTiles.add(new Tile[]{new Tile(groundLevel.GD, null)});
                newTiles.add(new Tile[]{});
                newTiles.add(new Tile[]{new Tile(groundLevel.GD, null)});
                break;
            case 9:
                newTiles.add(new Tile[]{new Tile(groundLevel.GD, null)});
                newTiles.add(new Tile[]{});
                newTiles.add(new Tile[]{new Tile(groundLevel.GD, null)});
                break;
            default:
                newTiles.add(new Tile[]{new Tile(groundLevel.GD, null), new Tile(groundLevel.L2, null)});
                newTiles.add(new Tile[]{new Tile(groundLevel.UG, null)});
                newTiles.add(new Tile[]{new Tile(groundLevel.UG, null)});
                break;

        }

        tiles.addAll(newTiles);
    }

    public void generateNewTiles() {
        tiles.removeFirst();
        tiles.removeFirst();
        tiles.removeFirst();
        generateTiles();
    }

    public void moveForward() {
        currentTile = currentTile.getNext();
        noteModel.setTiles(currentTile.getValue());
        noteModel.checkLastTile = true;
    }

    public void addDistance() {
        ++ distance;
        if (distance == (difficulity+1)*(difficulity+1)) {
            ++difficulity;
            System.out.println(difficulity);
        }
    }

    public void fallOffTile() {
        if (!noteModel.isJumping) {
            boolean hasSameTile = false;
            for (Tile t : currentTile.getValue()) {
                groundLevel i = t.getLevel();
                if (i == noteModel.landOn()) {
                    hasSameTile = true;
                    break;
                }
            }
            if (!hasSameTile) {
                noteModel.checkLastTile = false;
                noteModel.jump(0);
            }
        }
        noteModel.checkLastTile = false;
    }

    public void prepare() {
        currentTile = tiles.getFirst();
        noteModel.setTiles(currentTile.getValue());
    }

    public void clearGame() {
        instance = null;
    }

}
