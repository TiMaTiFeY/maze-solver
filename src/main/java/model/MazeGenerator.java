package model;

import java.util.Random;
import java.util.Stack;

public class MazeGenerator {
    private static Random random = new Random();

   /* public Maze generateMaze(int height, int width) {
        Maze maze = new Maze(height, width);
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                if ((y % 2 != 0  && x % 2 != 0) && (y < height - 1 && x < width - 1)) {
                    maze.setCell(x, y, Maze.TypeCell.SPACE);
                } else {
                    maze.setCell(x, y, Maze.TypeCell.WALL);
                }
            }
        }

        Cell startCell = new Cell(1, 1);
        Cell currentCell = startCell;
        Cell neighbourCell;
        Stack<Cell> stack = new Stack<>();
        do {
            cellString Neighbours = getNeighbours(width, height, maze, startPoint, 2);
            if(Neighbours.size != 0){ //если у клетки есть непосещенные соседи
                randNum  = random.nextInt(Neighbours.size-1);
                neighbourCell = cellStringNeighbours.cells[randNum]; //выбираем случайного соседа
                stack.push(d.startPoint); //заносим текущую точку в стек
                maze = removeWall(currentCell, neighbourCell, maze); //убираем стену между текущей и сосендней точками
                currentCell = neighbourCell; //делаем соседнюю точку текущей и отмечаем ее посещенной
                maze = setMode(d.startPoint, d.maze, VISITED);
                free(cellStringNeighbours.cells);
            }
            else if(stackSize > 0){ //если нет соседей, возвращаемся на предыдущую точку
                startPoint = pop();
            }
            else{ //если нет соседей и точек в стеке, но не все точки посещены, выбираем случайную из непосещенных
                cellString cellStringUnvisited = getUnvisitedCells(width, height, maze);
                randNum = randomRange(0, cellStringUnvisited.size-1);
                currentCell = cellStringUnvisited.cells[randNum];
                free(cellStringUnvisited.cells);
            }
            while(unvisitedCount() > 0);

        return maze;
    }*/
}
