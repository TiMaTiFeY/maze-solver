package model;

import java.util.Arrays;

public class Maze {
    private final int height;
    private final int width;
    private Cell[][] map;
    private UniqueCell start;
    private UniqueCell finish;

    enum Cell {
        SPACE(0),
        WALL(1),
        START(2),
        FINISH(3),
        WAY(4);

        private final int code;

        Cell(int code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return String.valueOf(code);
        }
    }

    private Cell getCellByCode(int code) throws IllegalArgumentException {
        for (Cell cell: Cell.values()) {
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
        this.map = new Cell[height][width];
    }

    /**
     * Загрузка лабиринта через массив <code>int</code>
     * @param array матрица лабиринта (0 - пустота, 1 - стена, 3 - старт, 4 - финиш)
     * @throws IllegalArgumentException при неверном размере массива
     */
    public void loadMazeByIntArray(int[][] array) throws IllegalArgumentException{
        int arrHeight = array.length;
        int arrWidth = array[0].length;
        if (arrHeight != height || arrWidth != width) {
            throw new IllegalArgumentException("ARRAY SIZE DOES NOT EQUALS MAZE SIZE");
        }
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                map[y][x] = getCellByCode(array[y][x]);
                if (array[y][x] == Cell.START.code) {
                    start = new UniqueCell(x, y);
                }
                if (array[y][x] == Cell.FINISH.code) {
                    finish = new UniqueCell(x, y);
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

    public Cell[][] getMap() {
        return map;
    }

    public UniqueCell getStart() {
        return start;
    }

    public UniqueCell getFinish() {
        return finish;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < height; y++) {
            sb.append(Arrays.toString(map[y])).append("\n");
        }
        return "Maze{" +
                "height=" + height +
                ", width=" + width +
                ",\n" + sb +
                '}';
    }
}
