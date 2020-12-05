package model;

import java.util.*;

public class MazeGenerator {
    private static final Random random = new Random();
    private boolean[][] visitedCells;
    private int countVisited;

    /**
     * Генерация лабиринта по принципу симуляции "Жизнь"
     * source:
     * https://gamedevelopment.tutsplus.com/tutorials/generate-random-cave-levels-using-cellular-automata--gamedev-9664
     * @param height высота лабиринта
     * @param width ширина лабиринта
     * @param chanceToStartAlive шанс "выжить"
     * @param deathLimit лимит смерти
     * @param birthLimit лимит рождения
     * @param numberOfSteps количество шагов симуляции
     * @return сгенерированный лабиринт
     */
    public Maze generateMazeWithLiveSimulation(int height,
                                               int width,
                                               float chanceToStartAlive,
                                               int deathLimit,
                                               int birthLimit,
                                               int numberOfSteps) {
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
            maze = doSimulationStep(maze, deathLimit, birthLimit);
        }
        maze.setStart(new Cell(0,0));
        maze.setFinish(new Cell(height - 5,width - 5));
        return maze;
    }

    private int countAliveNeighbours(Maze maze, int x, int y){
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
                } else if (maze.getMap()[neighbour_y][neighbour_x] == Maze.TypeCell.SPACE) {
                    count = count + 1;
                }
            }
        }
        return count;
    }

    private Maze doSimulationStep(Maze oldMaze, int deathLimit, int birthLimit) {
        Maze newMaze = new Maze(oldMaze.getHeight(), oldMaze.getWidth());
        for (int x = 0; x < oldMaze.getWidth(); x++){
            for (int y = 0; y < oldMaze.getHeight(); y++){
                int nbs = countAliveNeighbours(oldMaze, x, y);
                if (oldMaze.getMap()[y][x] == Maze.TypeCell.SPACE){
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

    /**
     * Алгоритм, основанный на бэктрекинге, позволяющий создавать лабиринты без циклов,
     * имеющие единственный путь между двумя точками. Алгоритм не самый быстрый, довольно требователен к ресурсам,
     * но очень прост в реализации и позволяет создавать ветвистые лабиринты с очень длинными тупиковыми ответвлениями.
     * source: https://habr.com/ru/post/262345/
     * @param height высота лабиринта
     * @param width ширина лабиринта
     * @return сгенерированный лабиринт
     */
    public Maze generateMazeByBacktracking(int height, int width) {
        Maze maze = new Maze(height, width);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if ((y % 2 != 0 && x % 2 != 0) && (y < height - 1 && x < width - 1)) {
                    maze.setCell(x, y, Maze.TypeCell.SPACE);
                } else {
                    maze.setCell(x, y, Maze.TypeCell.WALL);
                }
            }
        }
        visitedCells = new boolean[height][width];
        countVisited = 0;

        Cell currentCell = new Cell(width - 3, height - 3);
        visitedCells[width - 3][height - 3] = true;
        Cell neighbourCell;
        Stack<Cell> stack = new Stack<>();
        int cellNumber = height * width - (height + width - 2) * 2;
        do {
            List<Cell> neighbours = getNeighbours(maze, currentCell, 2);
            if (neighbours.size() != 0) { //если у клетки есть непосещенные соседи
                int randNum = random.nextInt(neighbours.size());
                neighbourCell = neighbours.get(randNum); //выбираем случайного соседа
                stack.push(currentCell); //заносим текущую точку в стек
                removeWall(maze, currentCell, neighbourCell); //убираем стену между текущей и сосендней точками
                currentCell = neighbourCell; //делаем соседнюю точку текущей и отмечаем ее посещенной
                visitedCells[currentCell.y()][currentCell.x()] = true;
            } else if (stack.size() > 0) { //если нет соседей, возвращаемся на предыдущую точку
                currentCell = stack.pop();
            } else { //если нет соседей и точек в стеке, но не все точки посещены, выбираем случайную из непосещенных
                List<Cell> cellListUnvisited = getUnvisitedCells(maze);
                if (cellListUnvisited.size() == 0) {
                    break;
                }
                int randNum = random.nextInt(cellListUnvisited.size());
                currentCell = cellListUnvisited.get(randNum);
            }
        } while (countVisited < cellNumber);
        maze.setStart(new Cell(1, 1));
        maze.setFinish(new Cell(width - 3, height - 3));
        return maze;
    }

    private List<Cell> getNeighbours(Maze maze, Cell curr, int dist) {
        int[] deltaX = new int[] { dist, -dist, 0, 0 };
        int[] deltaY = new int[] { 0, 0, dist, -dist };
        List<Cell> neighboursList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Cell neighCell = new Cell(curr.x() + deltaX[i], curr.y() + deltaY[i]);
            if (neighCell.x() > 0 && neighCell.x() < maze.getWidth() - 1
                    && neighCell.y() > 0 && neighCell.y() < maze.getHeight() - 1
                    && maze.getType(neighCell) != Maze.TypeCell.WALL
                    && !visitedCells[neighCell.y()][neighCell.x()]) {
                neighboursList.add(neighCell);
            }
        }
        return neighboursList;
    }

    private void removeWall(Maze maze, Cell first, Cell second) {
        int xDiff = second.x() - first.x();
        int yDiff = second.y() - first.y();
        int addX, addY;

        addX = (xDiff != 0) ? (xDiff / Math.abs(xDiff)) : 0;
        addY = (yDiff != 0) ? (yDiff / Math.abs(yDiff)) : 0;

        Cell target = new Cell(first.x() + addX, first.y() + addY);

        visitedCells[target.y()][target.x()] = true;
        countVisited++;
        maze.setCell(target.x(), target.y(), Maze.TypeCell.SPACE);
    }

    private List<Cell> getUnvisitedCells(Maze maze) {
        List<Cell> res = new ArrayList<>();
        for (int y = 1; y < maze.getHeight(); y+=2) {
            for (int x = 1; x < maze.getWidth(); x+=2) {
                if (!visitedCells[y][x] && maze.getMap()[y][x] != Maze.TypeCell.WALL) {
                    res.add(new Cell(x, y));
                }
            }
        }
        return res;
    }

}
