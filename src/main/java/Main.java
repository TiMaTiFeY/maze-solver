import model.Maze;
import model.MazeReader;
import model.MazeSolver;
import model.MazeWriter;

import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        MazeReader reader = new MazeReader();
        Maze maze = reader.readMazeFromFile("src/main/resources/in.txt");
        System.out.println(maze);
        MazeSolver solver = new MazeSolver();
        System.out.println(solver.solveByWave(maze));
    }

    public static void printArray(int[][] d) {
        for (int i = 0; i < d.length; i++) {
            System.out.println(Arrays.toString(d[i]));
        }
    }
}

