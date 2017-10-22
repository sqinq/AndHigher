package com.shirleyqin.andhigher.Model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;

/**
 * Created by shirleyqin on 2017-10-05.
 */

public class Model extends Observable {

    public int difficulity = 1;
    public int distance = 0;

    public Queue<groundLevel[]> tiles;

    public Model() {
        tiles = new LinkedList<>();
        for (int i=0; i<6; i++)
            generateTiles();
    }

    public Queue<groundLevel[]> generateTiles() {
        int tileStyle = (int)(Math.random()*difficulity);
        Queue<groundLevel[]> newTiles = new LinkedList<>();

        System.out.println("dif"+tileStyle);
        switch (tileStyle) {
            case 0:
                newTiles.add(new groundLevel[]{groundLevel.GD});
                newTiles.add(new groundLevel[]{groundLevel.GD});
                newTiles.add(new groundLevel[]{groundLevel.GD});
                break;
            case 1:
                newTiles.add(new groundLevel[]{groundLevel.GD});
                newTiles.add(new groundLevel[]{groundLevel.GD, groundLevel.L1});
                newTiles.add(new groundLevel[]{groundLevel.GD});
                break;
            case 2:
                newTiles.add(new groundLevel[]{groundLevel.GD});
                newTiles.add(new groundLevel[]{groundLevel.L1});
                newTiles.add(new groundLevel[]{groundLevel.GD});
                break;
            case 3:
                newTiles.add(new groundLevel[]{groundLevel.GD});
                newTiles.add(new groundLevel[]{});
                newTiles.add(new groundLevel[]{groundLevel.GD});
                break;
            case 4:
                newTiles.add(new groundLevel[]{groundLevel.GD});
                newTiles.add(new groundLevel[]{});
                newTiles.add(new groundLevel[]{});
                break;
            case 5:
                newTiles.add(new groundLevel[]{groundLevel.UG});
                newTiles.add(new groundLevel[]{});
                newTiles.add(new groundLevel[]{groundLevel.GD});
                break;
            case 6:
                newTiles.add(new groundLevel[]{groundLevel.GD});
                newTiles.add(new groundLevel[]{groundLevel.L1});
                newTiles.add(new groundLevel[]{groundLevel.L1});
                break;
            case 7:
                newTiles.add(new groundLevel[]{groundLevel.UG});
                newTiles.add(new groundLevel[]{});
                newTiles.add(new groundLevel[]{groundLevel.UG});
                break;
            case 8:
                newTiles.add(new groundLevel[]{groundLevel.GD});
                newTiles.add(new groundLevel[]{});
                newTiles.add(new groundLevel[]{groundLevel.GD});
                break;
            case 9:
                newTiles.add(new groundLevel[]{groundLevel.GD});
                newTiles.add(new groundLevel[]{});
                newTiles.add(new groundLevel[]{groundLevel.GD});
                break;
            default:
                newTiles.add(new groundLevel[]{groundLevel.GD, groundLevel.L2});
                newTiles.add(new groundLevel[]{groundLevel.UG});
                newTiles.add(new groundLevel[]{groundLevel.UG});
                break;

        }

        tiles.addAll(newTiles);
        return newTiles;
    }

    public void generateNewTiles() {
        Queue<groundLevel[]> newTiles = generateTiles();

        setChanged();
        notifyObservers(newTiles);
    }

    public void addDistance() {

        ++ distance;
        if (distance == (difficulity+1)*(difficulity+1)) {
            ++difficulity;
            System.out.println(difficulity);
        }
    }

}
