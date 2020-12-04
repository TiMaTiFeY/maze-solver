package model;

import java.io.*;

public class MazeWriter {
    /**
     * Считывание лабиринта из файла
     * @param maze лабиринт
     * @param outputFileName путь ко выходному файлу лабиринта
     * @throws IOException при ошибке записи файла
     */
    public void writeMapToFile(Maze maze, String outputFileName) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFileName));
        bufferedWriter.write(String.valueOf(maze.getHeight()) + " ");
        bufferedWriter.write(String.valueOf(maze.getWidth()) + "\n");

        int[][] array = maze.getMazeByIntArray();
        for (int y = 0; y < maze.getHeight(); y++) {
            for (int x = 0; x < maze.getWidth(); x++) {
                bufferedWriter.write(array[y][x] + " ");
            }
            bufferedWriter.write("\n");
        }
        bufferedWriter.close();
    }
}
