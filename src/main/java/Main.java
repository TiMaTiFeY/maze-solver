import model.*;
import view.MazePainter;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        MazeGenerator generator = new MazeGenerator();
        MazePainter painter = new MazePainter();
        MazeSolver solver = new MazeSolver();
        painter.saveMazeToImg(
                solver.solveByWave(
                    generator.generateMazeWithLiveSimulation(
                                    1000,
                                    1000,
                                    0.45f,
                                    5,
                                    1,
                                    2
                            )
                ),
                "maze.png"
        );
    }
}

