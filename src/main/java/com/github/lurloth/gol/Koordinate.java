/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2014.
 */

package com.github.lurloth.gol;

import java.util.Arrays;

public class Koordinate {
    private final int[] point;

    public Koordinate(int[] point) {
        this.point = point;
    }

    public int getX() {
        return point[0];
    }

    public int getY() {
        return point[1];
    }

    public int[] getDimensions() {
        return point;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Koordinate that = (Koordinate) o;

        if (!Arrays.equals(point, that.point)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public String toString() {
        return "Koordinate{" +
                "point=" + Arrays.toString(point) +
                '}';
    }
}
