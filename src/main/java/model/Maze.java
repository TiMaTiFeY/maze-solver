package model;

public class Maze {
    private final int height;
    private final int width;
    private Cell[][] map;

    enum Cell {
        SPACE,
        WALL
    }

    public Maze(int height, int width) {
        this.height = height;
        this.width = width;
        this.map = new Cell[height][width];
    }


}
