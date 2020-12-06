package com.timatifey.model;

import java.io.*;

public class MazeReader {
    /**
     * Считывание лабиринта из файла
     * @param inputFileName путь ко входному файлу лабиринта
     * @return прочитанный лабиринт
     * @throws IOException при ошибке считывания файла
     */
    public Maze readMazeFromFile(String inputFileName) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFileName));
        String[] sizes = bufferedReader.readLine().split(" ");
        int height = Integer.parseInt(sizes[0]);
        int width = Integer.parseInt(sizes[1]);
        int[][] array = new int[height][width];
        for (int y = 0; y < height; y++) {
            String[] line = bufferedReader.readLine().split(" ");
            for (int x = 0; x < width; x++) {
                array[y][x] = Integer.parseInt(line[x]);
            }
        }
        Maze maze = new Maze(height, width);
        maze.loadMazeByIntArray(array);
        return maze;
    }
}
