package com.cr.reservation.service;

import org.junit.jupiter.api.Test;

public class GinaTest {
    @Test
    public void testSomething() {
        int M = 10, N = 10;

        // Intiliazing the grid.
        int[][] grid = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        // Displaying the grid
        printGrid(grid, "Original Generation");
        nextGeneration2(grid, M, N);
    }


    // Function to print next generation
    private void nextGeneration(int grid[][], int M, int N)
    {
        int[][] future = new int[M][N];

        // Loop through every cell
        for (int l = 1; l < M - 1; l++)
        {
            for (int m = 1; m < N - 1; m++)
            {
                // finding no Of Neighbours that are alive
                int aliveNeighbours = 0;
                for (int i = -1; i <= 1; i++)
                    for (int j = -1; j <= 1; j++)
                        aliveNeighbours += grid[l + i][m + j];

                // The cell needs to be subtracted from
                // its neighbours as it was counted before
                aliveNeighbours -= grid[l][m];

                // Implementing the Rules of Life

                // Cell is lonely and dies
                if ((grid[l][m] == 1) && (aliveNeighbours < 2))
                    future[l][m] = 0;

                    // Cell dies due to over population
                else if ((grid[l][m] == 1) && (aliveNeighbours > 3))
                    future[l][m] = 0;

                    // A new cell is born
                else if ((grid[l][m] == 0) && (aliveNeighbours == 3))
                    future[l][m] = 1;

                    // Remains the same
                else
                    future[l][m] = grid[l][m];
            }
        }

        printGrid(future, "Next Generation");
    }

    private void printGrid(int grid[][], String message) {
        int M = grid[0].length;
        int N = grid.length;
        System.out.println(message);
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (grid[i][j] == 0)
                    System.out.print(".");
                else
                    System.out.print("*");
            }
            System.out.println();
        }
        System.out.println();
    }


    // Function to print next generation
    private void nextGeneration2(int grid[][], int M, int N)
    {
        int[][] future = new int[M][N];

        // Loop through every cell
        for (int x = 0; x < M; x++)
        {
            for (int y = 0; y < N; y++)
            {
                // finding no Of Neighbours that are alive
                int aliveNeighbours = 0;
                if(x > 0 && grid[x-1][y] == 1) {
                    aliveNeighbours++;
                    if(y > 0 && grid[x-1][y-1] == 1) {
                        aliveNeighbours++;
                    }
                    if(y < N -1 && grid[x-1][y+1] == 1) {
                        aliveNeighbours++;
                    }
                }
                if(x < M-1 && grid[x+1][y] == 1) {
                    aliveNeighbours++;
                    if(y > 0 && grid[x+1][y-1] == 1) {
                        aliveNeighbours++;
                    }

                    if(y < N -1 && grid[x+1][y+1] == 1) {
                        aliveNeighbours++;
                    }
                }
                if(y > 0 && grid[x][y-1] == 1) {
                    aliveNeighbours++;
                }

                if(y < N -1 && grid[x][y+1] == 1) {
                    aliveNeighbours++;
                }
                 System.out.println("x:" + x + " y: " + " live total: " + aliveNeighbours);

                // Implementing the Rules of Life

                // Cell is lonely and dies
                if ((grid[x][y] == 1) && (aliveNeighbours < 2))
                    future[x][y] = 0;

                    // Cell dies due to over population
                else if ((grid[x][y] == 1) && (aliveNeighbours > 3))
                    future[x][y] = 0;

                    // A new cell is born
                else if ((grid[x][y] == 0) && (aliveNeighbours == 3))
                    future[x][y] = 1;

                    // Remains the same
                else
                    future[x][y] = grid[x][y];
            }
        }

        printGrid(future, "Next Generation");
    }
}
