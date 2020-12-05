package view;

import model.Maze;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class MazePainter {
    private final Map<Maze.TypeCell, Color> colors = Map.ofEntries(
            Map.entry(Maze.TypeCell.SPACE, Color.WHITE),
            Map.entry(Maze.TypeCell.WALL, Color.BLACK),
            Map.entry(Maze.TypeCell.START, Color.GREEN),
            Map.entry(Maze.TypeCell.FINISH, Color.RED),
            Map.entry(Maze.TypeCell.WAY, Color.MAGENTA)
    );

    public BufferedImage paintMaze(Maze maze) {
        BufferedImage img = new BufferedImage(maze.getWidth(), maze.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < maze.getHeight(); y++) {
            for (int x = 0; x < maze.getWidth(); x++) {
                img.setRGB(x, y, colors.get(maze.getMap()[y][x]).getRGB());
            }
        }
        return img;
    }

    public void saveMazeToImg(Maze maze, String outputFileName) throws IOException {
        BufferedImage img = paintMaze(maze);
        File outputFile = new File(outputFileName);
        ImageIO.write(img, "png", outputFile);
    }
}
