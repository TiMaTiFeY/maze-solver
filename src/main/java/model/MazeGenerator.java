package model;

import java.util.*;

public class MazeGenerator {
    private static final Random random = new Random();

    private float chanceToStartAlive = 0.45f;
    private int deathLimit = 25;
    private int birthLimit = 2;
    private int numberOfSteps = 5;

    public MazeGenerator() {
    }

    public MazeGenerator(float chanceToStartAlive, int deathLimit, int birthLimit, int numberOfSteps) {
        this.chanceToStartAlive = chanceToStartAlive;
        this.deathLimit = deathLimit;
        this.birthLimit = birthLimit;
        this.numberOfSteps = numberOfSteps;
    }

    public Maze generateMaze(int height, int width) {
        Maze maze = new Maze(height, width);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (random.nextFloat() < chanceToStartAlive){
                    maze.setCell(x, y, Maze.TypeCell.SPACE);
                } else {
                    maze.setCell(x, y, Maze.TypeCell.WALL);
                }
            }
        }
        for (int i = 0; i < numberOfSteps; i++){
            maze = doSimulationStep(maze);
        }
        maze.setStart(new Cell(0,0));
        maze.setFinish(new Cell(height - 5,width - 5));
        return maze;
    }

    public int countAliveNeighbours(Maze maze, int x, int y){
        int count = 0;
        for (int i = -1; i < 2; i++){
            for (int j = -1; j < 2; j++){
                int neighbour_x = x + i;
                int neighbour_y = y + j;
                if (i == 0 && j == 0) {
                } else if (neighbour_x < 0
                        || neighbour_y < 0
                        || neighbour_x >= maze.getWidth()
                        || neighbour_y >= maze.getHeight()) {
                    count = count + 1;
                } else if (maze.getMap()[neighbour_y][neighbour_x] == Maze.TypeCell.SPACE){
                    count = count + 1;
                }
            }
        }
        return count;
    }

    public Maze doSimulationStep(Maze oldMaze) {
        Maze newMaze = new Maze(oldMaze.getHeight(), oldMaze.getWidth());
        for (int x = 0; x < oldMaze.getWidth(); x++){
            for (int y = 0; y < oldMaze.getHeight(); y++){
                int nbs = countAliveNeighbours(oldMaze, x, y);
                if(oldMaze.getMap()[y][x] == Maze.TypeCell.SPACE){
                    if (nbs < deathLimit){
                        newMaze.setCell(x, y, Maze.TypeCell.WALL);
                    }
                    else{
                        newMaze.setCell(x, y, Maze.TypeCell.SPACE);
                    }
                }
                else {
                    if (nbs > birthLimit){
                        newMaze.setCell(x, y, Maze.TypeCell.SPACE);
                    }
                    else{
                        newMaze.setCell(x, y, Maze.TypeCell.WALL);
                    }
                }
            }
        }
        return newMaze;
    }

}
