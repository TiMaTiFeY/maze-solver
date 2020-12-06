package com.timatifey.view;

import com.timatifey.model.Maze;

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

    private static final int SIZE_PIXEL = 20;

    public BufferedImage paintMaze(Maze maze) {
        double scale = 1;
        if (maze.getWidth() * maze.getHeight() < 50) {
            scale = 5;
        } else if (maze.getWidth() * maze.getHeight() < 150) {
            scale = 3;
        }
        final int size_pixel = (int) (scale * SIZE_PIXEL);
        BufferedImage img = new BufferedImage(
                maze.getWidth() * size_pixel,
                maze.getHeight() * size_pixel,
                BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < maze.getHeight(); y++) {
            for (int x = 0; x < maze.getWidth(); x++) {
                for (int pixelX = 0; pixelX < size_pixel; pixelX++) {
                    for (int pixelY = 0; pixelY < size_pixel; pixelY++) {
                        img.setRGB(
                                x * size_pixel + pixelX,
                                y * size_pixel + pixelY,
                                colors.get(maze.getMap()[y][x]).getRGB()
                        );
                    }
                }
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
