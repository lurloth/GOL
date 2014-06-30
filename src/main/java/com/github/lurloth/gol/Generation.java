/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2014.
 */

package com.github.lurloth.gol;

import java.util.HashMap;
import java.util.Map;


public class Generation {

    private final Map<Koordinate, Cell> zellen = new HashMap<>();
    private Generation nextGen;

    public int getZellenSize() {
        return zellen.size();
    }

    public Map<Koordinate, Cell> getZellen() {
        return zellen;
    }

    public void put(boolean living, int... point) {
        Cell c = Cell.findCell(zellen, point);
        if (living) {
            c.setLiving(living);
            zellen.put(c.getKoordinate(), c);
        } else {
            zellen.remove(c.getKoordinate());
        }
    }


    public boolean lives(int... point){
        Cell cell = Cell.findCell(zellen, point);
        return cell.isLiving();
    }

    public Generation getNextGen() {
        if (nextGen == null) {
            nextGen = new Generation();

            for (Cell c : zellen.values()) {
                c.calcStatus(zellen,nextGen.zellen);
            }
        }
        return nextGen;
    }

}
