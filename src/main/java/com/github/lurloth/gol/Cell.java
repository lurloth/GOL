/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2014.
 */

package com.github.lurloth.gol;

import java.util.Arrays;
import java.util.Map;

public class Cell {
    private final Koordinate koordinate;
    private boolean living = false;
    private Cell nextGen = null;

    public Cell(Koordinate koordinate) {
        this.koordinate = koordinate;
    }

    public void setLiving(boolean living) {
        this.living = living;
    }

    public boolean isLiving() {
        return living;
    }

    public Koordinate getKoordinate() {
        return koordinate;
    }

    void calcStatus(Map<Koordinate, Cell> zellen, Map<Koordinate, Cell> nextZellen) {
        if (nextGen != null)
            return;
        int count = countNachbar(koordinate, zellen);

        int x = koordinate.getX();
        int y = koordinate.getY();

        this.nextGen = new Cell(koordinate);
        switch (count) {
            case 2:
                this.nextGen.living = living;
                break;
            case 3:
                this.nextGen.living = true;
                break;
            default:
                this.nextGen.living = false;
                break;
        }
        if (this.nextGen.isLiving())
            nextZellen.put(koordinate, nextGen);
        if (isLiving())
            activateNachbar(koordinate,zellen,nextZellen);
    }

    private void activateNachbar(Koordinate koordinate, Map<Koordinate, Cell> zellen, Map<Koordinate, Cell> nextZellen, int... dimensions) {
        if (dimensions.length >= koordinate.getDimensions().length) {
            if (!Arrays.equals(koordinate.getDimensions(),dimensions)) {
                Cell c= findCell(zellen, dimensions);
                c.calcStatus(zellen,nextZellen);
            }
            return;
        }
        final int dim = koordinate.getDimensions()[dimensions.length];
        for (int d = dim - 1; d <= dim + 1; d++) {
            final int[] newDimensions = Arrays.copyOf(dimensions, dimensions.length + 1);
            newDimensions[newDimensions.length - 1] = d;
            activateNachbar(koordinate, zellen, nextZellen, newDimensions);
        }

    }

    private int countNachbar(Koordinate koordinate, Map<Koordinate, Cell> zellen, int... dimensions) {
        if (dimensions.length >= koordinate.getDimensions().length) {
            if (Arrays.equals(koordinate.getDimensions(),dimensions)) {
                return 0;
            }
            return findCell(zellen, dimensions).isLiving() ? 1 : 0;
        }

        int count = 0;
        final int dim = koordinate.getDimensions()[dimensions.length];
        for (int d = dim - 1; d <= dim + 1; d++) {
            final int[] newDimensions = Arrays.copyOf(dimensions, dimensions.length + 1);
            newDimensions[newDimensions.length - 1] = d;
            count += countNachbar(koordinate, zellen, newDimensions);
            if (count > 3)
                return count;
        }
        return count;
    }

    public static Cell findCell(Map<Koordinate, Cell> zellen, int... point) {
        Koordinate k = new Koordinate(point);
        Cell c = zellen.get(k);
        if (c == null) {
            c = new Cell(k);
        }
        return c;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "koordinate=" + koordinate +
                ", living=" + living +
                '}';
    }
}
