import java.util.Random;

public class Board {
    private Cell [][] cells;

    public Board(int boardSize, int mineCount) {
        initializeCells(boardSize);
        generateMines(mineCount);
        setSurroundingMines();
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    private void initializeCells(int boardSize) {
        cells = new Cell[boardSize][boardSize];
        for(int row = 0; row < boardSize; row++){
            for(int col = 0; col < boardSize; col++){
                cells[row][col] = new Cell();
            }
        }
    }

    private void generateMines(int mineCount) {
        Random random = new Random();
        int minesPlaced = 0;
        while (minesPlaced < mineCount) {
            int x = random.nextInt(cells.length);
            int y = random.nextInt(cells.length);
            if (!cells[x][y].isMine()) {
                cells[x][y].setMine(true);
                minesPlaced++;
            }
        }
    }

    private void setSurroundingMines() {
        for(int x = 0; x < cells.length; x++){
            for(int y = 0; y < cells.length; y++){
                int count = getSurroundingMine(x, y);
                cells[x][y].setSurroundingMines(count);
            }
        }
    }

    private int getSurroundingMine(int x, int y) {
        int mines = 0;
        for (int dx = -1; dx <= 1 ; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                int nx = x + dx;
                int ny = y + dy;
                if (dx == 0 && dy == 0) {
                    continue;
                }

                if (nx < 0 || ny < 0 || nx >= cells.length || ny >= cells.length) {
                    continue;
                }

                if (cells[nx][ny].isMine()) {
                    mines++;
                }
            }
        }
        return mines;
    }
}
