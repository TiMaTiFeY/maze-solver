import model.*;
import view.MazePainter;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        MazeGenerator generator = new MazeGenerator(0.45f, 4, 2, 1);
        MazePainter painter = new MazePainter();
        MazeReader reader = new MazeReader();
        BufferedImage img = painter.paintMaze(
                new MazeSolver().solveByWave(
                        generator.generateMaze(100,100)
                )
        );
    }
}

