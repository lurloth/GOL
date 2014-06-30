/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2014.
 */

package com.github.lurloth.gol;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class GameOfLiveTest extends TestCase {
    @Test
    public void testBalken() {
        Generation gol = new Generation();
        gol.put(true, 1, 1);
        gol.put(true, 2, 1);
        gol.put(true, 3, 1);
        Assert.assertEquals(3, gol.getZellenSize());

        Generation golNG = gol.getNextGen();
        assertEquals(true, golNG.lives(2, 0));
        assertEquals(true, golNG.lives(2, 1));
        assertEquals(true, golNG.lives(2, 2));
        assertEquals(false, golNG.lives(1, 1));
        assertEquals(false, golNG.lives(3, 1));
        Assert.assertEquals(3, golNG.getZellenSize());
    }

    @Test
    public void testCross() {
        Generation gol = new Generation();
        gol.put(true, 1, 2);
        gol.put(true, 2, 1);
        gol.put(true, 2, 3);
        gol.put(true, 3, 2);
        Assert.assertEquals(4, gol.getZellenSize());

        Generation golNG = gol.getNextGen();

        assertEquals(true, golNG.lives(1, 2));
        assertEquals(true, golNG.lives(2, 1));
        assertEquals(true, golNG.lives(2, 3));
        assertEquals(true, golNG.lives(3, 2));
        Assert.assertEquals(4, golNG.getZellenSize());
    }

}