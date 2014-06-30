/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2014.
 */

package com.github.lurloth.gol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class GameOfLife {
    private static final int HEIGHT = 20;
    private static final int WIDTH = 20;

    private final JCheckBox[] jCheckBoxes = new JCheckBox[HEIGHT * WIDTH];
    private final JPanel jPanel;
    private Generation generation;

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GameOfLife();
            }
        });
    }

    public GameOfLife() {
        generation = new Generation();
        JFrame frame = new JFrame("Game Of Life");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel rootPanel = new JPanel(new GridBagLayout());

        jPanel = new JPanel(new GridLayout(HEIGHT, WIDTH));
        rootPanel.add(jPanel);
        frame.setContentPane(rootPanel);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 1));
        initializeButtons(buttonPanel);

        rootPanel.add(buttonPanel);
        setup();

        // Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    private void initializeButtons(JPanel buttonPanel) {
        JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Generation nextGeneration = new Generation();
                showGeneration(nextGeneration);
                generation = nextGeneration;
            }
        });
        buttonPanel.add(reset);

        JButton envolve = new JButton("Envolve");
        envolve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Generation nextGeneration = generation.getNextGen();
                showGeneration(nextGeneration);
                generation = nextGeneration;
            }
        });
        buttonPanel.add(envolve);
    }

    public void setup() {

        for (int i = 0; i < HEIGHT * WIDTH; i++) {
            MyJCheckBox jCheckBox = new MyJCheckBox();
            jCheckBox.setGridLocation(i);
            jCheckBox.setAction(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    MyJCheckBox source = (MyJCheckBox) e.getSource();

                    int index = source.getGridLocation();
                    int x = index % WIDTH;
                    int y = index / WIDTH;
                    generation.put(source.isSelected(), x, y);
                }
            });
            jCheckBoxes[i] = jCheckBox;
            jPanel.add(jCheckBox);
        }
    }

    private void showGeneration(Generation nextGeneration) {
        List<Koordinate> k1 = new ArrayList<>(generation.getZellen().keySet());
        List<Koordinate> k2 = new ArrayList<>(nextGeneration.getZellen().keySet());

        k1.removeAll(nextGeneration.getZellen().keySet());
        k2.removeAll(generation.getZellen().keySet());

        for (Koordinate koordinate : k1) {
            setCheckbox(false, koordinate.getX(), koordinate.getY());
        }
        for (Koordinate koordinate : k2) {
            setCheckbox(true, koordinate.getX(), koordinate.getY());
        }
    }

    private void setCheckbox(boolean setTo, int x, int y) {
        if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT)
            return;

        int pos = y * WIDTH + x;
        jCheckBoxes[pos].setSelected(setTo);
    }

    private static class MyJCheckBox extends JCheckBox {
        private int gridLocation = 0;

        public MyJCheckBox() {
            super("", false);
        }

        public void setGridLocation(int gridLocation) {
            this.gridLocation = gridLocation;
        }

        public int getGridLocation() {
            return gridLocation;
        }
    }
}
