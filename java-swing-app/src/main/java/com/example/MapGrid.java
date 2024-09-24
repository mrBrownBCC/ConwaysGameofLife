package com.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MapGrid extends JPanel implements ActionListener {
    private int rows;
    private int cols;
    private int cellSize;
    private int[][] grid;  // 2D array representing the grid state
    private Timer timer;   // Timer to control repainting

    public MapGrid(int rows, int cols, int cellSize) {
        this.rows = rows;
        this.cols = cols;
        this.cellSize = cellSize;
        this.grid = new int[rows][cols];  // Initialize the grid with default state 0 (empty)
        
        setPreferredSize(new Dimension(cols * cellSize, rows * cellSize));

        // Set up a Timer to repaint 30 times per second (33ms interval)
      //  timer = new Timer(33, this);
        //timer.start();  // Start the Timer

        // Mouse click listener to modify grid state on click
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX() / cellSize;
                int y = e.getY() / cellSize;

                // Toggle the state of the clicked cell (cycle through 0, 1, 2)
                if (x >= 0 && x < cols && y >= 0 && y < rows) {
                    if (grid[y][x] == 1) {
                        grid[y][x] = 2;  // Wall -> Red
                    }
                }
                
                System.out.println("Mouse click detected");
            }
        });

        // Set edges to walls (state 1)
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (row == 0 || row == rows - 1 || col == 0 || col == cols - 1 || col == 3) {
                    grid[row][col] = 1;  // Set edges to wall (brown)
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        System.out.println("attempting repaint");
        // Loop through each cell and paint based on its state
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int r = (int)(Math.random() * 256);  // Random red value
                int gColor = (int)(Math.random() * 256);  // Random green value
                int b = (int)(Math.random() * 256);  // Random blue value
                
                g.setColor(new Color(r, gColor, b));
                /*if (grid[row][col] == 0) {
                    // Empty (green)
                    g.setColor(Color.GREEN);
                } else if (grid[row][col] == 1) {
                    // Wall (brown)
                    g.setColor(new Color(139, 69, 19)); // Brown color
                } else if (grid[row][col] == 2) {
                    // Clicked (red)
                    g.setColor(Color.RED);
                }*/

                // Draw the cell
                g.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);

                // Draw grid lines (optional)
                g.setColor(Color.BLACK);
                g.drawRect(col * cellSize, row * cellSize, cellSize, cellSize);
            }
        }
    }
    public void triggerRepaint() {
        repaint();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // This method is called by the Timer every 33ms
        repaint();  // Repaint the grid at each frame
    }
}