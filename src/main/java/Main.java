import model.Maze;
import model.MazeReader;
import model.MazeWriter;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        MazeReader reader = new MazeReader();
        Maze maze = reader.readMazeFromFile("src/main/resources/in.txt");
        System.out.println(maze);
        MazeWriter writer = new MazeWriter();
        writer.writeMapToFile(maze, "src/main/resources/out.txt");
    }
}

