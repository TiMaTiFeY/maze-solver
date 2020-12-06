package com.timatifey.model;

import java.util.Arrays;

public class Maze {
    private final int height;
    private final int width;
    private TypeCell[][] map;
    private Cell start;
    private Cell finish;

    public enum TypeCell {
        SPACE(0),
        WALL(1),
        START(2),
        FINISH(3),
        WAY(4);

        private final int code;

        TypeCell(int code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return String.valueOf(code);
        }

        public int getCode() {
            return code;
        }
    }

    public static TypeCell getCellByCode(int code) throws IllegalArgumentException {
        for (TypeCell cell: TypeCell.values()) {
            if (cell.code == code) {
                return cell;
            }
        }
        System.out.println(code);
        throw new IllegalArgumentException("UNDEFINED CODE FOR MAZE'S CELL");
    }

    public Maze(int height, int width) {
        this.height = height;
        this.width = width;
        this.map = new TypeCell[height][width];
    }

    public Maze(int height, int width, TypeCell[][] map) {
        this.height = height;
        this.width = width;
        this.map = map;
    }

    public Maze(int height, int width, TypeCell[][] map, Cell start, Cell finish) {
        this.height = height;
        this.width = width;
        this.map = map;
        this.start = start;
        this.finish = finish;
    }

    /**
     * Загрузка лабиринта через массив <code>int</code>
     * @param array матрица лабиринта (0 - пустота, 1 - стена, 2 - старт, 3 - финиш)
     * @throws IllegalArgumentException при неверном размере массива
     */
    public void loadMazeByIntArray(int[][] array) throws IllegalArgumentException{
        int arrHeight = array.length;
        int arrWidth = array[0].length;
        if (arrHeight != height || arrWidth != width) {
            throw new IllegalArgumentException("ARRAY SIZE DOES NOT EQUALS MAZE SIZE");
        }
        boolean startWasFound = false;
        boolean finishWasFound = false;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                map[y][x] = getCellByCode(array[y][x]);
                if (array[y][x] == TypeCell.START.code) {
                    if (!startWasFound) {
                        start = new Cell(x, y);
                        startWasFound = true;
                    } else {
                        throw new IllegalArgumentException("MORE THAN 1 START CELL");
                    }
                }
                if (array[y][x] == TypeCell.FINISH.code) {
                    if (!finishWasFound) {
                        finish = new Cell(x, y);
                        finishWasFound = true;
                    } else {
                        throw new IllegalArgumentException("MORE THAN 1 FINISH CELL");
                    }
                }
            }
        }
    }

    /**
     * Лабиринт в виде матрицы <code>int</code>
     * @return матрицу лабиринта
     */
    public int[][] getMazeByIntArray() {
        int[][] array = new int[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                array[y][x] = map[y][x].code;
            }
        }
        return array;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public TypeCell[][] getMap() {
        return map;
    }

    public TypeCell getType(Cell cell) {
        return map[cell.y()][cell.x()];
    }

    public void setCell(int x, int y, TypeCell cell) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new IllegalArgumentException("WRONG CORDS");
        }
        this.map[y][x] = cell;
    }

    public Cell getStart() {
        return start;
    }

    public Cell getFinish() {
        return finish;
    }

    public void setStart(Cell start) {
        if (this.start != null) {
            map[this.start.y()][this.start.x()] = TypeCell.SPACE;
        }
        map[start.y()][start.x()] = TypeCell.START;
        this.start = start;
    }

    public void setFinish(Cell finish) {
        if (this.finish != null) {
            map[this.finish.y()][this.finish.x()] = TypeCell.SPACE;
        }
        map[finish.y()][finish.x()] = TypeCell.FINISH;
        this.finish = finish;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\n");
        for (int y = 0; y < height; y++) {
            sb.append(Arrays.toString(map[y])).append("\n");
        }
        return "Maze{" +
                "height=" + height +
                ", width=" + width +
                ",\n" +
                "start=" + start +
                ", finish=" + finish +
                sb +
                '}';
    }

    public Maze cloneMaze() {
        TypeCell[][] newMap = new TypeCell[height][width];
        for (int y = 0; y < height; y++)
            System.arraycopy(map[y], 0, newMap[y], 0, width);
        return new Maze(height, width, newMap, start.cloneCell(), finish.cloneCell());
    }

}
