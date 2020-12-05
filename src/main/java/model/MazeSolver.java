package model;

import java.lang.module.FindException;
import java.util.*;

public class MazeSolver {

    private List<Cell> getNearSpaces(Cell cell, Maze maze) {
        List<Cell> result = new ArrayList<>();
        if (cell.x() > 0 && (maze.getMap()[cell.y()][cell.x() - 1].equals(Maze.TypeCell.SPACE)
                        || maze.getMap()[cell.y()][cell.x() - 1].equals(Maze.TypeCell.START)
                        || maze.getMap()[cell.y()][cell.x() - 1].equals(Maze.TypeCell.FINISH))) {
            result.add(new Cell(cell.x() - 1, cell.y()));
        }
        if (cell.x() < maze.getWidth() - 1 && (maze.getMap()[cell.y()][cell.x() + 1].equals(Maze.TypeCell.SPACE)
                                            || maze.getMap()[cell.y()][cell.x() + 1].equals(Maze.TypeCell.START)
                                            || maze.getMap()[cell.y()][cell.x() + 1].equals(Maze.TypeCell.FINISH))) {
            result.add(new Cell(cell.x() + 1, cell.y()));
        }
        if (cell.y() > 0 && (maze.getMap()[cell.y() - 1][cell.x()].equals(Maze.TypeCell.SPACE)
                          || maze.getMap()[cell.y() - 1][cell.x()].equals(Maze.TypeCell.START)
                          || maze.getMap()[cell.y() - 1][cell.x()].equals(Maze.TypeCell.FINISH))) {
            result.add(new Cell(cell.x(), cell.y() - 1));
        }
        if (cell.y() < maze.getHeight() - 1 && (maze.getMap()[cell.y() + 1][cell.x()].equals(Maze.TypeCell.SPACE)
                                             || maze.getMap()[cell.y() + 1][cell.x()].equals(Maze.TypeCell.START)
                                             || maze.getMap()[cell.y() + 1][cell.x()].equals(Maze.TypeCell.FINISH))) {
            result.add(new Cell(cell.x(), cell.y() + 1));
        }
        return result;
    }

    public Maze solveByWave(Maze maze) {
        //Алгоритм Ли
        Maze m = maze.cloneMaze();
        int[][] d = new int[m.getHeight()][m.getWidth()];
        int markNumber = 1;
        Queue<Cell> queue = new ArrayDeque<>();
        queue.add(m.getStart());
        d[m.getStart().y()][m.getStart().x()] = markNumber++;
        while (d[m.getFinish().y()][m.getFinish().x()] == 0) {
            Cell curr = queue.poll();
            if (curr == null) {
                throw new FindException("MAZE HAS NO SOLUTIONS");
            }
            for (Cell cell : getNearSpaces(curr, maze)) {
                if (d[cell.y()][cell.x()] == 0) {
                    d[cell.y()][cell.x()] = markNumber;
                    queue.add(cell);
                }
            }
            markNumber++;
        }
        Cell curr = m.getFinish().cloneCell();
        while (!curr.equals(m.getStart())) {
            for (Cell cell : getNearSpaces(curr, maze)) {
                if (d[cell.y()][cell.x()] == markNumber - 1) {
                    m.setCell(cell.x(), cell.y(), Maze.TypeCell.WAY);
                    curr = cell;
                    break;
                }
            }
            markNumber--;
        }
        m.setCell(m.getStart().x(), m.getStart().y(), Maze.TypeCell.START);
        return m;
    }

    public void solveByRiverFormation(Maze maze) {
        //https://habr.com/ru/post/162915/
        //TODO
    }

    public void solveByAntColony(Maze maze) {
        //https://habr.com/ru/post/162915/
        //TODO
    }

}
